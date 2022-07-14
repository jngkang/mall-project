package com.mall.converter;

import com.mall.entity.BillOutItem;
import com.mall.model.BillOutItemDTO;
import com.mall.query.BillOutItemQuery;
import com.mall.query.BillOutItemQueryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BillOutItemMapper {

    BillOutItemMapper MAPPER = Mappers.getMapper(BillOutItemMapper.class);

    BillOutItemDTO bean2DTO(BillOutItem billOutItem);

    BillOutItem dto2bean(BillOutItemDTO billOutItemDTO);

    BillOutItemQuery queryDTO2Query(BillOutItemQueryDTO billOutItemQueryDTO);

    BillOutItemQueryDTO query2QueryDTO(BillOutItemQuery billOutItemQuery);

    List<BillOutItemDTO> beanList2DTOList(List<BillOutItem> billOutItems);

    List<BillOutItem> dtoList2beanList(List<BillOutItemDTO> billOutItemDTOS);

    List<BillOutItemQuery> queryDTOList2QueryList(List<BillOutItemQueryDTO> billOutItemQueryDTOS);

    List<BillOutItemQueryDTO> queryList2QueryDTOList(List<BillOutItemQuery> BillOutItemQueries);
}