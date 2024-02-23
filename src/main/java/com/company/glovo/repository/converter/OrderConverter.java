package com.company.glovo.repository.converter;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.dto.ProductDto;
import com.company.glovo.model.Order;
import com.company.glovo.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class OrderConverter {

    public OrderDto fromModel(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .date(order.getDate())
                .cost(order.getCost())
                .products(productsFromModel(order.getProducts()))
                .build();
    }

    public List<OrderDto> fromModel(Iterable<Order> orders) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orders) {
            orderDtoList.add(fromModel(order));
        }
        return orderDtoList;
    }

    public Order toModel(OrderDto dto) {
        return Order.builder()
                .date(dto.getDate())
                .cost(dto.getCost())
                .products(productsToModel(dto.getProducts()))
                .build();
    }

    public Order toModel(Order order, OrderDto orderDto){
        order.setDate(orderDto.getDate());
        order.setCost(orderDto.getCost());
        order.setProducts(productsToModel(orderDto.getProducts()));
        return order;
    }

    private List<ProductDto> productsFromModel(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        if (!products.isEmpty()) {
            for (Product product : products) {
                ProductDto productDto = ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .cost(product.getCost())
                        .build();
                productDtoList.add(productDto);
            }
        }
        return productDtoList;
    }

    private List<Product> productsToModel(List<ProductDto> dtos) {
        List<Product> products = new ArrayList<>();
        if (!dtos.isEmpty()) {
            for (ProductDto dto : dtos) {
                Product product = Product.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .cost(dto.getCost())
                        .build();
                products.add(product);
            }
        }
        return products;
    }
}
