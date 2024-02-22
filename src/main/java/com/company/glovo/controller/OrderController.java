package com.company.glovo.controller;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = orderService.getOrders();
        return ResponseEntity.ok().body(orders);
    }

   /* @GetMapping()
    public ApiResponse<List<OrderDto>> getOrders() {
        ApiResponse<List<OrderDto>> response = new ApiResponse<>();
        Optional<List<OrderDto>> orders = orderService.getOrders();
        if (orders.isPresent()) {
            response.setSuccess(true);
            response.setData(orders.get());
        }
        return response;
    }*/

   /* @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable("orderId") Integer orderId) {
        ApiResponse<OrderDto> response = new ApiResponse<>();
        Optional<OrderDto> order = orderService.getOrderById(orderId);
        if (order.isPresent()) {
            response.setSuccess(true);
            response.setData(order.get());
            response.setStatus(HttpStatus.CREATED);
            return response;
            // return ResponseEntity.ok().body(response);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND);
            return response;
        }
        //ResponseEntity.notFound().build();
    }*/

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
