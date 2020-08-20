package com.cts.ecommerce.customer.service;

import com.cts.ecommerce.customer.dto.InventoryDTO;
import com.cts.ecommerce.customer.entity.CustomerEntity;
import com.cts.ecommerce.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@EnableEurekaClient
public class CustomerService {

    private static final Logger log= LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<CustomerEntity> getAllCustomers() {
        log.info("Details of all customers");
        List<CustomerEntity> customers =new ArrayList<>();
        customerRepository.findAll().forEach(customer ->customers.add(customer));
        List<CustomerEntity> sortedList=customers.stream().sorted(Comparator.comparing(CustomerEntity::getCustomerName))
                .collect(Collectors.toList());
        return sortedList;
    }

    public ResponseEntity<CustomerEntity> getCustomerById(Integer id) {
        log.info("Detail of customer on the basis of Id");
        Optional<CustomerEntity> customerEntity=customerRepository.findById(id);
        if(!customerEntity.isPresent())
        {
            log.error("Error happened");
            throw new NoSuchElementException("No Such Customer found for this id " + id );
        }
        return new  ResponseEntity<>(customerEntity.get(),HttpStatus.FOUND);
    }

    public void save(CustomerEntity customerEntity) {
        log.info("Saving detaila of customers");
        customerRepository.save(customerEntity);
    }

    public ResponseEntity<String> delete(Integer id)
    {
        log.info("Deleting details of customer on the basis of Id");
        Optional<CustomerEntity> customerEntity1=customerRepository.findById(id);
        if(!customerEntity1.isPresent())
        {
            log.error("Error happened");
            throw new NoSuchElementException("No Such Customer found for this id " + id );
        }
        else {
            customerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<String> updateCustomer(int id, CustomerEntity customerEntity)
    {
        Optional<CustomerEntity> customerEntity1=customerRepository.findById(id);
        log.info("Updating the details of customer on the basis of Id");
        if(!customerEntity1.isPresent())
        {
            log.error("Error happened");
            throw new NoSuchElementException("No Such Customer found for this id " + id );
        }
        else
        {
            customerEntity.setCustomer_id(id);
            customerRepository.save(customerEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    public InventoryDTO[] getAllInventory(Integer id)
    {
        Optional<CustomerEntity> dealerEntity=customerRepository.findById(id);
        log.info("All Inventory details stored in DB");
        if(!dealerEntity.isPresent())
        {
            log.error("Error happened");
            throw new NoSuchElementException("No Such Customer found for this id " + id );
        }
        else
        {
            String url="http://INVENTORY-SERVICE/inventory/allParts";
            log.info("client call made ti Inventory service");
            ResponseEntity<InventoryDTO[]> responseEntity = restTemplate.getForEntity(url,InventoryDTO[].class);
            return responseEntity.getBody();
        }
    }

    public ResponseEntity<String> placeInventoryOrder(int id,InventoryDTO inventoryDTO)
    {
        log.info("Order Placed by customer");
        StringJoiner url=new StringJoiner("");
        Optional<CustomerEntity> dealerEntity=customerRepository.findById(id);
        String message= "";
        HttpStatus httpStatus;
        if(!dealerEntity.isPresent())
        {
            log.error("Error happened");
            throw new NoSuchElementException("No Such Customer found for this id " + id );
        }
        else
        {
            url.add("http://INVENTORY-SERVICE/inventory/placeOrder");
            String urlString=url.toString();
            try{
                log.info("client call made to Inventory service");
                ResponseEntity<String> responseEntity= restTemplate.postForEntity(urlString,inventoryDTO,String.class);
                message= responseEntity.getBody();
                httpStatus=responseEntity.getStatusCode();
                return new ResponseEntity<String>(message,httpStatus);
            }
            catch(Exception e)
            {
                log.error("Error happened");
                throw new NoSuchElementException("No Inventory found for this id " + inventoryDTO.getId() );
            }


        }
    }

}

