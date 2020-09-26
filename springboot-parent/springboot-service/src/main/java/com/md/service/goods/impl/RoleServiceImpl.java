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

import javax.management.relation.RoleInfo;
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
        //装载所有角色的所有权限的集合
        List<RolePerInfo> rolelist = new ArrayList<>();
        //封装类：
        RolePerInfo rolePerInfo = null;
        for (Role role : roles) {
            rolePerInfo = new RolePerInfo();
            String pids = role.getPs_ids();
//            System.out.println(pids.length() == 0);
            if (pids.length() != 0) {
                rolePerInfo = fengzhuang(role, pids, permissionDao, apiDao);
            }
            rolePerInfo.setId(role.getRole_id());
            rolePerInfo.setRoleName(role.getRole_name());
            rolePerInfo.setRoleDesc(role.getRole_desc());
            rolelist.add(rolePerInfo);
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
    public List<PerList> delRightsById(Integer roleId, Integer rightId) throws Exception {
       /* String ids = roleDao.getIdsById(roleId);
        List<PerList> onelist = null;
        if(ids.length() != 0) {
            //查出角色所有的二三级权限
            List<Permission> per1 = permissionDao.RolePerByLevel( ids,"1");
            List<Permission> per2 = permissionDao.RolePerByLevel( ids,"2");
            //创建要删除的权限id的集合
            List<Integer> delids = new ArrayList<>();
            delids.add(rightId);
            for (Permission permission : per1) {
                if (permission.getPs_pid() == rightId) {
                    Integer delid = permission.getPs_id();
                    delids.add(delid);
                    for (Permission permission1 : per2) {
                        if (permission1.getPs_pid() == delid) {
                            delids.add(permission1.getPs_id());
                        }
                    }
                }
            }
            List<String> strings = Arrays.asList(ids.split(","));
            strings.removeAll(delids);
            Iterator<String> iterator = strings.iterator();
            if (iterator.hasNext()) {
                ids = iterator + ",";
            }*/
        //查出角色所有的权限id
        String ids = roleDao.getIdsById(roleId);
        List<PerList> onelist = null;
        if (ids.length() != 0) {
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

            List<PerList> twolist = null;
            List<PerList> thrlist = null;
            if (ids.length() != 0) {
                //将现有的所有1,2,3级权限放进指定集合
                onelist = new ArrayList<>();
                twolist = new ArrayList<>();
                thrlist = new ArrayList<>();
                //查出所有的权限信息
                List<Permission> byIds = permissionDao.findByIds(ids);
                for (Permission byId : byIds) {
                    if (byId.getPs_level().equals("0")) {
                        onelist.add(roletolist(byId, apiDao));
                    } else if (byId.getPs_level().equals("1")) {
                        twolist.add(roletolist(byId, apiDao));
                    } else if (byId.getPs_level().equals("2")) {
                        thrlist.add(roletolist(byId, apiDao));
                    }
                }
                if (onelist.size() != 0) {
                    for (PerList olist : onelist) {
                        if (twolist.size() != 0) {
                            for (PerList tlist : twolist) {
                                //根据id查出pid
                                Integer tpid = permissionDao.PidById(tlist.getId());
                                if (olist.getId() == tpid) {
                                    olist.getChildren().add(tlist);
                                }
                                if (thrlist.size() != 0) {
                                    for (PerList thlist : thrlist) {
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

    public static RolePerInfo fengzhuang(Role role, String pids, IPermissionDao permissionDao, IPer_apiDao apiDao) throws Exception {
        Map<Integer, PerList> mapone = new HashMap<>();   //放1级权限
        Map<String, PerList> maptwo = new HashMap<>();  //2级
        Map<String, PerList> mapthree = new HashMap<>();  //3级
        //找出该角色对应的所有权限并放进集合中
        PerList perList = null;
        List<Permission> pers = permissionDao.findByIds(pids);
        for (Permission per : pers) {
            //将每个权限放进对应的集合
            if (per.getPs_level().equals("0")) {
                perList = roletolist(per, apiDao);
                mapone.put(perList.getId(), perList);
            }
            if (per.getPs_level().equals("1")) {
                perList = roletolist(per, apiDao);
                String id = idtopid(per.getPs_id(), per.getPs_pid());
                maptwo.put(id, perList);
            }
            if (per.getPs_level().equals("2")) {
                perList = roletolist(per, apiDao);
                String id = idtopid(per.getPs_id(), per.getPs_pid());
                mapthree.put(id, perList);
            }
        }
        //将所有三级放进对应的2级
        Set<String> twoset = maptwo.keySet();
        Set<String> thset = mapthree.keySet();
        for (String ts : twoset) {
            for (String hs : thset) {
                if (hs.endsWith(ts.substring(0, 3))) {
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
                if (ts.endsWith(os + "")) {
                    //将二级放进一级
                    mapone.get(os).getChildren().add(maptwo.get(ts));
                }
            }
        }
        //将所有一级权限放进角色中
        Set<Integer> oneper = mapone.keySet();
        RolePerInfo rolePerInfo = new RolePerInfo();
        for (Integer integer : oneper) {
            perList = mapone.get(integer);
            rolePerInfo.getChildren().add(perList);
        }
        return rolePerInfo;
    }

    public static String idtopid(int id, int pid) {
        return id + "," + pid;
    }

    //将per对象放入perList中
    public static PerList roletolist(Permission per, IPer_apiDao per_apiDao) throws Exception {
        PerList perList = new PerList();
        perList.setId(per.getPs_id());
        perList.setAuthName(per.getPs_name());
        String path = per_apiDao.pathById(per.getPs_id());
        perList.setPath(path);
        return perList;
    }
}
