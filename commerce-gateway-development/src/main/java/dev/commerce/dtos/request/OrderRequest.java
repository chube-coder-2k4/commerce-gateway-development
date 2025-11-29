package dev.commerce.dtos.request;

import dev.commerce.dtos.common.PaymentMethod;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
        private PaymentMethod paymentMethod;
        private String shippingAddress;
}
