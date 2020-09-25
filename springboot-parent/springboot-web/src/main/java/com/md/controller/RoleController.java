package com.md.controller;

import com.md.entity.UserVo.OnePerInfo;
import com.md.entity.UserVo.PerList;
import com.md.entity.UserVo.RoleAdd;
import com.md.entity.UserVo.RolePerInfo;
import com.md.entity.goodsVo.Result;
import com.md.service.goods.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    private Result result;

    //角色列表
    @GetMapping("/roles")
    public Result roleListPer() throws Exception{
        List<RolePerInfo> role = roleService.roleListPer();
        return Result.succ("获取成功", role);
    }

    //添加角色
    @PostMapping("/roles")
    public Result addRole(@RequestParam(required = true) String roleName, String roleDesc) throws Exception {
        RoleAdd add = roleService.addRole(roleName,roleDesc);
        return Result.succ("创建成功",add);
    }

    //根据id查询角色
    @GetMapping("/roles/{id}")
    public Result findById(@PathVariable(required = true) Integer id) throws Exception{
        RoleAdd add = roleService.findById(id);
        return Result.succ("获取成功",add);
    }

    //根据id修改角色名称和描述
    @PutMapping("/roles/{id}")
    public Result modifyRole(@PathVariable(required = true) Integer id, @RequestParam(required = true) String roleName, String roleDesc) throws Exception{
        RoleAdd add = roleService.modifyRole(id,roleName,roleDesc);
        return Result.succ("获取成功",add);
    }

    //删除角色
    @DeleteMapping("/roles/{id}")
    public Result delRoleById(@PathVariable(required = true) Integer id) throws Exception {
        String msg = roleService.delRoleById(id);
        return Result.succ(msg,null);
    }

    //角色授权
    @PostMapping("/roles/{roleId}/rights")
    public Result modifyRights(@PathVariable(required = true) Integer roleId,
                               @RequestBody Map<String,String> map) throws Exception{
        String rids = map.get("rids");
        String msg = roleService.modifyRights(roleId,rids);
        return Result.succ(msg,null);
    }

    //删除角色指定权限
    @DeleteMapping("/roles/{roleId}/rights/{rightId}")
    public Result delRightsById(@PathVariable(required = true) Integer roleId, @PathVariable(required = true) Integer rightId) throws Exception{
        List<PerList> onelist = roleService.delRightsById(roleId, rightId);
        return Result.succ("取消权限成功",onelist);
    }
}
