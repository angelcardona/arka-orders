package com.arka.store_orders.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
