package com.arka.store_orders.domain.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {

    private Long id;
    private Long productId;
    private Integer quantity;
    private Double price;
    private Double amount;
    private UUID orderId;

    public Double calculateAmount(){
        Double calculatedAmount = this.price * this.quantity;
        setAmount(calculatedAmount);
        return calculatedAmount;
    }
}
