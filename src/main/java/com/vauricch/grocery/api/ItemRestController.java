package com.vauricch.grocery.api;

import com.vauricch.grocery.model.Item;
import com.vauricch.grocery.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<?> postItem( @RequestParam String name, @RequestParam boolean out, @RequestParam MultipartFile multipartFile) throws IOException {
        Item item = new Item();
        item.setName(name);
        item.setOut(out);

        itemService.addItem(item, multipartFile);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getItem(@PathVariable String id) {
        Item item = itemService.getItem(id);

        return ResponseEntity.ok(item);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllItems() {
        final List<Item> items = itemService.getAllItems();

        return ResponseEntity.ok(items);
    }
}
