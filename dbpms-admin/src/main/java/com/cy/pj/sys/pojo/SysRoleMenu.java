package com.cy.pj.sys.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 借助此对象存储基于角色id查询到的角色自身信息以及菜单id,这部分
 * 数据来自于sys_role,sys_role_menus这两张表.当数据来自多个表时,
 * 我们查询方案一般有如下三种:
 * 1)业务层发起多次单表查询
 * 2)业务层发起一次查询,数据层进行多表关联(join)查询
 * 3)业务层发起一次查询,数据层进行表嵌套查询
 */
@Data
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = -2671028987524519218L;
    /**角色id*/
    private Integer id;
    /**角色名称*/
    private String name;
    /**角色备注*/
    private String note;
    /**菜单id*/
    private List<Integer> menuIds;
}
