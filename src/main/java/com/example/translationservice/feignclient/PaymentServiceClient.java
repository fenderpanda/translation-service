package com.example.translationservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "FeignClientPaymentService",
        url = "http://localhost:8003",
        path = "/api"
)
public interface PaymentServiceClient {

    @PostMapping("/payment")
    long makePayment(@RequestParam long fileId, @RequestParam int cost);
}
