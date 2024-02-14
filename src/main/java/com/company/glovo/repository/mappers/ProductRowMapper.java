package com.company.glovo.repository.mappers;

import com.company.glovo.dto.ProductDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<ProductDto> {

    @Override
    public ProductDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductDto productDto = new ProductDto();
        productDto.setId(rs.getInt("id"));
        productDto.setName(rs.getString("name"));
        productDto.setCost(rs.getDouble("cost"));
        return productDto;
    }
}
