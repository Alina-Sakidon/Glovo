package com.company.glovo.service;

import com.company.glovo.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderDto> getOrderById(Integer id);

    List<OrderDto> getOrders();

    boolean saveNewOrder(OrderDto dto);

    boolean updateOrder(Integer id, OrderDto dto);

    boolean deleteOrder(Integer id);
}
