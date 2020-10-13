package com.md.controller;

import com.md.common.JwtUtils;
import com.md.entity.UserVo.ManagerLogin;
import com.md.entity.UserVo.MenusPer;
import com.md.entity.UserVo.PerTree;
import com.md.entity.UserVo.Perfind;
import com.md.entity.goodsVo.Result;
import com.md.service.goods.ManagerService;
import com.md.service.goods.PermissionService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ManagerService managerService;

    private Result result;
    private JwtUtils jwtUtils = new JwtUtils();

    @GetMapping("/rights/{type}")
    public Result findPer(@PathVariable(required = true) String type)throws Exception{
        if(type.equals("list")){
            List<Perfind> per = permissionService.findPerlist();
            result = Result.succ("获取权限列表成功",per);
        }else if(type.equals("tree")){
            List<PerTree> pertree = permissionService.findPertree();
            result = Result.succ("获取权限列表成功",pertree);
        }
        return result;
    }

    @GetMapping("/menus")
    public Result menusPer(HttpServletRequest request) throws Exception{

        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            //系统未捕捉到请求头信息
            throw new Exception("校验失败，请重新登录");
        }
        //查出用户的id
        Integer id = Integer.parseInt(jwtUtils.parseJwt(authorization).getId());
        List<MenusPer> menus = permissionService.menusPer(id);

        return Result.succ("获取菜单列表成功",menus);
    }
}
