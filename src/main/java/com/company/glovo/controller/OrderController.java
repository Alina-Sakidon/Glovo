package com.company.glovo.controller;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.company.glovo.controller.AppConstants.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrders(@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                                 @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                 @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
                                                                 @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        ApiResponse<List<OrderDto>> response = new ApiResponse<>();
        List<OrderDto> orders = orderService.getOrders(pageable);
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
    public ResponseEntity<ApiResponse<OrderDto>> createNewOrder(@RequestBody OrderDto orderDto) {
        ApiResponse<OrderDto> response = new ApiResponse<>();
        Optional<OrderDto> order = orderService.saveNewOrder(orderDto);
        if (order.isPresent()) {
            log.debug("Order was created" + order.get());
            response.setSuccess(true);
            response.setData(order.get());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        log.debug("Order wasn't created");
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDto>> deleteOrderById(@PathVariable("orderId") Integer orderId) {
        if (orderService.deleteOrder(orderId)) {
            log.debug("Order with id {} was deleted", orderId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrderById(@PathVariable("orderId") Integer orderId, @RequestBody OrderDto orderDto) {
        if (orderService.updateOrder(orderId, orderDto)) {
            log.debug("Order was updated ");
            return ResponseEntity.ok().build();
        }
        log.debug("Order wasn't updated ");
        return ResponseEntity.notFound().build();
    }
}
