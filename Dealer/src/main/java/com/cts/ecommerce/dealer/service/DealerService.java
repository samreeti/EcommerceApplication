package com.cts.ecommerce.dealer.service;

import com.cts.ecommerce.dealer.dto.CustomerDTO;
import com.cts.ecommerce.dealer.dto.InventoryDTO;
import com.cts.ecommerce.dealer.entity.DealerEntity;
import com.cts.ecommerce.dealer.repository.DealerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DealerService {

    private static final Logger log= LoggerFactory.getLogger(DealerService.class);
    @Autowired
    private DealerRepository dealerRepository;
    @Autowired
    private RestTemplate restTemplate;

    public List<DealerEntity> getAllDealer() {
        log.info("All dealer details");
        List<DealerEntity> dealers = new ArrayList<>();
        dealerRepository.findAll().forEach(dealer ->dealers.add(dealer));
        List<DealerEntity> sortedList=dealers.stream().sorted(Comparator.comparing(DealerEntity::getDealerName))
                .collect(Collectors.toList());
        return sortedList;
    }

    public ResponseEntity<DealerEntity> getDealerById(int id) {
        log.info("Dealer details on the basis of Id");
        Optional<DealerEntity> dealerEntity=dealerRepository.findById(id);
        if(!dealerEntity.isPresent())
        {
            log.error("Error occured");
            throw new NoSuchElementException("No Such Dealer found for this id " + id );
        }
        return new  ResponseEntity<>(dealerEntity.get(),HttpStatus.FOUND);
    }

    public void saveDealer(DealerEntity dealerEntity)
    {
        dealerRepository.save(dealerEntity);
    }

    public ResponseEntity<String> delete(int id)
    {
        log.info("Deleting Dealer details on the Id");
        Optional<DealerEntity> dealerEntity1=dealerRepository.findById(id);
        if(!dealerEntity1.isPresent())
        {
            log.error("Error occured");
            throw new NoSuchElementException("No Such Dealer found for this id " + id );
        }
        else
        {
            dealerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<String> updateDealer(int id, DealerEntity dealerEntity)
    {
        log.info("Updating Dealer details");
        Optional<DealerEntity> dealerEntity1=dealerRepository.findById(id);
        if(!dealerEntity1.isPresent())
        {
            log.error("Error occured");
            throw new NoSuchElementException("No Such Dealer found for this id " + id );
        }
        else
        {
            dealerEntity.setDealer_id(id);
            dealerRepository.save(dealerEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    public CustomerDTO[] getCustomers(Integer id)
    {
        Optional<DealerEntity> dealerEntity=dealerRepository.findById(id);
        if(!dealerEntity.isPresent())
        {
            log.error("Error occured");
            throw new NoSuchElementException("No Such Dealer found for this id " + id );
        }
        else
        {
            log.info("All Customer Details");
            String url="http://CUSTOMER-SERVICE/customer/allCustomers";
            ResponseEntity<CustomerDTO[]> responseEntity = restTemplate.getForEntity(url,CustomerDTO[].class);
            return responseEntity.getBody();
        }
    }

    public InventoryDTO[] getAllInventory(Integer id)
    {
        Optional<DealerEntity> dealerEntity=dealerRepository.findById(id);
        if(!dealerEntity.isPresent())
        {
            log.error("Error occured");
            throw new NoSuchElementException("No Such Dealer found for this id " + id );
        }
        else
        {
            log.info("All Inventory details");
            String url="http://INVENTORY-SERVICE/inventory/allParts";
            ResponseEntity<InventoryDTO[]> responseEntity = restTemplate.getForEntity(url,InventoryDTO[].class);
            return responseEntity.getBody();
        }
    }

    public ResponseEntity<String> deleteInventory(Integer id, int dealerId)
    {
        StringJoiner url= new StringJoiner("");
        Optional<DealerEntity> dealerEntity1=dealerRepository.findById(dealerId);
        if(!dealerEntity1.isPresent())
        {
            log.error("Error occured");
            throw new NoSuchElementException("No Such Dealer found for this id " + dealerId );
        }
        else
        {
            log.info("Deleting Inventory by Dealer");
            url.add("http://INVENTORY-SERVICE/inventory/");
            url.add(Integer.toString(id));
            String urlString=url.toString();
            try
            {
                restTemplate.delete(urlString);
            }
            catch (Exception e)
            {
                throw new NoSuchElementException("No Such Inventory found for this id " + id );
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    public ResponseEntity<String> updateInventory(int id,int dealer_id, InventoryDTO inventoryDTO) {
        StringJoiner url = new StringJoiner("");
        Optional<DealerEntity> dealerEntity1 = dealerRepository.findById(dealer_id);
        if (!dealerEntity1.isPresent())
        {
            log.error("Error occured");
            throw new NoSuchElementException("No Such Dealer found for this id " + dealer_id);
        } else {
            log.info("updating Inventory by Dealer");
            url.add("http://INVENTORY-SERVICE/inventory/");
            url.add(Integer.toString(id));
            String urlString = url.toString();
            try {
                restTemplate.put(urlString, inventoryDTO);
            } catch (Exception e) {
                    log.error("Error Occured");
                throw new NoSuchElementException("No Such Inventory found for this id " + id);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();

        }
    }
}
