package com.company.glovo.controller;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.dto.ProductDto;
import com.company.glovo.model.Order;
import com.company.glovo.model.Product;
import com.company.glovo.repository.order.OrderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OrderControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldGetOrderById() {
        Order order = Order.builder()
                .date(LocalDate.now())
                .cost(91.1)
                .product(List.of(Product.builder()
                        .cost(22.2)
                        .name("p")
                        .build()))
                .build();
        Order savedOrder = orderRepository.save(order);
        String url = "http://localhost:" + port + "/api/v1/orders/";

        ResponseEntity<ApiResponse<List<OrderDto>>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        List<OrderDto> dtos = Objects.requireNonNull(responseEntity.getBody()).getData();

        Assertions.assertEquals(savedOrder.getId(), dtos.get(0).getId());
    }

    @Test
    void shouldCreateNewOrder() {
        OrderDto dto = OrderDto.builder()
                .date(LocalDate.now())
                .cost(7.7)
                .products(List.of(ProductDto.builder()
                        .cost(7.2)
                        .name("new")
                        .build()))
                .build();
        String url = "http://localhost:" + port + "/api/v1/orders/";
        HttpEntity<OrderDto> request = new HttpEntity<>(dto);

        ResponseEntity<ApiResponse<OrderDto>> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<>() {
        });

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        OrderDto createdDto = Objects.requireNonNull(response.getBody()).getData();
        Assertions.assertEquals(dto.getProducts().get(0).getName(), createdDto.getProducts().get(0).getName());

        orderRepository.deleteById(createdDto.getId());
    }
}