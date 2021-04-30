package com.example.babyneeds;

import android.content.Intent;
import android.os.Bundle;

import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText itemname;
    private EditText itemquantity;
    private EditText itemcolor;
    private EditText itemsize;
    private Button savebutton;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        byPassActivity();
        List<Item> itemList= databaseHandler.getAllItem();
        for(Item item: itemList){
            Log.d("Main", "onCreate: "+ item.getItemName() + " " + item.getDataItemAdded());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Createpopup();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void byPassActivity() {
        if (databaseHandler.getCount()>0){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
    }

    private void saveItem(View view) {
        // save the item
        Item item = new Item();
        item.setItemName(itemname.getText().toString().trim());
        item.setItemQuantity(Integer.parseInt(itemquantity.getText().toString().trim()));
        item.setItemColour(itemcolor.getText().toString().trim());
        item.setItemSize(Integer.parseInt(itemsize.getText().toString().trim()));
        databaseHandler.Additem(item);


        Snackbar.make(view,"item added", BaseTransientBottomBar.LENGTH_SHORT).show();
        // move to the new activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this,ListActivity.class ));
            }
        },1200);
    }

    private void Createpopup() {
        builder= new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        savebutton =view.findViewById(R.id.save_button);
        itemname=view.findViewById(R.id.enter_item);
        itemquantity=view.findViewById(R.id.quantity);
        itemcolor= view.findViewById(R.id.colour);
        itemsize = view.findViewById(R.id.size);
        savebutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!itemname.getText().toString().isEmpty() &&
                   !itemquantity.getText().toString().isEmpty() &&
                   !itemsize.getText().toString().isEmpty()&&
                        !itemcolor.getText().toString().isEmpty()){
                       saveItem(v);
                }else
                {
                    Snackbar.make(v,"Empty field cannot be added", BaseTransientBottomBar.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}