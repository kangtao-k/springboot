package com.md.service.goods.impl;

import com.md.dao.IPer_apiDao;
import com.md.dao.IPermissionDao;
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
import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private IPermissionDao perDao;

    @Autowired
    private IPer_apiDao apiDao;

    @Override
    public List<Perfind>  findPerlist() throws Exception {
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
        System.out.println(per2);
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
            last =  new PerTreeLast();
            last.setId(per.getPs_id());
            last.setAuthName(per.getPs_name());
            String path = apiDao.pathById(per.getPs_id());
            last.setPath(path);
            last.setPid(per.getPs_pid().toString());
            perlist3.add(last);
        }
        for (PerTree p1 : perlist1) {
            for (PerTree p2 : perlist2) {
                if(p1.getId().equals(p2.getPid())){
                    //将数据放进p1中
                    p1.getChildren().add(p2);
                    for (PerTreeLast p3 : perlist3) {
                        if((p2.getId().toString()).equals(p3.getPid())){
                            p3.setPid((p1.getId())+","+(p2.getId()));
                            p2.getChildren().add(p3);
                        }
                    }
                }
            }
        }
        return perlist1;
    }

    @Override
    public MenusPer menusPer() throws Exception {
        Permission per = perDao.findByName("商品管理");
        MenusPer menus = PermissionServiceImpl.perToMen(apiDao, per);
        Permission per2 = perDao.findByName("商品列表");
        MenusPer menus1 = PermissionServiceImpl.perToMen(apiDao, per2);
        menus.getChildren().add(menus1);
        return menus;
    }

    //将Permission对象的值放进PerTree里
    public static PerTree setfind(IPer_apiDao apiDao ,Permission per) throws Exception {
        PerTree perTree = new PerTree();
        perTree.setId(per.getPs_id());
        perTree.setAuthName(per.getPs_name());
        String path = apiDao.pathById(per.getPs_id());
        perTree.setPath(path);
        perTree.setPid(per.getPs_pid());
        return perTree;
    }

    //将Permission对象的值放进MenusPer里
    public static MenusPer perToMen(IPer_apiDao apiDao ,Permission per) throws Exception {
        MenusPer menus = new MenusPer();
        menus.setId(per.getPs_id());
        menus.setAuthName(per.getPs_name());
        String path1 = apiDao.pathById(per.getPs_id());
        menus.setPath(path1);
        return menus;
    }
}
