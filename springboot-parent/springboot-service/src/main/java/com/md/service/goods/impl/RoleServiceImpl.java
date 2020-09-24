package com.md.service.goods.impl;

import com.md.dao.IPer_apiDao;
import com.md.dao.IPermissionDao;
import com.md.dao.IRoleDao;
import com.md.entity.UserVo.*;
import com.md.pojo.user.Permission;
import com.md.pojo.user.Role;
import com.md.service.goods.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IPermissionDao permissionDao;

    @Autowired
    private IPer_apiDao apiDao;

    private RoleAdd add;

    @Override
    public List<RolePerInfo> roleListPer() throws Exception {
        //查询出所有的角色
        List<Role> roles = roleDao.findRole();

        List<RolePerInfo> rolelisi = new ArrayList<>();
        //获取每个角色所有的权限
        List<Integer> psIds = null;
        //封装类：
        RolePerInfo rolePerInfo = null;     //角色权限
        OnePerInfo menus =new OnePerInfo();              //一级权限
        TwoPerInfo two = new TwoPerInfo();              //二级权限
        ThreePerInfo three = new ThreePerInfo();          //三级权限
        List<OnePerInfo> onelist = null;
        List<TwoPerInfo> twolist = null;
        List<ThreePerInfo> threelist = null;
        for (Role role : roles) {
            rolePerInfo = new RolePerInfo();
            onelist = new ArrayList<>();
            twolist = new ArrayList<>();
            threelist = new ArrayList<>();





            String ps_ids = role.getPs_ids();
            rolePerInfo.setId(role.getRole_id());
            rolePerInfo.setRoleName(role.getRole_name());
            rolePerInfo.setRoleDesc(role.getRole_desc());
            if (ps_ids.length() != 0) {
                //得到每个id所对应的权限等级
                List<Permission> pers = permissionDao.findByIds(ps_ids);
                for (Permission per : pers) {
                    if (per.getPs_level().equals("0")) {
                        menus = new OnePerInfo();
                        //1级权限
                        menus.setId(per.getPs_id());
                        menus.setAuthName(per.getPs_name());
                        String path = apiDao.pathById(per.getPs_id());
                        menus.setPath(path);
                        onelist.add(menus);

                    } else if (per.getPs_level().equals("1")) {
                        two = new TwoPerInfo();
                        //2级权限
                        two.setId(per.getPs_id());
                        two.setAuthName(per.getPs_name());
                        String path = apiDao.pathById(per.getPs_id());
                        two.setPath(path);
                        twolist.add(two);

                    } else if (per.getPs_level().equals("2")) {
                        three = new ThreePerInfo();
                        //3级权限
                        three.setId(per.getPs_id());
                        three.setAuthName(per.getPs_name());
                        String path = apiDao.pathById(per.getPs_id());
                        three.setPath(path);
                        threelist.add(three);
                    }
                    menus.setChildren(twolist);
                    two.setChildren(threelist);
                }
                rolePerInfo.setChildren(onelist);
                rolelisi.add(rolePerInfo);
            } else {
                rolelisi.add(rolePerInfo);
            }
        }
        return rolelisi;
    }

    @Override
    public RoleAdd addRole(String roleName, String roleDesc) throws Exception {
        roleDao.addRole(roleName, roleDesc);
        //根据名字查询角色
        Role role = roleDao.findByName(roleName);
        add = RoleServiceImpl.roleutil(add, role);
        return add;
    }

    @Override
    public RoleAdd findById(Integer id) throws Exception {
        Role role = roleDao.findById(id);
        add = RoleServiceImpl.roleutil(add, role);
        return add;
    }

    @Override
    public RoleAdd modifyRole(Integer id, String roleName, String roleDesc) throws Exception {
        roleDao.modifyRole(id, roleName, roleDesc);
        //查询角色,封装进roleadd
        Role role = roleDao.findById(id);
        add = RoleServiceImpl.roleutil(add, role);
        return add;
    }

    @Override
    public String delRoleById(Integer id) throws Exception {
        roleDao.delRoleById(id);
        Role byId = roleDao.findById(id);
        String msg = "";
        if (byId == null) {
            msg = "删除成功";
        } else {
            msg = "删除失败";
        }
        return msg;
    }

    @Override
    public String modifyRights(Integer id, String rids) throws Exception {
        roleDao.modifyRights(id, rids);
        Role role = roleDao.findById(id);
        String msg = "";
        if (role.getPs_ids().equals(rids)) {
            msg = "更新成功";
        } else {
            msg = "更新失败";
        }
        return msg;
    }

    @Override
    public List<OnePerInfo> delRightsById(Integer roleId, Integer rightId) throws Exception {
        //查出角色所有的权限id
        String ids = roleDao.getIdsById(roleId);
        //根据要删除的权限id找到指定的权限
        Permission per = permissionDao.findById(rightId);
        String level = per.getPs_level();
        //找出指定权限id下的所有下一级权限
        List<Integer> perid = permissionDao.IdByPid(rightId);
        if (level.equals("2")) {
            //删除权限
            ids = RoleServiceImpl.delPid3(ids, rightId.toString());
        } else if (level.equals("1")) {
            //找出指定删除权限下所有的3级权限
            ids = RoleServiceImpl.delPid2(perid, ids);
            //删除传递过来的权限
            ids = RoleServiceImpl.delPid3(ids, rightId.toString());
        } else if (level.equals("0")) {
            //找出指定删除权限下的所有2级权限
            String[] split = ids.split(",");
            for (Integer integer : perid) {
                for (String s : split) {
                    if (Integer.parseInt(s) == integer) {
                        //找到2级权限下的所有3级权限并删除
                        List<Integer> threePer = permissionDao.IdByPid(integer);
                        //删除每个2级下的三级权限
                        ids = RoleServiceImpl.delPid2(threePer, ids);
                        //删除每个2级权限
                        ids = RoleServiceImpl.delPid3(ids, s);

                    }
                }
            }
            //删除传递过来的权限
            if (ids.length() != 3) {
                RoleServiceImpl.delPid3(ids, rightId.toString());
            } else {
                ids = "";
            }
        }
        //修改权限
        roleDao.modifyRights(roleId, ids);
        List<OnePerInfo> onelist = null;
        if (ids.length() != 0) {
            //将现有的所有1,2,3级权限放进指定集合
            OnePerInfo one = new OnePerInfo();
            TwoPerInfo two = new TwoPerInfo();
            ThreePerInfo three = new ThreePerInfo();
            onelist = new ArrayList<>();
            List<TwoPerInfo> twolist = new ArrayList<>();
            List<ThreePerInfo> thrlist = new ArrayList<>();
            //查出所有的权限信息
            List<Permission> byIds = permissionDao.findByIds(ids);
            for (Permission byId : byIds) {
                if (byId.getPs_level().equals("0")) {
                    one.setId(byId.getPs_id());
                    one.setAuthName(byId.getPs_name());
                    String path = apiDao.pathById(byId.getPs_id());
                    one.setPath(path);
                    onelist.add(one);
                } else if (byId.getPs_level().equals("1")) {
                    two.setId(byId.getPs_id());
                    two.setAuthName(byId.getPs_name());
                    String path = apiDao.pathById(byId.getPs_id());
                    two.setPath(path);
                    twolist.add(two);
                } else if (byId.getPs_level().equals("2")) {
                    three.setId(byId.getPs_id());
                    three.setAuthName(byId.getPs_name());
                    String path = apiDao.pathById(byId.getPs_id());
                    three.setPath(path);
                    thrlist.add(three);
                }
            }
            if (onelist.size() != 0) {
                for (OnePerInfo olist : onelist) {
                    if (twolist.size() != 0) {
                        for (TwoPerInfo tlist : twolist) {
                            //根据id查出pid
                            Integer tpid = permissionDao.PidById(tlist.getId());
                            if (olist.getId() == tpid) {
                                olist.getChildren().add(tlist);
                            }
                            if (thrlist.size() != 0) {
                                for (ThreePerInfo thlist : thrlist) {
                                    Integer thpid = permissionDao.PidById(thlist.getId());
                                    if (tlist.getId() == thpid) {
                                        tlist.getChildren().add(thlist);
                                    }
                                }
                            }

                        }
                    }

                }
            }
        }
        return onelist;

    }

    //将role放进add
    public static RoleAdd roleutil(RoleAdd add, Role role) {
        add = new RoleAdd();
        add.setRoleId(role.getRole_id());
        add.setRoleDesc(role.getRole_desc());
        add.setRoleName(role.getRole_name());
        return add;
    }

    //删除下级权限
    public static String delPid3(String ids, String pid) {
        if (ids.startsWith(pid)) {
            ids = ids.replace((pid + ","), "");
        } else {
            ids = ids.replace(("," + pid), "");
        }
        return ids;
    }

    //删除2级权限下的所有3级权限
    public static String delPid2(List<Integer> perid, String ids) {

        //将角色的权限分割
        String[] split = ids.split(",");
        for (String s : split) {
            for (Integer i : perid) {
                int i1 = Integer.parseInt(s);
                if (i1 == i) {
                    //删除权限id
                    ids = RoleServiceImpl.delPid3(ids, s);
                }
            }
        }
        return ids;
    }
}
