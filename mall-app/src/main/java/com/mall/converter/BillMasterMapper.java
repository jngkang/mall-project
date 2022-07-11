package com.mall.converter;

import com.mall.entity.BillMaster;
import com.mall.model.BillMasterDTO;
import com.mall.query.BillMasterQuery;
import com.mall.query.BillMasterQueryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-07-09 16:01
 */
@Mapper
public interface BillMasterMapper {
    BillMasterMapper MAPPER = Mappers.getMapper(BillMasterMapper.class);

    BillMasterDTO bean2DTO(BillMaster billMaster);

    BillMaster dto2bean(BillMasterDTO billMasterDTO);

    BillMasterQuery queryDTO2Query(BillMasterQueryDTO billMasterQueryDTO);

    BillMasterQueryDTO query2QueryDTO(BillMasterQuery billMasterQuery);

    List<BillMasterDTO> beanList2DTOList(List<BillMaster> billMasters);

    List<BillMaster> dtoList2beanList(List<BillMasterDTO> billMasterDTOs);

    List<BillMasterQuery> queryDTOList2QueryList(List<BillMasterQueryDTO> billMasterQueryDTOs);

    List<BillMasterQueryDTO> queryList2QueryDTOList(List<BillMasterQuery> billMasterQueries);
}
