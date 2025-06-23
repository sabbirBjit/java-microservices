package com.sabbir.microservices.order.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderPlacedEvent(
    String orderNumber,
    String skuCode,
    Integer quantity,
    BigDecimal price,
    LocalDateTime timestamp
) {
    public static OrderPlacedEvent of(String orderNumber, String skuCode, Integer quantity, BigDecimal price) {
        return new OrderPlacedEvent(orderNumber, skuCode, quantity, price, LocalDateTime.now());
    }
}
