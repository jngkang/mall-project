package com.mall.converter;

import com.mall.entity.BillOut;
import com.mall.model.BillOutDTO;
import com.mall.query.BillOutQuery;
import com.mall.query.BillOutQueryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BillOutMapper {

    BillOutMapper MAPPER = Mappers.getMapper(BillOutMapper.class);

    BillOutDTO bean2DTO(BillOut billOut);

    BillOut dto2bean(BillOutDTO billOutDTO);

    BillOutQuery queryDTO2Query(BillOutQueryDTO billOutQueryDTO);

    BillOutQueryDTO query2QueryDTO(BillOutQuery billOutQuery);

    List<BillOutDTO> beanList2DTOList(List<BillOut> billOuts);

    List<BillOut> dtoList2beanList(List<BillOutDTO> billOutDTOS);

    List<BillOutQuery> queryDTOList2QueryList(List<BillOutQueryDTO> billOutQueryDTOS);

    List<BillOutQueryDTO> queryList2QueryDTOList(List<BillOutQuery> BillOutQueries);
}