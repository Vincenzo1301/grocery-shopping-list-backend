package com.vauricch.grocery.repository;

import com.vauricch.grocery.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> { }
