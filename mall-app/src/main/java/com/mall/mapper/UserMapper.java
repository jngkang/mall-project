package com.mall.mapper;

import com.mall.annotation.PageX;
import com.mall.model.User;
import com.mall.model.query.UserQuery;
import com.mall.model.dto.UserLoginDTO;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-10 14:46
 */
@Mapper
public interface UserMapper {

    /**
     * 登录
     *
     * @param userQuery 根据用户id查询
     * @return java.util.List<com.mall.model.VO.UserVO> 返回用户的信息
     */
    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<UserLoginDTO> select(UserQuery userQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(User user);

    //@UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    //public Integer update(User user);

    //@UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    //public Integer updateStatus(OrderMasterStatus orderMasterStatus);
}