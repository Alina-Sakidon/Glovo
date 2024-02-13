package com.company.glovo.repository.mappers;

import com.company.glovo.dto.OrderDto;
import com.company.glovo.helpers.ParserHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderRowMapper implements RowMapper<OrderDto> {

    @Override
    public OrderDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(rs.getInt("id"));
        orderDto.setCost(rs.getDouble("cost"));
        orderDto.setDate((LocalDate) ParserHelper.parseValue(rs.getDate("date"), Date::toLocalDate));
        //todo do we need PRODUCTS list here?
        return orderDto;
    }

}
