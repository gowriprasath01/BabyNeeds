package com.example.babyneeds.data;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;


import androidx.annotation.Nullable;

import com.example.babyneeds.MainActivity;
import com.example.babyneeds.model.Item;
import com.example.babyneeds.ui.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BABY_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_NAME + " TEXT,"
                + Util.KEY_QUANTITY + " INTEGER,"
                + Util.KEY_SIZE+ " TEXT,"
                + Util.KEY_COLOUR + " INTEGER,"
                + Util.KEY_DATE + " LONG);";
        Log.d("items", "onCreate: "+ "table created");
        db.execSQL(CREATE_BABY_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME );
        Log.d("items", "onUpgrade: "+ "table deleted");
        onCreate(db);


    }

    public void Additem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, item.getItemName());
        values.put(Util.KEY_QUANTITY, item.getItemQuantity());
        values.put(Util.KEY_COLOUR, item.getItemColour());
        values.put(Util.KEY_SIZE, item.getItemSize());
        values.put(Util.KEY_DATE, java.lang.System.currentTimeMillis());

        db.insert(Util.TABLE_NAME, null, values);
        Log.d("items", "Additem: "+ "items added");

    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID,
                        Util.KEY_NAME,
                        Util.KEY_QUANTITY,
                        Util.KEY_COLOUR,
                        Util.KEY_SIZE,
                        Util.KEY_DATE},
                Util.KEY_ID + "=?",
                new String[]{String.valueOf(id)
                }, null, null, null, null
        );

        if ((cursor != null)) {
            cursor.moveToFirst();
        }
        Item item = new Item();
        item.setId(cursor.getInt(cursor.getColumnIndex(Util.KEY_ID)));
        item.setItemName(cursor.getString(cursor.getColumnIndex(Util.KEY_NAME)));
        item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Util.KEY_QUANTITY)));
        item.setItemColour(cursor.getString(cursor.getColumnIndex(Util.KEY_COLOUR)));
        item.setItemSize(cursor.getInt(cursor.getColumnIndex(Util.KEY_SIZE)));

        DateFormat dateFormat = DateFormat.getDateInstance();
        String formatteddate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Util.KEY_DATE))));
        item.setDataItemAdded(formatteddate);  // fe 24,2002

        //item.setDataItemAdded(cursor.getString(cursor.getColumnIndex(Util.KEY_DATE)));
        return item;
    }

    public List<Item> getAllItem() {
        List<Item> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID,
                        Util.KEY_NAME,
                        Util.KEY_QUANTITY,
                        Util.KEY_COLOUR,
                        Util.KEY_SIZE,
                        Util.KEY_DATE},
                null, null,
                null,
                null, Util.KEY_DATE + " DESC"
        );

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(Util.KEY_ID)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Util.KEY_NAME)));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Util.KEY_QUANTITY)));
                item.setItemColour(cursor.getString(cursor.getColumnIndex(Util.KEY_COLOUR)));
                item.setItemSize(cursor.getInt(cursor.getColumnIndex(Util.KEY_SIZE)));
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formatteddate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Util.KEY_DATE))));
                item.setDataItemAdded(formatteddate);

                itemList.add(item);


            } while (cursor.moveToNext());
        }
        return itemList;
    }


    //update item
    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_ID, item.getId());
        values.put(Util.KEY_NAME, item.getItemName());
        values.put(Util.KEY_QUANTITY, item.getItemQuantity());
        values.put(Util.KEY_COLOUR, item.getItemColour());
        values.put(Util.KEY_SIZE, item.getItemSize());
        values.put(Util.KEY_DATE, java.lang.System.currentTimeMillis());

        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?", new String[]{Util.KEY_ID});
    }

    public void deteteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();

    }

    public int getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String COUNT_QUERY = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(COUNT_QUERY, null);
        return cursor.getCount();

    }
}
