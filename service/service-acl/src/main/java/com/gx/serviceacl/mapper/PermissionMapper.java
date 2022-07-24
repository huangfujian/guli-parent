package com.gx.serviceacl.mapper;

import com.gx.serviceacl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author FL
 * @since 2022-06-28
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectAllPermissionValue();

    List<String> selectPermissionValueByUserId(@Param("id") String id);

    List<Permission> selectPermissionByUserId(@Param("id") String id);

}
