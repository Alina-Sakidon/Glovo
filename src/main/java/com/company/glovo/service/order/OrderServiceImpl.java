package com.company.glovo.service.order;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.exception.ResourceNotFoundException;
import com.company.glovo.model.Order;
import com.company.glovo.repository.converter.OrderConverter;
import com.company.glovo.repository.jdbc.OrderJDBCRepository;
import com.company.glovo.repository.order.OrderRepository;
import com.company.glovo.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    //  private final OrderJDBCRepository orderJDBCRepository;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    @Override
    public Optional<OrderDto> getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return Optional.of(orderConverter.fromModel(order.get()));
    }
        return Optional.empty();
}

@Override
public Optional<List<OrderDto>> getOrders() {
    // return orderJDBCRepository.getAll();
    return null;
}

@Override
public boolean saveNewOrder(OrderDto dto) {
    //  return orderJDBCRepository.save(dto);
    return true;
}

@Override
public boolean updateOrder(Integer id, OrderDto dto) {
       /* Optional<OrderDto> order = orderJDBCRepository.getById(id);
        if (order.isEmpty()) {
            throw new ResourceNotFoundException("Order not exist with id: " + id);
        } else {
            order.get().setDate(dto.getDate());
            order.get().setCost(dto.getCost());
            return orderJDBCRepository.updateById(order.get());
        }*/
    return true;
}

@Override
public boolean deleteOrder(Integer id) {
    // return orderJDBCRepository.deleteById(id);
    return true;
}
}
