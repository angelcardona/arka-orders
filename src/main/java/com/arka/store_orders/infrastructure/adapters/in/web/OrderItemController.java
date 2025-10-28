package com.arka.store_orders.infrastructure.adapters.in.web;

import com.arka.store_orders.domain.models.OrderItem;
import com.arka.store_orders.domain.ports.in.OrderItemUseCases;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemUseCases useCases;
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<OrderItem> deleteItem(@PathVariable("itemId")Long itemId){
        useCases.delete(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{itemId}")
    public ResponseEntity<OrderItem> updateItem(
            @PathVariable("itemId")Long itemId,
            @RequestBody OrderItem item){
        OrderItem updateItem=useCases.updateOrderItem(itemId,item);
        return ResponseEntity.ok(updateItem);
    }
}
