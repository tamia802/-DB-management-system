package com.cy.pj.sys.dao;

import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.pojo.SysUserDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**通过此Dao操作用户模块的数据*/
@Mapper
public interface SysUserDao {
    /**基于id修改账户密码*/
    @Update("update sys_users set password=#{newPassword},salt=#{newSalt} where id=#{id}")
    int updatePassword(String newPassword,String newSalt,Integer id);

    @Select("select * from sys_users where username=#{username}")
    SysUser findUserByUserName(String username);

    /**基于用户id查询用户以及用户对应的部门信息*/
    SysUserDept findById(Integer id);

    int updateObject(SysUser entity);
    /**保存用户自身信息*/
    int insertObject(SysUser entity);
    /**禁用启用
     * @param id 用户id
     * @param valid 用户状态
     * @param modifiedUser 修改用户(登录用户)
     * */
    @Update("update sys_users set valid=#{valid},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
    int validById(Integer id,Integer valid,String modifiedUser);


    //int getRowCount(String name);
    //List<SysUserDept> findPageObjects(String username,Integer startIndex,Integer pageSize);

    /**基于用户名进行分页查询(底层基于PageHelper去实现)*/
    List<SysUserDept> findPageObjects(String username);

}
