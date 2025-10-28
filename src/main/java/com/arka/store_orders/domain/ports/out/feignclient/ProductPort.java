package com.arka.store_orders.domain.ports.out.feignclient;

import com.arka.store_orders.infrastructure.resources.Response.AvailableStockResponse;
import com.arka.store_orders.infrastructure.resources.Response.ReservationResponse;

public interface ProductPort {

    void reserveStock(Long productId, Integer quantity);
    void decrementStock(Long productId,Integer quantity);
    AvailableStockResponse recoveryStock(Long productId, Integer quantity);
    AvailableStockResponse getAvailableStock(Long productId);
}
