package com.md.service.goods.impl;

import com.md.dao.IManagerDao;
import com.md.dao.IPer_apiDao;
import com.md.dao.IPermissionDao;
import com.md.dao.IRoleDao;
import com.md.entity.UserVo.MenusPer;
import com.md.entity.UserVo.PerTree;
import com.md.entity.UserVo.PerTreeLast;
import com.md.entity.UserVo.Perfind;
import com.md.pojo.user.Permission;
import com.md.service.goods.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private IPermissionDao perDao;

    @Autowired
    private IPer_apiDao apiDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IManagerDao managerDao;

    @Override
    public List<Perfind> findPerlist() throws Exception {
        List<Permission> pers = perDao.findPer();
        List<Perfind> list = new ArrayList<>();
        Perfind find;
        for (Permission per : pers) {
            find = new Perfind();
            find.setId(per.getPs_id());
            find.setPid(per.getPs_pid());
            find.setAuthName(per.getPs_name());
            find.setLevel(per.getPs_level());
            String path = apiDao.pathById(per.getPs_id());
            find.setPath(path);
            list.add(find);
        }
        return list;
    }

    @Override
    public List<PerTree> findPertree() throws Exception {
        //找出所有一级权限
        List<Permission> per1 = perDao.findByLevel("0");
        PerTree pertree1 = null;
        List<PerTree> perlist1 = new ArrayList<>();
        //将信息放进封装类中，暂时不放子权限
        for (Permission per : per1) {
            pertree1 = PermissionServiceImpl.setfind(apiDao, per);
            perlist1.add(pertree1);
        }

        //找出所有二级权限
        List<Permission> per2 = perDao.findByLevel("1");
        PerTree pertree2 = null;
        List<PerTree> perlist2 = new ArrayList<>();
        //将信息放进封装类中，暂时不放子权限
        for (Permission per : per2) {
            pertree2 = PermissionServiceImpl.setfind(apiDao, per);
            perlist2.add(pertree2);
        }

        //找出所有三级权限
        List<Permission> per3 = perDao.findByLevel("2");
        PerTreeLast last = null;
        List<PerTreeLast> perlist3 = new ArrayList<>();

        //将信息封装到类中，暂时不放进pid
        for (Permission per : per3) {
            last = new PerTreeLast();
            last.setId(per.getPs_id());
            last.setAuthName(per.getPs_name());
            String path = apiDao.pathById(per.getPs_id());
            last.setPath(path);
            last.setPid(per.getPs_pid().toString());
            perlist3.add(last);
        }
        for (PerTree p1 : perlist1) {
            for (PerTree p2 : perlist2) {
                if (p1.getId().equals(p2.getPid())) {
                    //将数据放进p1中
                    p1.getChildren().add(p2);
                    for (PerTreeLast p3 : perlist3) {
                        if ((p2.getId().toString()).equals(p3.getPid())) {
                            p3.setPid((p1.getId()) + "," + (p2.getId()));
                            p2.getChildren().add(p3);
                        }
                    }
                }
            }
        }
        return perlist1;
    }

    @Override
    public List<MenusPer> menusPer(Integer id) throws Exception {
        //根据用户的id查询出角色id对应的权限ids
        String ids = roleDao.getIdsByMgId(id);
        //根据权限ids查询出所有的1级,2级和3级权限
        List<MenusPer> listpers = new ArrayList<>();
        //查出1级权限
        List<Permission> pers1 = perDao.findByIdslev(ids, "0");
        for (Permission permission : pers1) {
            MenusPer menus = perToMen(apiDao, permission);
            listpers.add(menus);
        }
        for (MenusPer listper : listpers) {
            menusPersTest(listper, perDao, apiDao, ids, 1);
        }
        return listpers;
    }

    //递归查询嵌套循环
    public static void menusPersTest(MenusPer pers, IPermissionDao perDao, IPer_apiDao apiDao, String ids, Integer level) throws Exception {
        if (level < 3) {
            List<Permission> pers1 = perDao.findByIdslev(ids, level.toString());
            if (pers1 != null) {
                MenusPer menus = null;
                for (Permission permission : pers1) {
                    if (permission.getPs_pid().equals(pers.getId())) {
                        menus = perToMen(apiDao, permission);
                        pers.getChildren().add(menus);
                    }
                }
            }
            List<MenusPer> children = pers.getChildren();
            for (MenusPer per : children) {
                menusPersTest(per, perDao, apiDao, ids, level + 1);
            }
        }
    }

    //将Permission对象的值放进PerTree里
    public static PerTree setfind(IPer_apiDao apiDao, Permission per) throws Exception {
        PerTree perTree = new PerTree();
        perTree.setId(per.getPs_id());
        perTree.setAuthName(per.getPs_name());
        String path = apiDao.pathById(per.getPs_id());
        perTree.setPath(path);
        perTree.setPid(per.getPs_pid());
        return perTree;
    }

    //将Permission对象的值放进MenusPer里
    public static MenusPer perToMen(IPer_apiDao apiDao, Permission per) throws Exception {
        MenusPer menus = new MenusPer();
        menus.setId(per.getPs_id());
        menus.setPid(per.getPs_pid());
        menus.setAuthName(per.getPs_name());
        String path1 = apiDao.pathById(per.getPs_id());
        menus.setPath(path1);
        if ("用户管理".equals(per.getPs_name())) {
            menus.setOrder(1);
        }
        if ("权限管理".equals(per.getPs_name())) {
            menus.setOrder(2);
        }
        if ("商品管理".equals(per.getPs_name())) {
            menus.setOrder(3);
        }
        if ("订单管理".equals(per.getPs_name())) {
            menus.setOrder(4);
        }
        if ("数据统计".equals(per.getPs_name())) {
            menus.setOrder(5);
        }
        if ("商品列表".equals(per.getPs_name())) {
            menus.setOrder(1);
        }
        if ("分类参数".equals(per.getPs_name())) {
            menus.setOrder(2);
        }
        if ("商品分类".equals(per.getPs_name())) {
            menus.setOrder(3);
        }
        return menus;
    }
}
