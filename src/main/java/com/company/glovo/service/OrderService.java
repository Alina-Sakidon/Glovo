package com.company.glovo.service;

import com.company.glovo.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto getOrderById(Integer id);

    List<OrderDto> getOrders();

    void saveOrder(OrderDto dto);

    void  updateOrder(Integer id, OrderDto dto);

    void deleteOrder(Integer id);
}
