package com.arka.store_orders.infrastructure.adapters.out.webclient.adapter;

import com.arka.store_orders.domain.ports.out.feignclient.ProductPort;
import com.arka.store_orders.infrastructure.adapters.out.webclient.feign.ProductClient;
import com.arka.store_orders.infrastructure.resources.Response.AvailableStockResponse;
import com.arka.store_orders.infrastructure.resources.Response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductWebAdapter implements ProductPort {
    private final ProductClient productClient;
    @Override
    public void reserveStock(Long productId, Integer quantity) {
        productClient.reserveStock(productId,quantity);
    }
    @Override
    public void decrementStock(Long productId, Integer quantity) {
            productClient.decrementStock(productId,quantity);
    }

    @Override
    public AvailableStockResponse recoveryStock(Long productId, Integer quantity) {
             return productClient.recoveryStock(productId,quantity);
    }

    @Override
    public AvailableStockResponse getAvailableStock(Long productId) {
        return productClient.getAvailableStock(productId);
    }
}
