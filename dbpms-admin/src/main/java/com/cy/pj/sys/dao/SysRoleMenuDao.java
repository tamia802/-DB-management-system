package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleMenuDao {

    /**
     * 基于多个角色id找到对应菜单id
     * @param roleIds
     * @return
     */
    List<Integer> findMenuIdsByRoleIds(List<Integer> roleIds);

    @Delete("delete from sys_role_menus where role_id=#{roleId}")
    int deleteObjectsByRoleId(Integer roleId);

    @Select("select menu_id from sys_role_menus where role_id=#{roleId}")
    List<Integer> findMenuIdsByRoleId(Integer roleId);
    /**保存角色和菜单关系数据*/
    int insertObjects(Integer roleId,Integer[]menuIds);
}
