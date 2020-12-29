package com.cy.pj.sys.service.impl;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.pojo.SysUserDept;
import com.cy.pj.sys.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public int updatePassword(String sourcePassword, String newPassword, String confirmPassword) {
        //1.参数校验
        if(StringUtils.isEmpty(sourcePassword))
            throw new IllegalArgumentException("原密码不能为空");
        if(StringUtils.isEmpty(newPassword))
            throw new IllegalArgumentException("新密码不能为空");
        if(!newPassword.equals(confirmPassword))
            throw new IllegalArgumentException("两次输入新密码不一致");
        SysUser sysUser=(SysUser)SecurityUtils.getSubject().getPrincipal();
        String sourceSalt=sysUser.getSalt();
        SimpleHash simpleHash=
                new SimpleHash("md5",sourcePassword,sourceSalt,1);
        if(!sysUser.getPassword().equals(simpleHash.toHex()))
            throw  new IllegalArgumentException("原密码不正确");
        //2.对新密码进行加密
        String newSalt=UUID.randomUUID().toString();
        simpleHash=
                new SimpleHash("md5",newPassword,newSalt,1);
        //3.更新新密码
        int rows=sysUserDao.updatePassword(simpleHash.toHex(),newSalt, sysUser.getId());
        return rows;
    }
    @Override
    public Map<String, Object> findById(Integer id) {
        //1.参数校验
        //2.查询用户以及用户对应部门信息
        SysUserDept user=sysUserDao.findById(id);
        //3.查询用户对应的角色信息
        List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(id);
        Map<String,Object> map=new HashMap<>();
        map.put("user",user);//这里的key不能随意写,要与客户端取值时的key一致
        map.put("roleIds",roleIds);
        return map;
    }
    /***
     * 更新用户以及用户对应的角色关系数据
     * @param entity
     * @param roleIds
     * @return
     */
    @Transactional
    @Override
    public int updateObject(SysUser entity, Integer[] roleIds) {
        //1.参数校验
        if(entity==null)
            throw new IllegalArgumentException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getUsername()))
            throw new IllegalArgumentException("用户名不能为空");
        if(roleIds==null||roleIds.length==0)
            throw new IllegalArgumentException("必须为用户分配角色");
        //3.保存用户自身信息
        int rows=sysUserDao.updateObject(entity);
        //4.保存用户和角色关系数据
        sysUserRoleDao.deleteObjectsByUserId(entity.getId());
        sysUserRoleDao.insertObjects(entity.getId(),roleIds);
        return rows;
    }
    /***
     * 保存用户以及用户对应的角色关系数据
     * @param entity
     * @param roleIds
     * @return
     */
    @Transactional
    @Override
    public int saveObject(SysUser entity, Integer[] roleIds) {
        //1.参数校验
        if(entity==null)
            throw new IllegalArgumentException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getUsername()))
            throw new IllegalArgumentException("用户名不能为空");
        if(StringUtils.isEmpty(entity.getPassword()))
            throw new IllegalArgumentException("密码不能为空");
        if(roleIds==null||roleIds.length==0)
            throw new IllegalArgumentException("必须为用户分配角色");
        //.....
        //2.密码加密(本次采用MD5盐值加密-->盐值可以保证密码更加安全)
        //String hashedPwd= DigestUtils.md5DigestAsHex(entity.getPassword().getBytes());
        String salt= UUID.randomUUID().toString();//随机字符串
        SimpleHash sh=
                new SimpleHash("MD5",entity.getPassword(),salt,1);
        String hashedPwd=sh.toHex();//将加密结果转换为16进制
        entity.setPassword(hashedPwd);
        entity.setSalt(salt);
        //3.保存用户自身信息
        int rows=sysUserDao.insertObject(entity);
        //4.保存用户和角色关系数据
        sysUserRoleDao.insertObjects(entity.getId(),roleIds);
        return rows;
    }

    /**
     * RequiresPermissions 注解由shiro框架提供,此注解描述的方法为切入点方法,
     * 表示此方法在执行时,需要进行授权.也就是说假如用户拥有访问此方法的权限,才
     * 可以访问这个方法.
     * @param id
     * @param valid
     * @return
     */
    @RequiredLog(operation = "禁用启用")//此注解描述的方法为日志切入点方法
    @RequiresPermissions("sys:user:update")//注解中的字符串为一个权限标识
    @Override
    public int validById(Integer id, Integer valid) {
        //1.参数校验
        //2.修改用户状态
        int rows=sysUserDao.validById(id,valid,"admin");//这里的admin暂时理解为登录用户
        if(rows==0)
            throw new ServiceException("记录有可能已经不存在");
        return rows;
    }
    @RequiredLog(operation = "分页查询用户信息")
    @Override
    public PageObject<SysUserDept> findPageObjects(String username, Integer pageCurrent) {
        //1.参数校验
        if(pageCurrent==null||pageCurrent<1)
            throw new IllegalArgumentException("页码值不合法");
        //2.分页查询用户信息
        int pageSize=3;
        //执行查询之前基于pageHelper实现分页设置,并启动数据层的拦截机制
        Page<SysUserDept> page=PageHelper.startPage(pageCurrent,pageSize);
        List<SysUserDept> records=sysUserDao.findPageObjects(username);
        //3.封装结果并返回
        return new PageObject<>((int)page.getTotal(),records,page.getPages(),pageSize,pageCurrent);
    }
}
