package com.example.somesh.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FileUtils.*;


public class TodoActivity extends ActionBarActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        lvItems = (ListView)findViewById(R.id.lvItems);
        //items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        //items.add("First Item");
        //items.add("Second Item");
        setupListViewListener();
        setupClickListener();
    }

    private void setupClickListener() {

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {

                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                i.putExtra("itemName", items.get(position));
                i.putExtra("itemPos", ""+position);

                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    private void setupListViewListener() {

        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View item, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String itemName = data.getExtras().getString("finalName");
            int itemPos = Integer.valueOf(data.getExtras().getString("finalPos"));
            System.out.println("!!!!"+itemPos);
            items.remove(itemPos);
            System.out.println(items);
            items.add(itemPos, itemName);
            itemsAdapter.notifyDataSetChanged();
            writeItems();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {

        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try{
            FileUtils.writeLines(todoFile, items);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
