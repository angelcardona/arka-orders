package com.arka.store_orders.infrastructure.adapters.out.webclient.adapter;

import com.arka.store_orders.domain.ports.out.feignclient.ShippingPort;
import com.arka.store_orders.infrastructure.adapters.out.webclient.feign.ShippingClient;
import com.arka.store_orders.infrastructure.resources.Request.ShippingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShippingAdapter implements ShippingPort {
    private final ShippingClient  shippingClient;
    @Override
    public void sendOrder(ShippingRequest request) {
        shippingClient.sendShipping(request);
    }

}
