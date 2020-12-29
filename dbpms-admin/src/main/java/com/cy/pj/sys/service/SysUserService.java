package com.cy.pj.sys.service;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.pojo.SysUserDept;

import java.util.Map;

public interface SysUserService {
      /**
       * 修改密码
       * @param sourcePassword 原密码
       * @param newPassword 新密码
       * @param confirmPassword 确认密码
       * @return
       */
      int updatePassword(String sourcePassword,String newPassword,String confirmPassword);

      Map<String,Object> findById(Integer id);

      int updateObject(SysUser entity, Integer[]roleIds);

      int saveObject(SysUser entity,Integer[]roleIds);
      /**
       * 禁用启用
       * @param id
       * @param valid
       * @return
       */
      int validById(Integer id,Integer valid);

      PageObject<SysUserDept> findPageObjects(String username,Integer pageCurrent);
}
