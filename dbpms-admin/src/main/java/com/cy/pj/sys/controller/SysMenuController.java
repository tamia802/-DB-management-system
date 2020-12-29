package com.cy.pj.sys.controller;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.sys.pojo.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu/")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysMenu entity){
        sysMenuService.saveObject(entity);
        return new JsonResult("save ok");
    }

    @RequestMapping("doFindZtreeMenuNodes")
    public JsonResult doFindZtreeMenuNodes(){
        return new JsonResult(sysMenuService.findZtreeMenuNodes());
    }

    @RequestMapping("doFindObjects")
    public JsonResult doFindObjects(){
        List<Map<String,Object>> list=sysMenuService.findObjects();
        return new JsonResult(list);
    }
}
