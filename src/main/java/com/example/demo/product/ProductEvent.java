package com.example.demo.product;

import lombok.Data;

@Data
public class ProductEvent {
    private String eventId;
    private String eventType;
    
    public ProductEvent() {}
    public ProductEvent(String eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
    }
}
