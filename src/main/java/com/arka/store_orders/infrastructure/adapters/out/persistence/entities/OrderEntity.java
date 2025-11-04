package com.arka.store_orders.infrastructure.adapters.out.persistence.entities;
import com.arka.store_orders.domain.models.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class OrderEntity {

    @Id
    private UUID id;
    private LocalDateTime createAt;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> items = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    private Double total;
    private LocalDateTime updateAt;
    private String userId;
}
