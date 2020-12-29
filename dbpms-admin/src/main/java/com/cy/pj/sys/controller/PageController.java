package com.cy.pj.sys.controller;

import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.pojo.SysUserMenu;
import com.cy.pj.sys.service.SysMenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**借助PageController处理客户端所有页面请求*/
@RequestMapping("/")
@Controller
public class PageController {
    //http://localhost/menu/doFindObjects
    //http://localhost/log/log_list
    //http://localhost/menu/menu_list
    @RequestMapping("/{module}/{moduleUI}")
    public String doModuleUI(@PathVariable String moduleUI){
        return "sys/"+moduleUI;
    }

    @RequestMapping("doPageUI")
    public String doPageUI(){
        return "common/page";
    }

    @Autowired
    private SysMenuService sysMenuService;
    @RequestMapping("doIndexUI")
    public String doIndexUI(Model model){
        SysUser user=(SysUser)
        SecurityUtils.getSubject().getPrincipal();//从session获取登录用户
        model.addAttribute("username",user.getUsername());
        List<SysUserMenu> userMenus=sysMenuService.findUserMenus(user.getId());
//        for(SysUserMenu userMenu:userMenus){
//            System.out.println(userMenu);
//        }
        model.addAttribute("userMenus",userMenus);
        return "starter";
    }
    @RequestMapping("doLoginUI")
    public String doLoginUI(){
        return "login";
    }

}
