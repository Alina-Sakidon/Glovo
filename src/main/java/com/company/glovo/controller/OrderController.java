package com.company.glovo.controller;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getOrders() {
        Optional<List<OrderDto>> orders = orderService.getOrders();
        return orders.map(orderDtos -> ResponseEntity.ok().body(orderDtos)).orElseGet(() -> ResponseEntity.ok().build());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("orderId") Integer orderId) {
        return orderService.getOrderById(orderId)
                .map(oder -> ResponseEntity.ok().body(oder))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDto> createNewOrder(@RequestBody OrderDto orderDto) {
        if (orderService.saveNewOrder(orderDto)) {
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderDto> deleteOrderById(@PathVariable("orderId") Integer orderId) {
        if (orderService.deleteOrder(orderId)) {
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrderById(@PathVariable("orderId") Integer orderId, @RequestBody OrderDto orderDto) {
        if (orderService.updateOrder(orderId, orderDto)) {
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }
}
