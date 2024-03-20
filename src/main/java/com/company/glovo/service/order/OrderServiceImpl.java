package com.company.glovo.service.order;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.mapper.OrderMapper;
import com.company.glovo.model.Order;
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
    private final OrderMapper orderMapper;


    @Override
    public Optional<OrderDto> getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return Optional.of(orderMapper.orderToOrderDto(order.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<OrderDto> getOrders(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        List<Order> orders = page.getContent();
        return orderMapper.toOrderDtoList(orders);
    }

    @Override
    public Optional<OrderDto> saveNewOrder(OrderDto dto) {
        Order order = orderMapper.orderDtoToOrder(dto);
        Optional<Order> savedOrder = Optional.of(orderRepository.save(order));
        return savedOrder.map(orderMapper::orderToOrderDto);
    }

    @Override
    public boolean updateOrder(Integer id, OrderDto dto) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order initial = order.get();
            Order updated = orderMapper.toTarget(dto,initial);
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
