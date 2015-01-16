package com.example.somesh.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {


    private String itemPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String itemName = getIntent().getStringExtra("itemName");
        itemPos = getIntent().getStringExtra("itemPos");
        onEditItem(itemName);

    }

    private void onEditItem(String itemText) {

        EditText etNewItem = (EditText) findViewById(R.id.editTxt);
        etNewItem.setText(itemText);
        etNewItem.setSelection(etNewItem.getText().length());
        etNewItem.setFocusable(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void onSaveClick(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.editTxt);
        Intent data = new Intent();
        data.putExtra("finalName", etNewItem.getText().toString());
        data.putExtra("finalPos", itemPos);

        setResult(RESULT_OK, data);
        finish();


    }
}
