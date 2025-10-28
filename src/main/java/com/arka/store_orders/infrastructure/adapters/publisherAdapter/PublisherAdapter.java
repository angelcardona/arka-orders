package com.arka.store_orders.infrastructure.adapters.publisherAdapter;

import com.arka.store_orders.domain.events.OrderCreatedEvent;
import com.arka.store_orders.domain.ports.out.eventsPort.OrderEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PublisherAdapter implements OrderEventPublisher {
    @Override
    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("Order Created Event");
    }
}
