package com.payment_processor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment_processor")
public class PaymentProcessorController {
    @GetMapping
    public String paymentProcessor() {
        return "Payment Processor called from payment service";
    }
}
