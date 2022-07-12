package com.mall.converter;

import com.mall.entity.BillInItem;
import com.mall.model.BillInItemDTO;
import com.mall.query.BillInItemQuery;
import com.mall.query.BillInItemQueryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BillInItemMapper {

    BillInItemMapper MAPPER = Mappers.getMapper(BillInItemMapper.class);

    BillInItemDTO bean2DTO(BillInItem billInItem);

    BillInItem dto2bean(BillInItemDTO billInItemDTO);

    BillInItemQuery queryDTO2Query(BillInItemQueryDTO billInItemQueryDTO);

    BillInItemQueryDTO query2QueryDTO(BillInItemQuery billInItemQuery);

    List<BillInItemDTO> beanList2DTOList(List<BillInItem> billInItems);

    List<BillInItem> dtoList2beanList(List<BillInItemDTO> billInItemDTOS);

    List<BillInItemQuery> queryDTOList2QueryList(List<BillInItemQueryDTO> billInItemQueryDTOS);

    List<BillInItemQueryDTO> queryList2QueryDTOList(List<BillInItemQuery> BillInItemQueries);
}