package com.arka.store_orders.domain.ports.out.feignclient;

import com.arka.store_orders.infrastructure.resources.Request.ShippingRequest;

public interface ShippingPort {
    void sendOrder(ShippingRequest request);
}
