package com.company.glovo.controller;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrders() {
        ApiResponse<List<OrderDto>> response = new ApiResponse<>();
        List<OrderDto> orders = orderService.getOrders();
        response.setSuccess(true);
        response.setData(orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable("orderId") Integer orderId) {
        ApiResponse<OrderDto> response = new ApiResponse<>();
        Optional<OrderDto> order = orderService.getOrderById(orderId);
        if (order.isPresent()) {
            response.setSuccess(true);
            response.setData(order.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrderDto> createNewOrder(@RequestBody OrderDto orderDto) {
        if (!(orderService.saveNewOrder(orderDto) == null)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderDto> deleteOrderById(@PathVariable("orderId") Integer orderId) {
        if (orderService.deleteOrder(orderId)) {
            return ResponseEntity.noContent().build();
        } else return ResponseEntity.notFound().build();

    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrderById(@PathVariable("orderId") Integer orderId, @RequestBody OrderDto orderDto) {
        if (orderService.updateOrder(orderId, orderDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
