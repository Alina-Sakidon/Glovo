package com.company.glovo.service.order;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.model.Order;
import com.company.glovo.repository.converter.OrderConverter;
import com.company.glovo.repository.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl testInstance;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderConverter orderConverter;

    @Mock
    private Page<Order> ordersPage;
    @Mock
    private Order order, order2;
    private OrderDto orderDto;

    @BeforeEach
    public void init() {
        orderDto = OrderDto.builder()
                .id(111)
                .cost(44.4)
                .date(LocalDate.now())
                .build();
    }

    @Test
    void shouldReturnOrderById() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(orderConverter.fromModel(order)).thenReturn(orderDto);

        Optional<OrderDto> result = testInstance.getOrderById(orderDto.getId());

        verify(orderRepository).findById(orderDto.getId());
        verify(orderConverter).fromModel(order);
        assertThat(result).isPresent();
        assertEquals(orderDto.getId(), result.get().getId());
    }

    @Test
    void shouldNotReturnOrderById() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        Optional<OrderDto> result = testInstance.getOrderById(orderDto.getId());

        verify(orderRepository).findById(orderDto.getId());
        verify(orderConverter, never()).fromModel(order);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllOrders() {
        List<Order> orders = List.of(order, order2);
        List<OrderDto> orderDtos = Arrays.asList(orderDto, OrderDto.builder()
                .id(99)
                .cost(99.9)
                .build());
        when(orderRepository.findAll(any(Pageable.class))).thenReturn(ordersPage);
        when(ordersPage.getContent()).thenReturn(orders);
        when(orderConverter.fromModel(orders)).thenReturn(orderDtos);
        Pageable pageRequest = PageRequest.of(0, ordersPage.getContent().size());


        List<OrderDto> result = testInstance.getOrders(pageRequest);

        assertNotNull(result);
        verify(orderRepository).findAll(pageRequest);
        verify(orderConverter).fromModel(orders);
        assertEquals(result.size(), 2);
    }

    @Test
    void shouldSaveNewOrder() {
        when(orderConverter.toModel(any(OrderDto.class))).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order2);
        when(orderConverter.fromModel(order2)).thenReturn(orderDto);

        Optional<OrderDto> result = testInstance.saveNewOrder(orderDto);

        assertNotNull(result);
        verify(orderConverter).toModel(orderDto);
        verify(orderConverter).fromModel((orderRepository).save(order));
        assertEquals(result.get(), orderDto);
    }

    @Test
    void shouldUpdateOrderById() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));
        when(orderConverter.toModel(order, orderDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        Boolean result = testInstance.updateOrder(orderDto.getId(), orderDto);

        verify(orderRepository).findById(orderDto.getId());
        verify(orderConverter).toModel(order, orderDto);
        assertNotNull(result);
        assertEquals(result, true);
    }

    @Test
    void shouldNotUpdateOrderById() {
        when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());

        Boolean result = testInstance.updateOrder(orderDto.getId(), orderDto);

        verify(orderRepository).findById(orderDto.getId());
        verify(orderConverter, never()).toModel(order, orderDto);
        verify(orderRepository, never()).save(order);
        assertNotNull(result);
        assertEquals(result, false);
    }

    @Test
    void shouldDeleteOrderById() {
        when(orderRepository.existsById(anyInt())).thenReturn(true);

        Boolean result = testInstance.deleteOrder(orderDto.getId());

        verify(orderRepository).existsById(orderDto.getId());
        verify(orderRepository).deleteById(orderDto.getId());
        assertNotNull(result);
        assertEquals(result, true);
    }

    @Test
    void shouldNotDeleteOrderById() {
        when(orderRepository.existsById(anyInt())).thenReturn(false);

        Boolean result = testInstance.deleteOrder(orderDto.getId());

        verify(orderRepository).existsById(orderDto.getId());
        verify(orderRepository, never()).deleteById(orderDto.getId());
        assertNotNull(result);
        assertEquals(result, false);
    }
}