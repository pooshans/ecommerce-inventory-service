package com.talentica.ecommerce.inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    private String message;
    private String details;
    private LocalDateTime timestamp;
    private int status;
    private String path;

    public ErrorResponseDTO(String message, String details, int status, String path) {
        this.message = message;
        this.details = details;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}