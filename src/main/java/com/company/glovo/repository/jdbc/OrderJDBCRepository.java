package com.company.glovo.repository.jdbc;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.repository.mappers.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderJDBCRepository {

    private final String SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ";
    private final String SAVE_NEW_ORDER = "INSERT INTO orders(date, cost) VALUES (?, ?)";
    private final String UPDATE_ORDER = "UPDATE orders SET date = ?, cost = ? WHERE id = ";
    private final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ";

    private final JdbcTemplate jdbcTemplate;

    public Optional<OrderDto> getById(int id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_ORDER_BY_ID + id, new OrderRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<List<OrderDto>> getAll() {
        return Optional.of(jdbcTemplate.query(SELECT_ALL_ORDERS, new OrderRowMapper()));
    }

    public boolean save(OrderDto orderDto) {
        return jdbcTemplate.update(SAVE_NEW_ORDER, orderDto.getDate(), orderDto.getCost()) > 0;
    }

    public boolean updateById(OrderDto orderDto) {
        return jdbcTemplate.update(UPDATE_ORDER + orderDto.getId(), orderDto.getDate(), orderDto.getCost()) > 0;
    }

    public boolean deleteById(int id) {
        return jdbcTemplate.update(DELETE_ORDER_BY_ID + id) > 0;
    }
}
