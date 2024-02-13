package com.company.glovo.repository.jdbc;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.repository.mappers.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderJDBCRepository {

    private final String SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ";
    private final String SAVE_NEW_ORDER = "INSERT INTO orders(date, cost) VALUES (?, ?)";
    private final String UPDATE_ORDER = "UPDATE orders SET date = ?, cost = ? WHERE id = ";
    private final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ";

    private final JdbcTemplate jdbcTemplate;

    public OrderDto getById(int id) {
        OrderDto order = jdbcTemplate.queryForObject(SELECT_ORDER_BY_ID + id, new OrderRowMapper());
        return order;
    }

    public List<OrderDto> getAll() {
        List<OrderDto> orders = jdbcTemplate.query(SELECT_ALL_ORDERS, new OrderRowMapper());
        return orders;
    }

    public void save(OrderDto orderDto) {
        jdbcTemplate.update(SAVE_NEW_ORDER, orderDto.getDate(), orderDto.getCost()/*, orderDto.getProducts()*/);
    }

    public void updateById(OrderDto orderDto) {
        jdbcTemplate.update(UPDATE_ORDER + orderDto.getId(), orderDto.getDate(), orderDto.getCost()/*, orderDto.getProducts()*/);
    }

    public void deleteById(int id) {
        jdbcTemplate.update(DELETE_ORDER_BY_ID + id);
    }

}
