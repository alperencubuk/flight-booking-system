package com.iyzico.challenge.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
public class PaymentServiceClients {

    private final IyzicoPaymentService iyzicoPaymentService;

    public PaymentServiceClients(IyzicoPaymentService iyzicoPaymentService) {
        this.iyzicoPaymentService = iyzicoPaymentService;
    }

    @Async
    public CompletableFuture<String> call(BigDecimal price) {
        iyzicoPaymentService.pay(price);
        return CompletableFuture.completedFuture("success");
    }
}
