package com.company.glovo.controller;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.model.Order;
import com.company.glovo.model.Product;
import com.company.glovo.repository.order.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
//https://www.baeldung.com/resttemplate-page-entity-response

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
    void shouldGetOrderById() throws JsonProcessingException {
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


        ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, ApiResponse.class);
        // Assertions.assertEquals(savedOrder.getId(), dto.getId()
        var a = responseEntity.getBody().getData();
        List<OrderDto> itemWithOwner = new ObjectMapper().readValue(a.toString(),List.class);
    }


}