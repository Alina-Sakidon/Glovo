package com.company.glovo.service.order.jpa;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private List<OrderDto> orders;

    @Override
    public OrderDto getOrderById(Integer id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<OrderDto> getOrders() {
        return orders;
    }

    @Override
    public void saveOrder(OrderDto dto) {
        orders = new ArrayList<>();
        orders.add(dto);
    }

    @Override
    public void updateOrder(Integer id, OrderDto dto) {
        orders.stream().filter(o -> o.getId().equals(id))
                .map(f -> new OrderDto(dto.getId(), dto.getDate(), dto.getCost(), dto.getProducts()));
    }

    @Override
    public void deleteOrder(Integer id) {
        List<OrderDto> operatedList = new ArrayList<>();
        orders.stream().filter(o -> o.getId().equals(id)).forEach(operatedList::add);
        orders.removeAll(operatedList);
    }
}
