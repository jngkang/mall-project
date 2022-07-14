package com.mall.converter;

import com.mall.entity.Product;
import com.mall.model.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDTO bean2DTO(Product product);

    Product dto2bean(ProductDTO productDTO);

    List<ProductDTO> beanList2DTOList(List<Product> products);

    List<Product> dtoList2beanList(List<ProductDTO> productDTOS);
}