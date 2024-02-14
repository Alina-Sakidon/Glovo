package com.company.glovo.controller;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class TestController {

    private final OrderService orderService;

    private OrderDto orderDto = OrderDto.builder()
            .date(LocalDate.now())
            .cost(12569874.5).build();

    @GetMapping()
    public Optional<List<OrderDto>> /*Optional<OrderDto>*//* void*/ testService() {
        // return orderService.getOrderById(22);
        // orderService.saveNewOrder(orderDto);
          return orderService.getOrders();
        // orderService.deleteOrder(4);
        //orderService.updateOrder(1, orderDto);
    }
}
