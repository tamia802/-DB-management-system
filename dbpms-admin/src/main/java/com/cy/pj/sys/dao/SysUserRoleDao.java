package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserRoleDao {
    /**
     * 基于用户id查询用户对应的角色id
     * @param userId
     * @return
     */
    @Select("select role_id from sys_user_roles where user_id=#{userId}")
    List<Integer> findRoleIdsByUserId(Integer userId);
    /**
     * 保存用户以及用户对应的角色id (保存用户和角色的关系数据)
     * @param userId
     * @param roleIds
     * @return
     */
    int insertObjects(Integer userId,Integer[]roleIds);

    @Delete("delete from sys_user_roles where user_id=#{userId}")
    int deleteObjectsByUserId(Integer userId);
}
