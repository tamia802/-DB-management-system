package com.cy.pj.sys.dao;

import com.cy.pj.common.pojo.CheckBox;
import com.cy.pj.sys.pojo.SysRole;
import com.cy.pj.sys.pojo.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleDao {
    @Select("select id,name from sys_roles")
    List<CheckBox> findRoles();

    //@Select("select id,name,note from sys_roles where id=#{id}")
    SysRoleMenu findById(Integer id);

    /**修改角色自身信息*/
    int updateObject(SysRole entity);

    /**保存角色自身信息*/
    int insertObject(SysRole entity);

    /**基于角色名查询总记录数*/
    int getRowCount(String name);
    /**
     * 基于条件查询当前页角色信息
     * @param name 角色名
     * @param startIndex 起始位置
     * @param pageSize 页面大小(每页最多显示多少条记录)
     * @return 当前页记录(一行记录映射为一个SysRole对象)
     */
    List<SysRole> findPageObjects(String name,Integer startIndex,Integer pageSize);
}
