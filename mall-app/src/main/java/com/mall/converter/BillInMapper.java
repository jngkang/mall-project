package com.mall.converter;

import com.mall.entity.BillIn;
import com.mall.model.BillInDTO;
import com.mall.query.BillInQuery;
import com.mall.query.BillInQueryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-07-09 16:01
 */
@Mapper
public interface BillInMapper {
    BillInMapper MAPPER = Mappers.getMapper(BillInMapper.class);

    BillInDTO bean2DTO(BillIn billIn);

    BillIn dto2bean(BillInDTO billInDTO);

    BillInQuery queryDTO2Query(BillInQueryDTO billInQueryDTO);

    BillInQueryDTO query2QueryDTO(BillInQuery billInQuery);

    List<BillInDTO> beanList2DTOList(List<BillIn> billIns);

    List<BillIn> dtoList2beanList(List<BillInDTO> billInDTOS);

    List<BillInQuery> queryDTOList2QueryList(List<BillInQueryDTO> billInQueryDTOS);

    List<BillInQueryDTO> queryList2QueryDTOList(List<BillInQuery> billMasterQueries);
}
