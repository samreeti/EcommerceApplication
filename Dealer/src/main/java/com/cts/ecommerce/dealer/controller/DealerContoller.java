package com.cts.ecommerce.dealer.controller;

import com.cts.ecommerce.dealer.dto.CustomerDTO;
import com.cts.ecommerce.dealer.dto.InventoryDTO;
import com.cts.ecommerce.dealer.entity.DealerEntity;
import com.cts.ecommerce.dealer.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/dealer")
public class DealerContoller {

    @Autowired
    DealerService dealerService;

    @GetMapping("/allDealers")
    public List<DealerEntity> getAllDealers()
    {
        return dealerService.getAllDealer();
    }

    @PostMapping("/registration")
    public ResponseEntity<String> saveDealer(@Validated @RequestBody  DealerEntity dealer) {
        dealerService.saveDealer(dealer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerEntity> getDealerById(@PathVariable int id)
    {
        return dealerService.getDealerById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDealer(@PathVariable("id") int id)
    {
        return dealerService.delete(id);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDealer(@PathVariable int id, @RequestBody @Valid DealerEntity dealerEntity)
    {
        return dealerService.updateDealer(id,dealerEntity);
    }

    @GetMapping("getCustomers")
    public CustomerDTO[] getAllCustomers(@RequestParam int dealerId)
    {
        return dealerService.getCustomers(dealerId);
    }

    @GetMapping("allInventory")
    public InventoryDTO[] getAllInventory(@RequestParam int dealerId)
    {
        return dealerService.getAllInventory(dealerId);
    }

    @DeleteMapping("deleteInventory/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable int id, @RequestParam int dealerId )
    {
        return dealerService.deleteInventory(id,dealerId);
    }

    @PutMapping("updateInventory/{id}")
    public ResponseEntity<String> updateInventory(@PathVariable int id,@RequestParam int dealerId, @RequestBody InventoryDTO inventoryDTO )
    {
        return dealerService.updateInventory(id,dealerId,inventoryDTO);
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
