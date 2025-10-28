package com.arka.store_orders.infrastructure.adapters.out.webclient.adapter;

import com.arka.store_orders.domain.ports.out.feignclient.PaymentPort;
import com.arka.store_orders.infrastructure.adapters.out.webclient.feign.PaymentClient;
import com.arka.store_orders.infrastructure.resources.Request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentWebAdapter implements PaymentPort {
    private final PaymentClient paymentClient;
    @Override
    public String processPayment(PaymentRequest paymentRequest) {
        return paymentClient.processPayment(paymentRequest);
    }

    @Override
    public boolean validPayment(String transactionId) {
        return paymentClient.validPayment(transactionId);
    }
}
