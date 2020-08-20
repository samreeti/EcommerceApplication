package com.cts.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/dealerFallBack")
    public Mono<String> dealerServiceFallBack()
    {

        return  Mono.just("Dealer service is taking too long time to respond or is down. please try again later");
    }

    @RequestMapping("/customerFallBack")
    public Mono<String> customerServiceFallBack()
    {
        return  Mono.just("Customer service is taking too long time to respond or is down. please try again later");
    }

    @RequestMapping("/inventoryFallBack")
    public Mono<String> inventoryServiceFallBack()
    {
        return  Mono.just("Inventory service is taking too long time to respond or is down. please try again later");
    }
}
