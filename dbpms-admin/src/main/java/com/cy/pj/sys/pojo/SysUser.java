package com.cy.pj.sys.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 3776304095517793129L;
    private Integer id;
    private String username;
    private String password;//md5
    private String salt;//加密盐
    private String email;
    private String mobile;
    private Integer valid=1;
    private Integer deptId;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;

}
