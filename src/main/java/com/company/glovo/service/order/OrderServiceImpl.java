package com.company.glovo.service.order;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.mapper.OrderMapper;
import com.company.glovo.model.Order;
import com.company.glovo.repository.order.OrderRepository;
import com.company.glovo.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    @Override
    public Optional<OrderDto> getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            log.debug("Order with id {} found in DB ", id);
            return Optional.of(orderMapper.orderToOrderDto(order.get()));
        }
        log.debug("Order with id {} wasn't found in DB ", id);
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
            log.debug("Order with id {} found in DB ", id);
            Order initial = order.get();
            Order updated = orderMapper.toTarget(dto, initial);
            orderRepository.save(updated);
            return true;
        }
        log.debug("Order wasn't updated in DB");
        return false;
    }

    @Override
    public boolean deleteOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            log.debug("Order with id {} exist in DB ", id);
            orderRepository.deleteById(id);
            return true;
        }
        log.debug("Order with id {} wasn't found in DB ", id);
        return false;
    }
}
