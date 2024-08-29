package com.harsha.payment_service.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/payment_service")
public class PaymentServiceController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8081/";
    int count = 1;

    @GetMapping
    @CircuitBreaker(name = "payment_service", fallbackMethod = "PaymentServiceFallback")
    public String paymentService() {
        String url = BASE_URL + "payment_processor";
        return  restTemplate.getForObject(
                url,
                String.class
        );
    }

    @GetMapping("/retry")
    @Retry(name = "payment_service2", fallbackMethod = "PaymentServiceFallback")
    public String paymentService2() {
        String url = BASE_URL + "payment_processor";
        System.out.println("retry method called " + count++ + " times at " + new Date());
        return  restTemplate.getForObject(
                url,
                String.class
        );
    }

    @GetMapping("/ratelimiter")
    @RateLimiter(name = "payment_service", fallbackMethod = "PaymentServiceFallback")
    public String paymentService3() {
        String url = BASE_URL + "payment_processor";
//        return  restTemplate.getForObject(
//                url,
//                String.class
//        );
        return url;
    }

    public String PaymentServiceFallback(Exception exception){
        return "This is fallback method for Payment Service";
    }
}
