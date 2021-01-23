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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/items")
public class ItemRestController {

    @Autowired
    private ItemService itemService;

    @PostMapping(path = "/add")
    public ResponseEntity<?> postItem(@RequestBody Item item, @RequestParam MultipartFile multipartFile) throws IOException {
        itemService.addItem(item, multipartFile);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getItem(@PathVariable String id) {
        Optional<Item> optionalItem = itemService.getItem(id);
        if(!optionalItem.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Item item = optionalItem.get();
        return ResponseEntity.ok(item);
    }
}
