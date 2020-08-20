package com.cts.ecommerce.customer.service;

import com.cts.ecommerce.customer.dto.InventoryDTO;
import com.cts.ecommerce.customer.entity.CustomerEntity;
import com.cts.ecommerce.customer.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

   @Mock
   CustomerRepository customerRepository;
   @Mock
   RestTemplate restTemplate;
   @Mock
   InventoryDTO inventoryDTO;

   public List<CustomerEntity> customerEntity()
   {
       LocalDate localDate=LocalDate.parse("2018-12-18");
       List<CustomerEntity> customerEntities=new ArrayList<>();
       CustomerEntity customerEntity=new CustomerEntity();
       customerEntity.setCustomer_id(1);
       customerEntity.setCustomerName("sam");
       customerEntity.setPassword("12345");
       customerEntity.setDateOfBirth(localDate);
       customerEntity.setDeliveryAddress("xyz");
       customerEntities.add(customerEntity);
       return customerEntities;

   }

   public Optional<CustomerEntity> customerEntity1()
   {
       Integer id=1;
       LocalDate localDate=LocalDate.parse("2018-12-18");
       CustomerEntity customerEntity=new CustomerEntity();
       customerEntity.setCustomer_id(1);
       customerEntity.setCustomerName("sam");
       customerEntity.setPassword("12345");
       customerEntity.setDateOfBirth(localDate);
       customerEntity.setDeliveryAddress("xyz");
       return Optional.ofNullable(customerEntity);
   }

   public InventoryDTO inventoryDTO()
    {
        long qt=22;
        Integer id=1;
        InventoryDTO inventoryDTO=new InventoryDTO();
        inventoryDTO.setId(id);
        inventoryDTO.setProductName("wheels");
        inventoryDTO.setQuantity(200);
        inventoryDTO.setDateAdded("2019-10-07");
        inventoryDTO.setPricePerUnit(qt);
        return inventoryDTO;
    }

    public InventoryDTO inventoryDTO3()
    {
        long qt=22;
        Integer id=9;
        InventoryDTO inventoryDTO=new InventoryDTO();
        inventoryDTO.setId(id);
        inventoryDTO.setProductName("wheels");
        inventoryDTO.setQuantity(200);
        inventoryDTO.setDateAdded("2019-10-07");
        inventoryDTO.setPricePerUnit(qt);
        return inventoryDTO;
    }

    public InventoryDTO inventoryDTO2()
    {

        Integer id=1;
        InventoryDTO inventoryDTO=new InventoryDTO();
        inventoryDTO.setId(1);
        inventoryDTO.setQuantity(200);
        return inventoryDTO;
    }

    public ResponseEntity<InventoryDTO[]> inventoryDTO1()
    {
       InventoryDTO[] inventoryDTOS=new InventoryDTO[10];
        long qt=22;
        Integer id=1;
        InventoryDTO inventoryDTO=new InventoryDTO();
        inventoryDTO.setId(id);
        inventoryDTO.setProductName("wheels");
        inventoryDTO.setQuantity(200);
        inventoryDTO.setDateAdded("2019-10-07");
        inventoryDTO.setPricePerUnit(qt);
        inventoryDTOS[1]=inventoryDTO;
        return new ResponseEntity<>(inventoryDTOS,HttpStatus.OK);
    }

   @Test
    public void getAllCustomerTest()
   {
       Mockito.when(customerRepository.findAll()).thenReturn(customerEntity());
       List<CustomerEntity> entities= customerService.getAllCustomers();
       Assert.assertEquals(entities,customerEntity());
   }

   @Test
    public void getCustomerByIdTest()
   {
       Integer id=1;
       Mockito.when(customerRepository.findById(id)).thenReturn(customerEntity1());
       ResponseEntity<CustomerEntity> entity=customerService.getCustomerById(id);
       Assert.assertEquals(entity.getBody(),customerEntity1().orElse(new CustomerEntity()));
   }

   @Test(expected = NoSuchElementException.class)
    public void getCustomerByIdElseTest()
   {
       Integer id=2;
       ResponseEntity<CustomerEntity> entity=customerService.getCustomerById(id);
       Assert.assertEquals(entity.getBody(),customerEntity1().orElse(new CustomerEntity()));
       Assert.assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
   }

   @Test
    public void saveTest()
   {
       customerService.save(customerEntity1().orElse(new CustomerEntity()));
   }

   @Test
    public void deleteTest()
   {
       Mockito.when(customerRepository.findById(1)).thenReturn(customerEntity1());
       ResponseEntity<String> entity=customerService.delete(1);
       Assert.assertEquals(entity.getStatusCode(),HttpStatus.NO_CONTENT);
   }

   @Test(expected = NoSuchElementException.class)
    public void deleteElseTest()
   {
       ResponseEntity<String> entity=customerService.delete(6);
       Assert.assertEquals(entity.getStatusCode(),HttpStatus.NOT_FOUND);
   }

   @Test
    public void updateCustomerTest()
   {
       Mockito.when(customerRepository.findById(1)).thenReturn(customerEntity1());
       ResponseEntity<String> entity=customerService.updateCustomer(1,customerEntity1().orElse(new CustomerEntity()));
       Assert.assertEquals(entity.getStatusCode(),HttpStatus.CREATED);
   }

   @Test(expected = NoSuchElementException.class)
    public void updateCustomerElseTest()
   {
       ResponseEntity<String> entity=customerService.updateCustomer(8,customerEntity1().orElse(new CustomerEntity()));
       Assert.assertEquals(entity.getStatusCode(),HttpStatus.NOT_FOUND);
   }

   @Test
    public void getAllInventoryTest()
   {
       Integer customerId=1;
       Mockito.when(customerRepository.findById(customerId)).thenReturn(customerEntity1());
       String url="http://INVENTORY-SERVICE/inventory/allParts";
       Mockito.when(restTemplate.getForEntity(url,InventoryDTO[].class)).thenReturn(inventoryDTO1());
       Assert.assertEquals(customerService.getAllInventory(customerId),inventoryDTO1().getBody());

   }

   @Test(expected = NoSuchElementException.class)
    public void getAllInventoryElseTest()
   {
       InventoryDTO[] inventoryDTO=customerService.getAllInventory(7);
   }

   ResponseEntity<String> stringResponseEntity()
   {
       return ResponseEntity.status(HttpStatus.ACCEPTED)
               .body("Order placed Sucessfully");
   }

   @Test(expected = NoSuchElementException.class)
    public void placeInventoryOrderTest()
   {
       Integer  id=1;
       Mockito.when(customerRepository.findById(1)).thenReturn(customerEntity1());
       String url="http://INVENTORY-SERVICE/inventory/placeOrder";
       //Mockito.when(restTemplate.postForEntity(url,inventoryDTO1(),String.class)).thenReturn(stringResponseEntity());
       Assert.assertEquals(customerService.placeInventoryOrder(id,inventoryDTO2()),stringResponseEntity());
   }

    @Test(expected = NoSuchElementException.class)
    public void placeInventoryOrderElseTest()
    {
        String url="http://INVENTORY-SERVICE/inventory/placeOrder";
        Assert.assertEquals(customerService.placeInventoryOrder(7,inventoryDTO()),stringResponseEntity());
    }


    @Test(expected = NoSuchElementException.class)
    public void placeInventoryOrderElseFirstTest()
    {
        String url="http://INVENTORY-SERVICE/inventory/placeOrder";
        Assert.assertEquals(customerService.placeInventoryOrder(1,inventoryDTO3()),stringResponseEntity());
    }

}
