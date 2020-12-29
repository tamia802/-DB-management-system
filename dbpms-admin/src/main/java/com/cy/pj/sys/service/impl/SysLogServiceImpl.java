package com.cy.pj.sys.service.impl;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.pojo.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public PageObject<SysLog> findPageObjects(String username,
                                              Integer pageCurrent) {
        //1.参数校验
        //2.查询当前页记录
        int pageSize=5;
        Page<SysLog> page= PageHelper.startPage(pageCurrent,pageSize);
        List<SysLog> records= sysLogDao.findPageObjects(username);
        //3.封装查询结果
        return new PageObject<>((int)page.getTotal(),records,page.getPages(),pageSize,pageCurrent);
    }

    @Override
    public void saveObject(SysLog entity) {
        sysLogDao.insertObject(entity);
    }
}
