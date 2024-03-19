package com.company.glovo.service;

import com.company.glovo.dto.OrderDto;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderDto> getOrderById(Integer id);

    List<OrderDto> getOrders(Pageable pageable);

    Optional<OrderDto> saveNewOrder(OrderDto dto);

    boolean updateOrder(Integer id, OrderDto dto);

    boolean deleteOrder(Integer id);
}
