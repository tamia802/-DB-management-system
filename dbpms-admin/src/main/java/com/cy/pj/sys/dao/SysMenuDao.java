package com.cy.pj.sys.dao;

import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.pojo.SysMenu;
import com.cy.pj.sys.pojo.SysUserMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单数据层对象
 */
@Mapper
public interface SysMenuDao {

    List<SysUserMenu> findMenusByIds(List<Integer> menuIds);

    List<String> findUserPermissions(Integer userId);
    /**基于菜单id获取菜单对应授权标识*/
    List<String> findPermissions(List<Integer> menuIds);

    int insertObject(SysMenu menu);
    /**
     * 查询所有菜单中的ID,name,parentId字段的值,然后每行记录封装为一个Node对象,
     * 这样的对象可以适配类似zTree结构的数据呈现.
     * @return
     */
    @Select("select id,name,parentId from sys_menus")
    List<Node> findZtreeMenuNodes();

    /**查询所有菜单以及菜单对应的上级菜单名称
     * 使用map充当pojo的角色,有什么优势劣势?
     * 1)优势:代码简单,不需要写pojo了,一般适合工作量比较大,开发周期比较短的外包项目
     * 2)劣势:第一,可读性差(打开源码不知道内部要存什么),第二,值的类型不可控
     * */
    List<Map<String,Object>> findObjects();

}


