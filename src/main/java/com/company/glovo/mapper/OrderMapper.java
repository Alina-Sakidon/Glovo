package com.company.glovo.mapper;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.model.Order;
import org.mapstruct.*;

import java.util.List;

@Mapper(uses = {ProductMapper.class}, componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "product", source = "products")
    Order orderDtoToOrder(OrderDto dto);

    @Mapping(target = "products", source = "product")
    OrderDto orderToOrderDto(Order order);

    List<OrderDto> toOrderDtoList(Iterable<Order> orders);

    @Mapping(target = "product", source = "products", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order toTarget(OrderDto dto, @MappingTarget Order order);
}
