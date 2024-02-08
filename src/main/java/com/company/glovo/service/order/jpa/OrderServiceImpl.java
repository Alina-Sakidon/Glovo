package com.company.glovo.service.order.jpa;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.service.OrderService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    @NonNull
    private List<OrderDto> orders;

    @Override
    public OrderDto getOrderById(Integer id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst().orElseThrow(NullPointerException::new);
    }

    @Override
    public List<OrderDto> getOrders() {
        return orders;
    }

    @Override
    public void saveOrder(OrderDto dto) {
        orders.add(dto);
    }

    @Override
    public void updateOrder(Integer id, OrderDto dto) {
        orders.stream().filter(o -> o.getId().equals(id)).findFirst()
                .map(f -> new OrderDto(dto.getId(), dto.getDate(), dto.getCost(), dto.getProducts()))
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public void deleteOrder(Integer id) {
        OrderDto order = orders.stream().filter(o -> o.getId().equals(id)).findFirst()
                .orElseThrow(NullPointerException::new);
        orders.remove(order);
    }
}
