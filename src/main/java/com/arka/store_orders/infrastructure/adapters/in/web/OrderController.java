package com.arka.store_orders.infrastructure.adapters.in.web;

import com.arka.store_orders.domain.ports.in.OrderUseCases;
import com.arka.store_orders.infrastructure.mapper.OrderItemMapper;
import com.arka.store_orders.infrastructure.mapper.OrderMapper;
import com.arka.store_orders.infrastructure.resources.Request.ItemQuantityUpdate;
import com.arka.store_orders.infrastructure.resources.Request.OrderItemRequest;
import com.arka.store_orders.infrastructure.resources.Request.OrderRequest;
import com.arka.store_orders.infrastructure.resources.Response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderUseCases useCases;
    private final OrderMapper mapper;
    private final OrderItemMapper itemMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request){
        OrderResponse orderResponse=mapper.domainToResponse(useCases.createOrder(request));
        return ResponseEntity.ok(orderResponse);
    }
    @PostMapping("/process/{orderId}")
    public ResponseEntity<OrderResponse> processOrder(@PathVariable("orderId")UUID orderId){
        OrderResponse orderResponse=mapper.domainToResponse(useCases.processOrder(orderId));
        return ResponseEntity.ok(orderResponse);
    }
    @PutMapping("/update/{orderId}/{itemId}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable("orderId")UUID orderId,
            @PathVariable("itemId")Long itemId,
            @RequestBody ItemQuantityUpdate quantityUpdate){
        OrderResponse orderResponse=mapper.domainToResponse(useCases.updateOrder(orderId,itemId,quantityUpdate));
        return ResponseEntity.ok(orderResponse);
    }
    @PutMapping("/add-item/{orderId}")
    public ResponseEntity<OrderResponse> addItem(@PathVariable("orderId")UUID orderId,@RequestBody OrderItemRequest item){
        OrderResponse orderResponse=mapper.domainToResponse(useCases.addItem(orderId, itemMapper.requestToDomain(item)));
        return ResponseEntity.ok(orderResponse);
    }
    @PutMapping("/remove-item/{orderId}/{itemId}")
    public ResponseEntity<OrderResponse> removeItem(@PathVariable("orderId")UUID orderId,@PathVariable("itemId")Long itemId){
        OrderResponse orderResponse=mapper.domainToResponse(useCases.deleteItem(orderId, itemId));
        return ResponseEntity.ok(orderResponse);
    }
    @PostMapping("/accept/{orderId}/{userId}")
    public ResponseEntity<OrderResponse> acceptOrder(
            @PathVariable("orderId")UUID orderId,
            @PathVariable("userId")String userId){
        OrderResponse orderResponse=mapper.domainToResponse(useCases.acceptOrder(orderId,userId));
        return ResponseEntity.ok(orderResponse);
    }
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId")UUID orderId){
        useCases.cancelOrder(orderId);
        String message="Order was canceled Successfully";
        return ResponseEntity.ok(message);
    }
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrder(){
        List<OrderResponse> orders=useCases.getOrders().stream()
                .map(mapper::domainToResponse).toList();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse>getById(@PathVariable("orderId")UUID orderId){
        return useCases.getOrderById(orderId).map(mapper::domainToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
