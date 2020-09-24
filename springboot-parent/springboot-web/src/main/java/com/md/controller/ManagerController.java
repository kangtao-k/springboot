package com.md.controller;

import com.md.common.JwtUtils;
import com.md.common.MD5;
import com.md.common.PageObj;
import com.md.entity.UserVo.ManagerLogin;
import com.md.entity.UserVo.ManagerModifyType;
import com.md.entity.UserVo.Manageradd;
import com.md.entity.goodsVo.Result;
import com.md.service.goods.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    private Result result = null;

    private JwtUtils jwtUtils = new JwtUtils();

    //登录
    @PostMapping(value = "/login")
    public Result loginManager(@RequestBody(required = true) Map<String, String> map) throws Exception {
        String username = map.get("username");
        String password = map.get("password");
        ManagerLogin login = managerService.loginManager(username, MD5.getMD5(password));
        if (login.getId() != null) {
            //登陆成功
            //其他数据以map集合存放在token中
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("loginId", login.getId());
            dataMap.put("loginName", login.getUsername());
            String loginId = Integer.toString(login.getId());
            //生成token并存入数据返回
            String token = jwtUtils.createJwt(loginId, login.getUsername(), dataMap);
            StringBuffer sb = new StringBuffer();
            String pre = "Bearer ";
            StringBuffer sb2 = sb.append(pre);
            StringBuffer tokens = sb2.append(token);
            login.setToken(tokens.toString());
            result = Result.succ("登陆成功", login);
        } else if (login.getId() == null) {
            //登陆失败
            result = Result.failed("用户名或密码错误");
        }
        return result;
    }

    //查询所有用户
    @GetMapping(value = "/users")
    public Result findUser(String query, @RequestParam(required = true) Integer pagenum,
                           @RequestParam(required = true) Integer pagesize,
                           HttpServletRequest request) throws Exception {
//        从请求头信息中获取token数据
//        request.getHeader()
        PageObj obj = null;
        if (query == null) {//参数为空
            obj = managerService.pagelist(pagenum, pagesize);
        } else {//参数不为空
            obj = managerService.pagelistquery(query, pagenum, pagesize);
        }

        return Result.succ("获取管理员列表成功", obj);
    }

    //添加用户
    @PostMapping(value = "/users")
    public Result addUser(@RequestParam(required = true) String username, @RequestParam(required = true) String password,
                          String email, String mobile) throws Exception {
        boolean flag = managerService.addManager(username, MD5.getMD5(password), email, mobile);
        if (flag) {
            //根据用户名查询用户
            Manageradd add = managerService.findByName(username);
            result = Result.succ("用户创建成功", add);
        } else {
            result = Result.failed("该用户名已存在");
        }
        return result;
    }

    //修改用户状态
    @PutMapping(value = "/users/{uid}/state/{type}")
    public Result modifyType(@PathVariable(required = true) Integer uid, @PathVariable(required = true) boolean type) throws Exception {
        int mg_state;
        if (type) {
            mg_state = 1;
        } else {
            mg_state = 0;
        }
        ManagerModifyType modifyType = managerService.modifyType(uid, mg_state);
        return Result.succ("设置状态成功", modifyType);
    }

    //根据id查询用户信息
    @GetMapping(value = "/users/{id}")
    public Result findById(@PathVariable(required = true) Integer id) throws Exception {
        ManagerLogin manager = managerService.findById(id);
        if (manager == null) {
            //不存在此用户
            result = Result.failed("不存在此用户");
        } else {
            result = Result.succ("查询成功", manager);
        }
        return result;
    }

    //编辑用户提交
    @PutMapping(value = "/users/{id}")
    public Result modifySById(@PathVariable(required = true) Integer id, String email, String mobile) throws Exception {
        //根据id修改邮箱和手机号
        ManagerLogin manager = managerService.modifyById(id, email, mobile);
        return Result.succ("更新成功", manager);
    }

    //删除用户
    @DeleteMapping(value = "/users/{id}")
    public Result delManager(@PathVariable(required = true) Integer id) throws Exception {
        //根据id删除用户
        managerService.delManager(id);
        return Result.succ("删除成功", null);
    }

    //给用户分配新角色
    @PutMapping(value = "/users/{id}/role")
    public Result modifyRoleById(@PathVariable(required = true) Integer id, Integer rid) throws Exception {
        //根据用户id改变角色id
        ManagerLogin manager = managerService.modifyRoleById(id, rid);
        return Result.succ("设置角色成功", manager);
    }
}
