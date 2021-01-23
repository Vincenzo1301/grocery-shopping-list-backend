package com.vauricch.grocery.service;

import com.vauricch.grocery.model.Item;
import com.vauricch.grocery.repository.ItemRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item addItem(Item item, MultipartFile multipartFile) throws IOException {
        item.setImageFile(
                new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
        return itemRepository.insert(item);
    }

    public Optional<Item> getItem(String id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }
}
