package com.cts.ecommerce.customer.controller;

import com.cts.ecommerce.customer.dto.InventoryDTO;
import com.cts.ecommerce.customer.entity.CustomerEntity;
import com.cts.ecommerce.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/allCustomers")
    public List<CustomerEntity> getAllCustomers()
    {

        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable int id)
    {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id)
    {
        return customerService.delete(id);

    }

    @PostMapping("/registration")
    private ResponseEntity<String> saveCustomer(@Validated @RequestBody CustomerEntity customer) {
        customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable int id, @RequestBody @Valid CustomerEntity customerEntity)
    {
        return customerService.updateCustomer(id,customerEntity);
    }

    @GetMapping("allInventory")
    public InventoryDTO[] getAllInventory(@RequestParam int customerId)
    {
        return customerService.getAllInventory(customerId);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeInventoryOrder(@RequestParam int customerId,@RequestBody InventoryDTO inventoryDTO)
    {
        return customerService.placeInventoryOrder(customerId,inventoryDTO);
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

