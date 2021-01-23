package com.vauricch.grocery.api;

import com.vauricch.grocery.model.Item;
import com.vauricch.grocery.service.ItemService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/items")
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<?> postItem(@RequestParam String name, @RequestParam boolean out, @RequestParam MultipartFile multipartFile) throws IOException {
        Item item = new Item();
        item.setName(name);
        item.setOut(out);

        itemService.addItem(item, multipartFile);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getItem(@PathVariable String id) {
        Optional<Item> item = itemService.getItem(id);

        if (!item.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(item);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllItems() {
        final List<Item> items = itemService.getAllItems();

        return ResponseEntity.ok(items);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> putItem(@PathVariable String id,
                                     @RequestParam String name,
                                     @RequestParam boolean out,
                                     @RequestParam MultipartFile multipartFile) throws IOException {
        Item item = new Item();

        item.setName(name);
        item.setOut(out);
        item.setImageFile(
                new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));

        final Optional<Item> oldItem = itemService.getItem(id);

        if (!oldItem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        final Item newItem = itemService.updateItem(item);

        return ResponseEntity.ok(newItem);
    }
}
