package com.arka.store_orders.domain.ports.out.eventsPort;

import com.arka.store_orders.domain.events.OrderCreatedEvent;

public interface OrderEventPublisher {
    void publishOrderCreatedEvent(OrderCreatedEvent event);
}
