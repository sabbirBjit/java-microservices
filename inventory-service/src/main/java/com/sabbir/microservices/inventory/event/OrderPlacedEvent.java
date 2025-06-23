package com.sabbir.microservices.inventory.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderPlacedEvent(
    String orderNumber,
    String skuCode,
    Integer quantity,
    BigDecimal price,
    LocalDateTime timestamp
) {
}
