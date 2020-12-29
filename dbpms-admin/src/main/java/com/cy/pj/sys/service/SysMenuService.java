package com.cy.pj.sys.service;

import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.pojo.SysMenu;
import com.cy.pj.sys.pojo.SysUserMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
    //金字塔原理
    /**基于用户id查询用户拥有访问权限的一级和二级菜单
     * FAQ:如何基于用户id找到菜单相关的信息
     * 1)第一:用户表与菜单表没有直接关系
     * 2)第二:可以通过用户角色关系表找到用户对应的角色id
     * 3)第三:基于角色id可以从角色菜单关系表获取角色对应的菜单id
     * 4)第四:获取到了菜单id就可以在菜单表中获取菜单相关信息了
     * */
    List<SysUserMenu> findUserMenus(Integer userId);
    int saveObject(SysMenu sysMenu);
    List<Node> findZtreeMenuNodes();
    List<Map<String,Object>> findObjects();
}
