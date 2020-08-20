package com.ctc.ecommerce.Inventory.service;

import com.ctc.ecommerce.Inventory.entity.InventoryEntity;
import com.ctc.ecommerce.Inventory.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private static final Logger log= LoggerFactory.getLogger(InventoryService.class);
    @Autowired
    InventoryRepository inventoryRepository;

    public List<InventoryEntity> getAllParts() {
        log.info("Displaying all Inventory details");
        List<InventoryEntity> parts = new ArrayList<>();
        inventoryRepository.findAll().forEach(part -> parts.add(part));
        List<InventoryEntity> sortedList=parts.stream().sorted(Comparator.comparing(InventoryEntity::getProductName)).collect(Collectors.toList());
        return sortedList;
    }

    public ResponseEntity<InventoryEntity> getInventoryById(Integer id) {
        log.info("Displaying Inventory details on the basis of Id");
        Optional<InventoryEntity> dealerEntity=inventoryRepository.findById(id);
        if(!dealerEntity.isPresent())
        {
            log.error("Error Occured");
            throw new NoSuchElementException("No Data found for this id " + id );
        }
        return new  ResponseEntity<>(dealerEntity.get(),HttpStatus.FOUND);
    }

    public ResponseEntity<String> searchParts(InventoryEntity inventoryEntity)
    {
        log.info("Placing order");
        InventoryEntity inventoryEntity1=new InventoryEntity();
        inventoryEntity1= inventoryRepository.findById(inventoryEntity.getId());
        String message="";
        if(inventoryEntity1!=null)
        {
            if(((inventoryEntity1.getQuantity())>(inventoryEntity.getQuantity()))||((inventoryEntity1.getQuantity()).equals(inventoryEntity.getQuantity())))
            {
                inventoryEntity1.setQuantity((inventoryEntity1.getQuantity())-(inventoryEntity.getQuantity()));
                if(inventoryEntity1.getQuantity()==0)
                {
                    inventoryRepository.deleteById(inventoryEntity1.getId());
                    message="Order Placed Successfully";
                    return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
                }
                else
                {
                    inventoryRepository.save(inventoryEntity1);
                    message="Order Placed Successfully";
                    return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
                }
            }
            else
            {
                message="Insufficent Parts";
                return new ResponseEntity<String>(message,HttpStatus.NOT_FOUND);
            }
        }
        else
        {
            log.error("Error Occured");
            throw new NoSuchElementException("No Data found for this id " + inventoryEntity.getId() );
        }

    }

    public ResponseEntity<String> save(InventoryEntity inventoryEntity)
    {
        log.info("Saving details of Inventory in DB");
        InventoryEntity inventoryEntity1=inventoryRepository.findByProductName(inventoryEntity.getProductName());
        if(inventoryEntity1!=null)
        {
            inventoryEntity1.setQuantity((inventoryEntity1.getQuantity())+(inventoryEntity.getQuantity()));
            inventoryEntity1.setProductName(inventoryEntity.getProductName());
            inventoryRepository.save(inventoryEntity1);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else
        {
            inventoryRepository.save(inventoryEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }

    public ResponseEntity<String> delete(Integer id)
    {
        log.info("Deleting Inventory details");
        Optional<InventoryEntity> inventoryEntity1=inventoryRepository.findById(id);
        if(!inventoryEntity1.isPresent())
        {
            throw new NoSuchElementException("No Data found for this id " + id );
        }
        else {
            inventoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<String> updateInventory(int id,InventoryEntity inventoryEntity)
    {
        log.info("updating inventory details");
        InventoryEntity inventoryEntity1=inventoryRepository.findById(id);
        if(inventoryEntity1==null)
        {
            log.error("Error Occured");
            throw new NoSuchElementException("No Data found for this id " + id );
        }
        else
        {
            inventoryEntity1.setQuantity((inventoryEntity1.getQuantity())+(inventoryEntity.getQuantity()));
            inventoryEntity1.setProductName(inventoryEntity.getProductName());
            inventoryEntity1.setId(id);
            inventoryRepository.save(inventoryEntity1);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
