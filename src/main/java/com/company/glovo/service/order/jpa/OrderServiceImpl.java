package com.company.glovo.service.order.jpa;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.repository.jdbc.OrderJDBCRepository;
import com.company.glovo.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderJDBCRepository orderJDBCRepository;

    @Override
    public OrderDto getOrderById(Integer id) {
        return orderJDBCRepository.getById(id);
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderJDBCRepository.getAll();
    }

    @Override
    public void saveNewOrder(OrderDto dto) {
        orderJDBCRepository.save(dto);
    }

    @Override
    public void updateOrder(Integer id, OrderDto dto) {
        OrderDto order = orderJDBCRepository.getById(id);
        order.setDate(dto.getDate());
        order.setCost(dto.getCost());
        orderJDBCRepository.updateById(order);
    }

    @Override
    public void deleteOrder(Integer id) {
       orderJDBCRepository.deleteById(id);
    }
}
