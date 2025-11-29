package dev.commerce.dtos.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private UUID id;
    private String orderCode;
    private double totalAmount;
    private String status;
    private UUID usersId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
