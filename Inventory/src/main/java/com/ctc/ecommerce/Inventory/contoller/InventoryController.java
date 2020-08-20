package com.ctc.ecommerce.Inventory.contoller;

import com.ctc.ecommerce.Inventory.entity.InventoryEntity;
import com.ctc.ecommerce.Inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/allParts")
    private List<InventoryEntity> getAllParts()
    {
        return inventoryService.getAllParts();
    }

    @GetMapping("/{id}")
    private ResponseEntity<InventoryEntity> getDealerById(@PathVariable int id)
    {
        return inventoryService.getInventoryById(id);
    }

    @PostMapping("/placeOrder")
    private ResponseEntity<String> searchParts( @RequestBody InventoryEntity inventoryEntity)
    {
        return inventoryService.searchParts(inventoryEntity);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteParts(@PathVariable("id") int id)
    {
        return inventoryService.delete(id);

    }

    @PostMapping()
    private ResponseEntity<String> saveParts(@Validated @RequestBody InventoryEntity parts) {
        inventoryService.save(parts);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateInventory(@Validated @PathVariable int id, @Validated @RequestBody  InventoryEntity parts)
    {
        return inventoryService.updateInventory(id,parts);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleMethodArgumentNotValid(MethodArgumentNotValidException e)
    {
        Map<String,String> errors= new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));
        return errors;

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Map<String,String> noSuchElementException(NoSuchElementException e)
    {
        Map<String,String> errors= new HashMap<>();
        errors.put("message",e.getMessage());
        return errors;

    }
}

