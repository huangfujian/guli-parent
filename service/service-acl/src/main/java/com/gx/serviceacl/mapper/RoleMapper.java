package com.gx.serviceacl.mapper;

import com.gx.serviceacl.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author FL
 * @since 2022-06-28
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRoleByUserId(@Param("id") String id);
}
