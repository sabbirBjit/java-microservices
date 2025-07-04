package com.sabbir.microservices.order.service;

import com.sabbir.microservices.order.client.InventoryClient;
import com.sabbir.microservices.order.dto.OrderRequest;
import com.sabbir.microservices.order.event.OrderPlacedEvent;
import com.sabbir.microservices.order.model.Order;
import com.sabbir.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setSkuCode(orderRequest.skuCode());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);
            
            // Publish order placed event
            OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.of(
                order.getOrderNumber(),
                order.getSkuCode(),
                order.getQuantity(),
                order.getPrice()
            );
            
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("Order placed event published for order: {}", order.getOrderNumber());
        }else {
            throw new RuntimeException("Product with SKU code " + orderRequest.skuCode() + " is not in stock.");
        }


    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}



