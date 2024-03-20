package com.company.glovo.mapper;

import com.company.glovo.dto.ProductDto;
import com.company.glovo.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductDto productToProductDto(Product product);
    Product productDtoToProduct(ProductDto dto);
    List<ProductDto> toProductDtoList(List<Product> products);
    List<Product> toProductList(List<ProductDto> dtos);
}
