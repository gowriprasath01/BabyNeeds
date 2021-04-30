package com.example.babyneeds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;
import com.example.babyneeds.util.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList= new ArrayList();
    private DatabaseHandler databaseHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText itemname;
    private EditText itemquantity;
    private EditText itemcolor;
    private EditText itemsize;
    private Button savebutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        databaseHandler =new DatabaseHandler(ListActivity.this);

        recyclerView =findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        itemList= databaseHandler.getAllItem();
        for(Item item: itemList){
            Log.d("list", "onCreate: "+ item.getItemName() + " " + item.getDataItemAdded());
        }
        recyclerViewAdapter = new RecyclerViewAdapter(this,itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createpopup();

            }
        });
    }

    private void createpopup() {
        builder= new AlertDialog.Builder(this);
        View view= getLayoutInflater().inflate(R.layout.popup,null);
        builder.setView(view);
        dialog= builder.create();
        dialog.show();
        savebutton =view.findViewById(R.id.save_button);
        itemname=view.findViewById(R.id.enter_item);
        itemquantity=view.findViewById(R.id.quantity);
        itemcolor= view.findViewById(R.id.colour);
        itemsize = view.findViewById(R.id.size);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!itemname.getText().toString().isEmpty() &&
                        !itemquantity.getText().toString().isEmpty() &&
                        !itemsize.getText().toString().isEmpty()&&
                        !itemcolor.getText().toString().isEmpty()){
                    saveItems(v);
                }else
                {
                    Snackbar.make(v,"Empty field cannot be added", BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void saveItems(View view) {
        Item item = new Item();
        item.setItemName(itemname.getText().toString().trim());
        item.setItemQuantity(Integer.parseInt(itemquantity.getText().toString().trim()));
        item.setItemColour(itemcolor.getText().toString().trim());
        item.setItemSize(Integer.parseInt(itemsize.getText().toString().trim()));
        databaseHandler.Additem(item);
        Snackbar.make(view,"item added",1200).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(ListActivity.this,ListActivity.class));
            }
        },1200);
    }
}