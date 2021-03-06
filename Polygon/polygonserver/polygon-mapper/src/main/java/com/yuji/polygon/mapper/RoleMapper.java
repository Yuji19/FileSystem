package com.yuji.polygon.mapper;

import com.yuji.polygon.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @interface: RoleMapper
 * @description: TODO
 * @author: yuji
 * @create: 2020-11-11 16:00:00
 */

@Mapper
public interface RoleMapper {

    int insertRole(Role role);

    List<Role> getRoleByEmployeeId(int eid);

    int countTotalRole(String name);

    List<Role> getRolePage(@Param("name") String name, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    List<Role> getAllRole();

    int deleteRoleById(@Param("ids") int[] ids);

    int updateRoleBase(Role role);
}
