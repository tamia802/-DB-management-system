package com.cy.pj.common.pojo;

import lombok.Data;

import java.io.Serializable;
/**通过此对象封装类似CheckBox的结构的数据*/
@Data
public class CheckBox implements Serializable {
    private static final long serialVersionUID = -3930756932197466333L;
    private Integer id;
    private String name;
}
