package com.company.glovo.controller;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.dto.ProductDto;
import com.company.glovo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class TestController {

    private final OrderService orderService;

    private ProductDto pr1 = ProductDto.builder()
            .name("book")
            .cost(15.1).build();
    private OrderDto orderDto = OrderDto.builder()

            .cost(1111.1)
            /*.products(Collections.singletonList(pr1))*/.build();


    @GetMapping()

    public /*List<OrderDto>*/ void testService() {
        //return Collections.singletonList(orderService.getOrderById(1));
        // orderService.saveNewOrder(orderDto);
        //   return orderService.getOrders();
        // orderService.deleteOrder(4);
        orderService.updateOrder(1, orderDto);
    }
}
