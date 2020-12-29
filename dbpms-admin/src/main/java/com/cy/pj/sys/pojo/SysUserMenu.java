package com.cy.pj.sys.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象能力?能够迅速将业务转换为领域对象(pojo)
 * 基于此对象封装登录用户拥有访问权限的一级菜单和二级菜单
 */
@Data
public class SysUserMenu implements Serializable {
    private static final long serialVersionUID = 2152789115079901409L;
    private Integer id;
    /**菜单名称*/
    private String name;
    /**菜单url*/
    private String url;
    /**一级菜单对应的二级菜单*/
    private List<SysUserMenu> childs;
}
