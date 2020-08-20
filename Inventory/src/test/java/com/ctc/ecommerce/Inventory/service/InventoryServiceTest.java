package com.ctc.ecommerce.Inventory.service;

import com.ctc.ecommerce.Inventory.entity.InventoryEntity;
import com.ctc.ecommerce.Inventory.repository.InventoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {
    @InjectMocks
    InventoryService inventoryService;

    @Mock
    InventoryRepository inventoryRepository;

    public List<InventoryEntity> inventoryEntityList()
    {
        long qt=22;
        List<InventoryEntity> inventoryEntity=new ArrayList<>();
        InventoryEntity inventoryEntity1=new InventoryEntity();
        inventoryEntity1.setId(1);
        inventoryEntity1.setProductName("tyers");
        inventoryEntity1.setQuantity(200);
        inventoryEntity1.setPricePerUnit(qt);
        inventoryEntity1.setDateAdded("2018-12-22");
        inventoryEntity.add(inventoryEntity1);
        return inventoryEntity;
    }

    public Optional<InventoryEntity> inventoryEntity()
    {
        long qt=22;
        Integer id=1;
        InventoryEntity inventoryEntity1=new InventoryEntity();
        inventoryEntity1.setId(1);
        inventoryEntity1.setProductName("glass");
        inventoryEntity1.setQuantity(500);
        inventoryEntity1.setPricePerUnit(qt);
        inventoryEntity1.setDateAdded("2018-12-22");
        Optional<InventoryEntity> entity=Optional.ofNullable(inventoryEntity1);
        return entity;
    }
    @Test
    public void getAllPartsTest()
    {
        Mockito.when(inventoryRepository.findAll()).thenReturn(inventoryEntityList());
        List<InventoryEntity> entities= inventoryService.getAllParts();
        Assert.assertEquals(entities,inventoryEntityList());
    }

    @Test
    public void getInventoryByIdTest()
    {
        Integer id=1;
        Mockito.when(inventoryRepository.findById(id)).thenReturn(inventoryEntity());
        ResponseEntity<InventoryEntity> inventory= inventoryService.getInventoryById(id);
        Optional<InventoryEntity> entity=Optional.ofNullable(inventory.getBody());
        Assert.assertEquals(entity,inventoryEntity());

    }

    @Test(expected = NoSuchElementException.class)
    public void getInventoryByIdElseTest()
    {
        Integer id=4;
        //Mockito.when(inventoryRepository.findById(1)).thenReturn(inventoryEntity().orElse(new InventoryEntity()));
        ResponseEntity<InventoryEntity> inventory= inventoryService.getInventoryById(id);
        Assert.assertEquals(HttpStatus.NOT_FOUND,inventory.getStatusCode());
    }

    @Test
    public void searchPartsTest()
    {
        Mockito.when(inventoryRepository.findById(1)).thenReturn(inventoryEntity().orElse(new InventoryEntity()));
        ResponseEntity<String> entity=inventoryService.searchParts(inventoryEntity().orElse(new InventoryEntity()));
        String message= entity.getBody();
        Assert.assertEquals("Insufficent Parts",message);
        Assert.assertEquals( HttpStatus.NOT_FOUND,entity.getStatusCode());

    }

    @Test
    public void searchPartsELseTest()
    {
        Integer id=1;
        InventoryEntity inventoryEntity=new InventoryEntity();
        inventoryEntity.setId(id);
        inventoryEntity.setQuantity(100);
        Mockito.when(inventoryRepository.findById(1)).thenReturn(inventoryEntity().orElse(new InventoryEntity()));
        ResponseEntity<String> entity=inventoryService.searchParts(inventoryEntity);
        String message= entity.getBody();
        Assert.assertEquals("Order Placed Successfully",message);
        Assert.assertEquals( HttpStatus.ACCEPTED,entity.getStatusCode());

    }

    @Test(expected = NoSuchElementException.class)
    public void searchPartsElseFirstTest()
    {
        Integer id=4;
        InventoryEntity inventoryEntity=new InventoryEntity();
        inventoryEntity.setId(id);
        inventoryEntity.setQuantity(100);
        //Mockito.when(inventoryRepository.findById(1)).thenReturn(inventoryEntity().orElse(new InventoryEntity()));
        ResponseEntity<String> inventory= inventoryService.searchParts(inventoryEntity);
        Assert.assertEquals(inventory.getStatusCode(),HttpStatus.NOT_FOUND);
    }

    @Test
    public void saveTest()
    {
        //Mockito.when(inventoryRepository.findByProductName("glass")).thenReturn(inventoryEntity().orElse(new InventoryEntity()));
        ResponseEntity<String> entity= inventoryService.save(inventoryEntity().orElse(new InventoryEntity()));
        Assert.assertEquals(entity.getStatusCode(),HttpStatus.CREATED);
    }

    @Test
    public void saveElseTest()
    {
        Integer id=1;
        InventoryEntity inventoryEntity=new InventoryEntity();
//        InventoryEntity inventoryEntity2=new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setQuantity(200);
        inventoryEntity.setProductName("glass");
        Mockito.when(inventoryRepository.findByProductName(inventoryEntity.getProductName())).thenReturn(inventoryEntity().orElse(new InventoryEntity()));
        InventoryEntity inventoryEntity1=inventoryEntity().orElse(new InventoryEntity());

        inventoryEntity1.setQuantity((inventoryEntity1.getQuantity()+inventoryEntity.getQuantity()));
        inventoryEntity1.setProductName(inventoryEntity.getProductName());
        ResponseEntity<String> entity= inventoryService.save(inventoryEntity().orElse(new InventoryEntity()));
        Assert.assertEquals(entity.getStatusCode(),HttpStatus.CREATED);
    }

    @Test
    public void deleteTest()
    {
        Integer id=1;
        Mockito.when(inventoryRepository.findById(id)).thenReturn(inventoryEntity());
        //inventoryRepository.deleteById(id);
        ResponseEntity<String> entity= inventoryService.delete(id);
        Assert.assertEquals(entity.getStatusCode(),HttpStatus.NO_CONTENT);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteElseTest()
    {
        inventoryRepository.deleteById(1);
        ResponseEntity<String> entity= inventoryService.delete(1);
        Assert.assertEquals(entity.getStatusCode(),HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateInventoryTest()
    {
        InventoryEntity inventoryEntity=new InventoryEntity();
//        InventoryEntity inventoryEntity2=new InventoryEntity();
        inventoryEntity.setId(1);
        inventoryEntity.setQuantity(200);
        inventoryEntity.setProductName("glass");
        Mockito.when(inventoryRepository.findById(1)).thenReturn(inventoryEntity().orElse(new InventoryEntity()));
        InventoryEntity inventoryEntity1=inventoryEntity().orElse(new InventoryEntity());
        inventoryEntity1.setQuantity((inventoryEntity1.getQuantity()+inventoryEntity.getQuantity()));
        inventoryEntity1.setProductName(inventoryEntity.getProductName());
        ResponseEntity<String> entity= inventoryService.updateInventory(1,inventoryEntity().orElse(new InventoryEntity()));
        Assert.assertEquals(entity.getStatusCode(),HttpStatus.CREATED);
    }

    @Test(expected = NoSuchElementException.class)
    public void updateInventoryElseTest()
    {
        ResponseEntity<String> entity= inventoryService.updateInventory(1,inventoryEntity().orElse(new InventoryEntity()));
        Assert.assertEquals(entity.getStatusCode(),HttpStatus.NOT_FOUND);
    }
}
