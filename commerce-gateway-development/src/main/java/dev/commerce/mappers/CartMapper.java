package dev.commerce.mappers;

import dev.commerce.dtos.response.CartResponse;
import dev.commerce.entitys.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse toResponse(Cart cart);
}
