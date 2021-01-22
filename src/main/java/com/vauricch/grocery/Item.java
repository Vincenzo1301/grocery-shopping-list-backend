package com.vauricch.grocery;

import lombok.Data;

@Data
public class Item {

    /**
     * The name of the item
     */
    private String name;

    /**
     * Indicates whether an item is no longer in stock
     */
    private boolean out;



}
