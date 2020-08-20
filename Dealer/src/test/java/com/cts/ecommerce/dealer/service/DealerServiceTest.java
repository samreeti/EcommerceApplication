package com.cts.ecommerce.dealer.service;

import com.cts.ecommerce.dealer.dto.CustomerDTO;
import com.cts.ecommerce.dealer.dto.InventoryDTO;
import com.cts.ecommerce.dealer.entity.DealerEntity;
import com.cts.ecommerce.dealer.repository.DealerRepository;
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
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class DealerServiceTest {

    @InjectMocks
    DealerService dealerService;

    @Mock
    DealerRepository dealerRepository;
    @Mock
    RestTemplate restTemplate;


    public List<DealerEntity> dealerList()
    {
        List<DealerEntity> dealerEntities=new ArrayList<>();
        DealerEntity dealer1=new DealerEntity();
        dealer1.setDealer_id(1);
        dealer1.setDealerName("sam");
        dealer1.setPassword("123456");
        dealer1.setStoreAddress("2dfff");
        dealerEntities.add(dealer1);
        return dealerEntities;
    }

    public List<CustomerDTO> dtos()
    {
        List<CustomerDTO> customerDTOS=new ArrayList<>();
        LocalDate localDate=LocalDate.parse("2018-12-22");
        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setCustomer_id(1);
        customerDTO.setCustomerName("sam");
        customerDTO.setDateOfBirth(localDate);
        customerDTO.setDeliveryAddress("abc");
        customerDTO.setPassword("12345");
        customerDTOS.add(customerDTO);
        return customerDTOS;
    }

    public ResponseEntity<CustomerDTO[]> customerDTO()
    {
        Integer id=1;
        CustomerDTO[] customerDTOS=new CustomerDTO[10];
        LocalDate localDate=LocalDate.parse("2018-12-22");
        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setCustomer_id(id);
        customerDTO.setCustomerName("sam");
        customerDTO.setDateOfBirth(localDate);
        customerDTO.setDeliveryAddress("abc");
        customerDTO.setPassword("12345");
        customerDTOS[0]=customerDTO;
        return new ResponseEntity<>(customerDTOS,HttpStatus.OK);
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
        inventoryDTOS[0]=inventoryDTO;
        return new ResponseEntity<>(inventoryDTOS,HttpStatus.OK);
    }

    public InventoryDTO dto()
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

    public Optional<DealerEntity> dealerEntity1()
    {
        DealerEntity dealer1=new DealerEntity();
        dealer1.setDealer_id(1);
        dealer1.setDealerName("sam");
        dealer1.setPassword("123456");
        dealer1.setStoreAddress("2dfff");
        Optional<DealerEntity> entity=Optional.ofNullable(dealer1);
        return entity;
    }
    @Test
    public void getAllDealerTest()
    {
        Mockito.when(dealerRepository.findAll()).thenReturn(dealerList());
        List<DealerEntity> dealerEntity= dealerService.getAllDealer();
        Assert.assertEquals(dealerList(),dealerEntity);
    }

    @Test
    public void getDealerByIdTest()
    {
        Mockito.when(dealerRepository.findById(1)).thenReturn(dealerEntity1());
        ResponseEntity<DealerEntity> dealerEntity=dealerService.getDealerById(1);
        Optional<DealerEntity> entity=Optional.ofNullable(dealerEntity.getBody());
        Assert.assertEquals(dealerEntity1(),entity);
    }

    @Test(expected = NoSuchElementException.class)
    public void getDealerByIdElseTest()
    {
        ResponseEntity<DealerEntity> dealerEntity=dealerService.getDealerById(9);
    }

    @Test
    public void deleteTest()
    {
        Mockito.when(dealerRepository.findById(1)).thenReturn(dealerEntity1());
        ResponseEntity<String> entity= dealerService.delete(1);
        Assert.assertEquals( HttpStatus.NO_CONTENT,entity.getStatusCode());
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteElseTest()
    {
        Mockito.when(dealerRepository.findById(1)).thenReturn(dealerEntity1());
        ResponseEntity<String> entity= dealerService.delete(3);
    }

    @Test
    public void updateDealerTest()
    {
        Mockito.when(dealerRepository.findById(1)).thenReturn(dealerEntity1());
        ResponseEntity<String> entity= dealerService.updateDealer(1,dealerEntity1().orElse(new DealerEntity()));
        Assert.assertEquals(HttpStatus.CREATED,entity.getStatusCode());
    }

    @Test(expected = NoSuchElementException.class)
    public void updateDealerElseTest()
    {
        int id=3;
        Mockito.when(dealerRepository.findById(1)).thenReturn(dealerEntity1());
        ResponseEntity<String> entity= dealerService.updateDealer(id,dealerEntity1().orElse(new DealerEntity()));
    }

    @Test
    public void getCustomerTest()
    {
        Integer dealerId=1;
        Mockito.when(dealerRepository.findById(dealerId)).thenReturn(dealerEntity1());
        String url="http://localhost:8086/customer/allCustomers";
        Mockito.when(restTemplate.getForEntity(url,CustomerDTO[].class)).thenReturn(customerDTO());
        CustomerDTO[] entity=dealerService.getCustomers(dealerId);
        Assert.assertEquals(entity,customerDTO().getBody());
    }

    @Test(expected = NoSuchElementException.class)
    public void getCustomerElseTest()
    {
        Mockito.when(dealerRepository.findById(1)).thenReturn(dealerEntity1());
        ResponseEntity<String> entity= dealerService.delete(3);
    }

    @Test
    public void getAllInventoryTest()
    {
        Integer dealerId=1;
        Mockito.when(dealerRepository.findById(dealerId)).thenReturn(dealerEntity1());
        String url="http://INVENTORY-SERVICE/inventory/allParts";
        Mockito.when(restTemplate.getForEntity(url,InventoryDTO[].class)).thenReturn(inventoryDTO1());
        Assert.assertEquals(dealerService.getAllInventory(1),inventoryDTO1().getBody());
    }

    @Test(expected = NoSuchElementException.class)
    public void getAllInventoryElseTest()
    {
        InventoryDTO[] inventoryDTO=dealerService.getAllInventory(7);
    }

    ResponseEntity<String> stringResponseEntity()
    {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @Test
    public void deleteInventoryTest()
    {
        Mockito.when(dealerRepository.findById(1)).thenReturn(dealerEntity1());
        StringJoiner url= new StringJoiner("");
        url.add("http://INVENTORY-SERVICE/inventory/");
        url.add(Integer.toString(1));
        String urlString=url.toString();
        restTemplate.delete(urlString);
        Assert.assertEquals(dealerService.deleteInventory(1,1),stringResponseEntity());
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteInventoryElseTest()
    {
        Assert.assertEquals(dealerService.deleteInventory(1,7),stringResponseEntity());
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteInventoryElseFirstTest()
    {
        Assert.assertEquals(dealerService.deleteInventory(8,1),stringResponseEntity());
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteInventoryElseSecondTest()
    {
        Assert.assertEquals(dealerService.deleteInventory(9,7),stringResponseEntity());
    }

    @Test
    public void updateInventoryTest()
    {
        Mockito.when(dealerRepository.findById(1)).thenReturn(dealerEntity1());
        StringJoiner url= new StringJoiner("");
        url.add("http://INVENTORY-SERVICE/inventory/");
        url.add(Integer.toString(1));
        String urlString=url.toString();
        restTemplate.put(urlString,inventoryDTO1().getBody());
        Assert.assertEquals(HttpStatus.CREATED,(dealerService.updateInventory(1,1,dto()).getStatusCode()));

    }

    @Test(expected = NoSuchElementException.class)
    public void updateInventoryElseTest()
    {
        Assert.assertEquals(HttpStatus.CREATED,(dealerService.updateInventory(1,7,dto()).getStatusCode()));

    }

    @Test(expected = NoSuchElementException.class)
    public void updateInventoryElseFirstTest()
    {
        Assert.assertEquals(HttpStatus.CREATED,(dealerService.updateInventory(8,1,dto()).getStatusCode()));

    }

    @Test(expected = NoSuchElementException.class)
    public void updateInventoryElseSecondTest()
    {
        Assert.assertEquals((dealerService.updateInventory(9,9,dto()).getStatusCode()),HttpStatus.CREATED);

    }
}
