package com.company.glovo.service.order;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.model.Order;
import com.company.glovo.repository.converter.OrderConverter;
import com.company.glovo.repository.order.OrderRepository;
import com.company.glovo.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

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
    public List<OrderDto> getOrders(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        List<Order> orders = page.getContent();
        return orderConverter.fromModel(orders);
    }

    @Override
    public OrderDto saveNewOrder(OrderDto dto) {
        Order order = orderConverter.toModel(dto);
        return orderConverter.fromModel(orderRepository.save(order));
    }

    @Override
    public boolean updateOrder(Integer id, OrderDto dto) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order initial = order.get();
            Order updated = orderConverter.toModel(initial, dto);
            orderRepository.save(updated);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
