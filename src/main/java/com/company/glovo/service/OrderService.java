package com.company.glovo.service;

import com.company.glovo.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderDto> getOrderById(Integer id);

    Optional<List<OrderDto>> getOrders();

    void saveNewOrder(OrderDto dto);

    void  updateOrder(Integer id, OrderDto dto);

    void deleteOrder(Integer id);
}
