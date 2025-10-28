package com.arka.store_orders.infrastructure.resources.Response;

import com.arka.store_orders.domain.models.OrderItem;
import com.arka.store_orders.domain.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponse {
    private UUID id;
    private LocalDateTime createAt;
    private List<OrderItem> items;
    private OrderStatus status;
    private Double total;
}
