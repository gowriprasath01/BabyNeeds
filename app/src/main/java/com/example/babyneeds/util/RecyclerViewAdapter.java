package com.example.babyneeds.util;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyneeds.R;
import com.example.babyneeds.data.DatabaseHandler;
import com.example.babyneeds.model.Item;

import java.util.List;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;

    private EditText itemname;
    private EditText itemquantity;
    private EditText itemcolor;
    private EditText itemsize;
    private Button savebutton;
    private TextView title_text;



    public RecyclerViewAdapter(Context context, List<Item> items) {
     this.context=context;
     this.itemList= items;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText("NAME: "+item.getItemName());
        holder.itemQuantity.setText("QUANTITY: "+ item.getItemQuantity()+ "");
        holder.itemColour.setText("COLOUR: "+item.getItemColour());
        holder.itemSize.setText("SIZE: "+item.getItemSize() + "");
        holder.date.setText("ADDED ON: "+item.getDataItemAdded() + "");
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private TextView itemName;
        private TextView itemQuantity;
        private TextView itemColour;
        private TextView itemSize;
        private TextView date;
        private Button editButton;
        private Button deleteButton;

        private Button yesButton;
        private Button noButton;







        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context=ctx;

            itemName = itemView.findViewById(R.id.item_name);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            itemColour = itemView.findViewById(R.id.item_colour);
            itemSize = itemView.findViewById(R.id.item_size);
            date= itemView.findViewById(R.id.item_date);
            editButton=itemView.findViewById(R.id.editButton);
            deleteButton= itemView.findViewById(R.id.deleteButton);



            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            Item item = itemList.get(position);
            switch (v.getId()){
                case R.id.editButton:
                    editItem(item);
                   break;
                case R.id.deleteButton:
                    deleteItem(item.getId());
                       break;
            }
        }

        private void deleteItem(int id) {

            builder= new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view= inflater.inflate(R.layout.confirmation_popup,null);
            Button yesButton = itemView.findViewById(R.id.yes_button);
            Button noButton= itemView.findViewById(R.id.no_button);
            builder.setView(view);
            alertDialog= builder.create();
            alertDialog.show();

            DatabaseHandler db= new DatabaseHandler(context);
            db.deteteItem(id);
            itemList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            alertDialog.dismiss();
//            yesButton.setOnClickListener(v -> {
//
//            });
            //noButton.setOnClickListener(v -> alertDialog.dismiss());

        }

        private void editItem(Item newItem) {

           // Log.d("items1", "onClick: "+ itemname.getText().toString() + itemquantity.getText().toString() + itemsize.getText().toString() + itemcolor.getText().toString());

            builder= new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup,null);

            savebutton =view.findViewById(R.id.save_button);
            itemname=view.findViewById(R.id.enter_item);
            itemquantity=view.findViewById(R.id.quantity);
            itemcolor= view.findViewById(R.id.colour);
            itemsize = view.findViewById(R.id.size);
            title_text=view.findViewById(R.id.Enter_item);


            itemname.setText(newItem.getItemName());
            itemquantity.setText(String.valueOf(newItem.getItemQuantity()));
            itemcolor.setText(newItem.getItemColour());
            itemsize.setText(String.valueOf(newItem.getItemSize()));
            title_text.setText(R.string.edit_title);
            savebutton.setText(R.string.update_text);

            Log.d("items", "onClick: "+ itemname.getText().toString() + itemquantity.getText().toString() + itemsize.getText().toString() + itemcolor.getText().toString());


            builder.setView(view);
            alertDialog= builder.create();
            alertDialog.show();



            savebutton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {


                    DatabaseHandler databaseHandler= new DatabaseHandler(context);
                    Log.d("items", "onClick: "+ itemname.getText().toString() + itemquantity.getText().toString() + itemsize.getText().toString() + itemcolor.getText().toString());
                    newItem.setItemName(itemname.getText().toString().trim());
                    newItem.setItemQuantity(Integer.parseInt(itemquantity.getText().toString()));
                    newItem.setItemColour(itemcolor.getText().toString().trim());
                    newItem.setItemSize(Integer.parseInt(itemsize.getText().toString().trim()));
                    if(!itemname.getText().toString().isEmpty() &&
                            !itemquantity.getText().toString().isEmpty() &&
                            !itemsize.getText().toString().isEmpty()&&
                            !itemcolor.getText().toString().isEmpty()){
                        databaseHandler.updateItem(newItem);
                        Log.d("items2", "onClick: "+ itemname.getText().toString() + itemquantity.getText().toString() + itemsize.getText().toString() + itemcolor.getText().toString());
                        alertDialog.dismiss();
                        notifyItemChanged(getAdapterPosition(),newItem);
                    }





                }
            });




        }
    }



}
