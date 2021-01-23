package com.vauricch.grocery.model;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

@Data
public class Item {

    /**
     * The unique identifier of the item
     */
    @Id
    private String id;

    /**
     * The name of the item
     */
    private String name;

    /**
     * Indicates whether an item is no longer in stock
     */
    private boolean out;

    /**
     * Each item is visualized by an image
     */
    private Binary imageFile;

}
