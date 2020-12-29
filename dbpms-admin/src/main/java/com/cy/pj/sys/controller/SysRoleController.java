package com.cy.pj.sys.controller;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.sys.pojo.SysRole;
import com.cy.pj.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role/")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("doFindRoles")
    public JsonResult doFindRoles(){
        return new JsonResult(sysRoleService.findRoles());
    }

    @RequestMapping("doFindById/{id}")
    public JsonResult doFindById(@PathVariable  Integer id){
        return new JsonResult(sysRoleService.findById(id));
    }

    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysRole entity,Integer[] menuIds){
        sysRoleService.updateObject(entity,menuIds);
        return new JsonResult("update ok");
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysRole entity,Integer[] menuIds){
        sysRoleService.saveObject(entity,menuIds);
        return new JsonResult("save ok");
    }
    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String name,Integer pageCurrent){
        return new JsonResult(sysRoleService.findPageObjects(name,pageCurrent));
        //问题:谁将返回值转换为json格式字符串? jackson
        //在这个JSON字符串中底层是如何获取key/value的?通过pojo对象的get方法
    }
}
