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

import java.util.*;

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
        List<RolePerInfo> rolelist = new ArrayList<>();
        //封装类：
        RolePerInfo rolePerInfo = null;     //角色权限
        for (Role role : roles) {
            rolePerInfo = new RolePerInfo();
            String pids = role.getPs_ids();
            //如果有权限id
            if (pids.length() != 0) {
                //得到每个id所对应的权限等级
                rolePerInfo = RoleServiceImpl.fengzhuang(role,pids, permissionDao, apiDao);

                rolelist.add(rolePerInfo);
            } else {
                rolelist.add(rolePerInfo);
            }
        }
        return rolelist;
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

    public static RolePerInfo fengzhuang(Role role,String pids,IPermissionDao permissionDao,IPer_apiDao apiDao) throws Exception {
        Map<Integer,OnePerInfo> mapone = new HashMap<>();   //放1级权限
        Map<String,TwoPerInfo> maptwo = new HashMap<>();  //2级
        Map<String,ThreePerInfo> mapthree = new HashMap<>();  //3级
        //找出该角色对应的所有权限并放进集合中
        OnePerInfo one = new OnePerInfo();
        TwoPerInfo two = new TwoPerInfo();
        ThreePerInfo three = new ThreePerInfo();
        List<Permission> pers = permissionDao.findByIds(pids);
        for (Permission per : pers) {
            //将每个权限放进对应的集合
            if(per.getPs_level().equals("0")){
                one.setId(per.getPs_id());
                one.setAuthName(per.getPs_name());
                one.setPath(apiDao.pathById(per.getPs_id()));
                mapone.put(one.getId(),one);
            }
            if(per.getPs_level().equals("0")){
                two.setId(per.getPs_id());
                two.setAuthName(per.getPs_name());
                two.setPath(apiDao.pathById(per.getPs_id()));
                String id = RoleServiceImpl.idtopid(per.getPs_id(), per.getPs_pid());
                maptwo.put(id,two);
            }
            if(per.getPs_level().equals("0")){
                three.setId(per.getPs_id());
                three.setAuthName(per.getPs_name());
                three.setPath(apiDao.pathById(per.getPs_id()));
                String id = RoleServiceImpl.idtopid(per.getPs_id(), per.getPs_pid());
                mapthree.put(id,three);
            }
        }
        //将所有三级放进对应的2级
        Set<String> twoset = maptwo.keySet();
        Set<String> thset = mapthree.keySet();
        for (String ts : twoset) {
            for (String hs : thset) {
                if(ts.endsWith(hs.substring(0,3))){
                    //id和pid相同,将3级放进2级
                    maptwo.get(ts).getChildren().add(mapthree.get(hs));
                }
            }
        }
        //将所有二级放进对应的一级
        Set<String> twosets = maptwo.keySet();
        Set<Integer> oneset = mapone.keySet();
        for (Integer os : oneset) {
            for (String ts : twosets) {
                if(ts.endsWith(os+"")){
                    //将二级放进一级
                    mapone.get(os).getChildren().add(maptwo.get(ts));
                }
            }
        }
        Set<Integer> oneper = mapone.keySet();
        RolePerInfo rolePerInfo = new RolePerInfo();
        for (Integer integer : oneper) {
            one = mapone.get(integer);
            rolePerInfo.setId(role.getRole_id());
            rolePerInfo.setRoleName(role.getRole_name());
            rolePerInfo.setRoleDesc(role.getRole_desc());
            rolePerInfo.getChildren().add(one);
        }
        return rolePerInfo;
    }
    public static String idtopid(int id,int pid){
        return id+","+pid;
    }

    public static void main(String[] args) {
        String a= "123,345";
        int b=345;
        boolean b1 = a.endsWith(b + "");
        System.out.println(b1);
    }
}