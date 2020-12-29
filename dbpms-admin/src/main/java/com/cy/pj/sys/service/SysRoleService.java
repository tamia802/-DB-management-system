package com.cy.pj.sys.service;

import com.cy.pj.common.pojo.CheckBox;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysRole;
import com.cy.pj.sys.pojo.SysRoleMenu;

import java.util.List;

public interface SysRoleService {

     List<CheckBox> findRoles();

     SysRoleMenu findById(Integer id);

     /**保存角色以及角色对应的菜单信息*/
     int updateObject(SysRole entity,Integer[]menuIds);

     /**保存角色以及角色对应的菜单信息*/
     int saveObject(SysRole entity,Integer[]menuIds);
     /**
     * 基于角色名分页查询角色信息
     * @param name 角色名
     * @param pageCurrent 当前页码值
     * @return  查询到的当前页角色信息以及分页信息
     */
     PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);
}
