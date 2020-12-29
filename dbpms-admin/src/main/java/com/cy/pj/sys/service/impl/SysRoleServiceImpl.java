package com.cy.pj.sys.service.impl;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.CheckBox;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.pojo.SysRole;
import com.cy.pj.sys.pojo.SysRoleMenu;
import com.cy.pj.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
//基于id查询角色以及角色对应的菜单id->方案2->业务层发起多次单表查询


    @Override
    public List<CheckBox> findRoles() {
        return sysRoleDao.findRoles();
    }

    @Override
    public SysRoleMenu findById(Integer id) {
        //1.参数校验
        //2.基于角色id查询角色以及角色对应菜单id,并返回查询结果
        SysRoleMenu roleMenu=sysRoleDao.findById(id);
        return roleMenu;
    }
//基于id查询角色以及角色对应的菜单id->方案1->业务层发起多次单表查询
//    @Override
//    public SysRoleMenu findById(Integer id) {
//        //1.参数校验
//        //2.查询角色自身信息
//        SysRoleMenu roleMenu=sysRoleDao.findById(id);
//        //3.查询角色对应的菜单信息并封装
//        List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleId(id);
//        roleMenu.setMenuIds(menuIds);
//        return roleMenu;
//    }

    @Transactional //此注解描述的方法为事务切入点方法,方法执行之前会开启事务,执行结束会提交或回滚事务
    @Override
    public int updateObject(SysRole entity, Integer[] menuIds) {
        //1.参数校验
        //2.更新角色自身信息(sys_roles)
        int rows=sysRoleDao.updateObject(entity);
        //3.保存角色和菜单关系数据(sys_role_menus)
        sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
        sysRoleMenuDao.insertObjects(entity.getId(),menuIds);//1-->10,1-->20,1-->30
        return rows;
    }
    @Transactional //此注解描述的方法为事务切入点方法,方法执行之前会开启事务,执行结束会提交或回滚事务
    @Override
    public int saveObject(SysRole entity, Integer[] menuIds) {
        //1.参数校验
        //2.保存角色自身信息(sys_roles)
        int rows=sysRoleDao.insertObject(entity);
        //3.保存角色和菜单关系数据(sys_role_menus)
        sysRoleMenuDao.insertObjects(entity.getId(),menuIds);//1-->10,1-->20,1-->30
        return rows;
    }

    @Override
    public PageObject<SysRole> findPageObjects(
               String name, Integer pageCurrent) {
        //1.参数校验
        if(pageCurrent==null||pageCurrent<1)
            throw new IllegalArgumentException("页码值不正确");
        //2.查询总记录数并校验
        int rowCount=sysRoleDao.getRowCount(name);
        if(rowCount==0)
            throw new ServiceException("没有找到对应记录");
        //3.查询当前页记录
        int pageSize=2;
        int startIndex=(pageCurrent-1)*pageSize;
        List<SysRole> records=sysRoleDao.findPageObjects(name,startIndex,pageSize);
        //4.封装查询结果并返回
//        PageObject pageObject=new PageObject<>();
//        pageObject.setRowCount(rowCount);
//        pageObject.setRecords(records);
//        pageObject.setPageSize(pageSize);
//        pageObject.setPageCurrent(pageCurrent);
//        int pageCount=rowCount/pageSize;
//        if(rowCount%pageSize!=0)pageCount++;
//        pageObject.setPageCount(pageCount);
//        return pageObject;

         return new PageObject<>(rowCount,records,pageSize,pageCurrent);

    }
}
