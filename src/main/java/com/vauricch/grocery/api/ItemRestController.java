package com.vauricch.grocery.api;

import com.vauricch.grocery.model.Item;
import com.vauricch.grocery.util.GeneratePdfService;
import com.vauricch.grocery.service.ItemService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
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

    @RequestMapping(method = RequestMethod.GET, value = "/out")
    public ResponseEntity<?> getAllItemsThatAreOut() {
        final List<Item> items = itemService.getAllItemsThatAreOut();

        return ResponseEntity.ok(items);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public ResponseEntity<?> putItem(@PathVariable String id,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) Boolean out,
                                     @RequestParam(required = false) MultipartFile multipartFile) throws IOException {

        final Optional<Item> optionalOldItem = itemService.getItem(id);

        if (!optionalOldItem.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Item oldItem = optionalOldItem.get();

        if (name != null) {
            oldItem.setName(name);
        }

        if (out != null) {
            oldItem.setOut(out);
        }

        if (multipartFile != null) {
            oldItem.setImageFile(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
        }

        itemService.updateItem(oldItem);

        return ResponseEntity.ok(optionalOldItem.get());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download-pdf")
    public ResponseEntity<?> getPdf() {
        final ArrayList<Item> items = new ArrayList<>(itemService.getAllItemsThatAreOut());

        ByteArrayInputStream bis = GeneratePdfService.itemReport(items);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
