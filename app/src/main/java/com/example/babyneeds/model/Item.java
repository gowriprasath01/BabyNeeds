package com.example.babyneeds.model;

public class Item {
    private int id;
    private  String itemName;
    private int itemQuantity;
    private String itemColour;
    private int itemSize;
    private String dataItemAdded;

    public Item() {
    }

    public Item(int id, String itemName, int itemQuantity, String itemColour, int itemSize, String dataItemAdded) {
        this.id = id;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemColour = itemColour;
        this.itemSize = itemSize;
        this.dataItemAdded = dataItemAdded;
    }

    public Item(String itemName, int itemQuantity, String itemColour, int itemSize, String dataItemAdded) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemColour = itemColour;
        this.itemSize = itemSize;
        this.dataItemAdded = dataItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemColour() {
        return itemColour;
    }

    public void setItemColour(String itemColour) {
        this.itemColour = itemColour;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public String getDataItemAdded() {
        return dataItemAdded;
    }

    public void setDataItemAdded(String dataItemAdded) {
        this.dataItemAdded = dataItemAdded;
    }
}

