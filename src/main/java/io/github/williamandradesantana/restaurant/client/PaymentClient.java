package io.github.williamandradesantana.restaurant.client;

import io.github.williamandradesantana.restaurant.dtos.PaymentRequest;
import io.github.williamandradesantana.restaurant.dtos.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-client", url = "${payment.api.url}")
public interface PaymentClient {
    @PostMapping("/api/payments/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest request);
}
