package com.cy.pj.sys.service.realm;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.pojo.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroUserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Autowired
    private SysMenuDao sysMenuDao;
    /**负责用户权限信息的获取和封装*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        System.out.println("==doGetAuthorizationInfo==");
        //1.获取登录用户
        SysUser user=(SysUser) principalCollection.getPrimaryPrincipal();
        //2.基于登录用户查询用户对应的权限标识
        List<String> perimssionList=
                sysMenuDao.findUserPermissions(user.getId());
        if(perimssionList==null||perimssionList.size()==0)
            throw new AuthorizationException();
        //3.封装用户权限信息并返回(交给SecurityManager对象)
        Set<String> set=new HashSet<>();
        for(String per:perimssionList){
            if(!StringUtils.isEmpty(per)){
                set.add(per);
            }
        }
        System.out.println("set="+set);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(
//            PrincipalCollection principalCollection) {
//        //1.获取登录用户
//        SysUser user=(SysUser) principalCollection.getPrimaryPrincipal();
//        //2.基于登录用户查询用户对应的角色id并校验.
//        List<Integer>  roleIds=sysUserRoleDao.findRoleIdsByUserId(user.getId());
//        if(roleIds==null||roleIds.size()==0)
//            throw new AuthorizationException();
//        //3.基于角色id查找菜单id并校验
//        List<Integer>  menuIds=sysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
//        if(menuIds==null||menuIds.size()==0)
//            throw new AuthorizationException();
//        //4.基于菜单id找到授权标识并校验
//        List<String> perimssionList=sysMenuDao.findPermissions(menuIds);
//        if(perimssionList==null||perimssionList.size()==0)
//            throw new AuthorizationException();
//        //5.封装用户权限信息并返回(交给SecurityManager对象)
//        Set<String> set=new HashSet<>();
//        for(String per:perimssionList){
//            if(!StringUtils.isEmpty(per)){
//                set.add(per);
//            }
//        }
//        System.out.println("set="+set);
//        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
//        info.setStringPermissions(set);
//        return info;
//    }


    /**负责认证信息的获取和封装*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取登录用户信息(用户名,密码)
        UsernamePasswordToken token=
                (UsernamePasswordToken)authenticationToken;
        String username=token.getUsername();
        //2.基于用户名查询数据库中的用户对象
        SysUser user=sysUserDao.findUserByUserName(username);
        //3.检测用户是否存在
        if(user==null)
            throw new UnknownAccountException();
        //4.检测用户是否被禁用
        if(user.getValid()==0)
            throw new LockedAccountException();
        //5.封装用户信息并返回
        ByteSource byteSource=ByteSource.Util.bytes(user.getSalt());
        SimpleAuthenticationInfo info=
                new SimpleAuthenticationInfo(
                        user,//principal (用户身份)
                        user.getPassword(),//credentials (凭证-已加密密码)
                        byteSource,//credentialsSalt(凭证盐)
                        getName());
        return info;
    }
    /**设置加密匹配器**/
//    @Override
//    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//        HashedCredentialsMatcher cMatcher=new HashedCredentialsMatcher();
//        cMatcher.setHashAlgorithmName("MD5");
//        cMatcher.setHashIterations(1);
//        super.setCredentialsMatcher(cMatcher);
//    }

    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        HashedCredentialsMatcher cMatcher=new HashedCredentialsMatcher();
        cMatcher.setHashAlgorithmName("MD5");
        cMatcher.setHashIterations(1);
        //cMatcher.setStoredCredentialsHexEncoded(true);
        return cMatcher;
    }
}
