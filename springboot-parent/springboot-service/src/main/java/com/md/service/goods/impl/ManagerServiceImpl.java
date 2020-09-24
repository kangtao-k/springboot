package com.md.service.goods.impl;

import com.md.common.DateUtils;
import com.md.common.PageObj;
import com.md.dao.IManagerDao;
import com.md.dao.IRoleDao;
import com.md.entity.UserVo.ManagerLogin;
import com.md.entity.UserVo.ManagerModifyType;
import com.md.entity.UserVo.Manageradd;
import com.md.entity.UserVo.Managerfind;
import com.md.pojo.user.Manager;
import com.md.service.goods.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private IManagerDao managerDao;

    @Autowired
    private IRoleDao roleDao;

    private PageObj pobj = new PageObj();

    private ManagerLogin login = new ManagerLogin();

    //登录
    @Override
    public ManagerLogin loginManager(String username, String password) throws Exception {
        Manager manager = managerDao.loginManager(username, password);
        if (manager != null) {
            login = ManagerServiceImpl.ManagerLogin(login, manager);
        }
        return login;
    }

    //添加用户，并把用户数据传输回去
    @Override
    public boolean addManager(String username, String password, String email, String mobile) throws Exception {
        Manager byName = managerDao.findByName(username);
        boolean flag;
        if (byName == null) {
            int time = DateUtils.getInteDate();
            int type = 1;
            managerDao.addManager(username, password, email, mobile, time, type);
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    //根据姓名查找用户信息并放进manageradd封装类中
    @Override
    public Manageradd findByName(String username) throws Exception {
        Manager manager = managerDao.findByName(username);
        Manageradd add = new Manageradd();
        add.setId(manager.getMg_id());
        add.setUsername(manager.getMg_name());
        add.setMobile(manager.getMg_mobile());
        add.setEmail(manager.getMg_email());
        add.setCreate_time(manager.getMg_time());
        add.setRole_id(-1);
        return add;
    }

    @Override
    public ManagerModifyType modifyType(Integer uId, int mg_state) throws Exception {
        managerDao.modifyType(uId, mg_state);
        //根据id查询用户信息
        Manager manager = managerDao.findById(uId);
        ManagerModifyType modifyType = new ManagerModifyType();
        modifyType.setId(uId);
        modifyType.setEmail(manager.getMg_email());
        modifyType.setMg_state(manager.getMg_state());
        modifyType.setMobile(manager.getMg_mobile());
        modifyType.setRid(manager.getRole_id());
        modifyType.setUsername(manager.getMg_name());
        return modifyType;
    }

    @Override
    public ManagerLogin findById(Integer id) throws Exception {
        Manager manager = managerDao.findById(id);
        if (manager != null) {
            login = ManagerServiceImpl.ManagerLogin(login, manager);
        }
        return login;
    }

    @Override
    public ManagerLogin modifyById(Integer id, String email, String mobile) throws Exception {
        managerDao.modifyById(email, mobile, id);
        Manager manager = managerDao.findById(id);
        login = ManagerServiceImpl.ManagerLogin(login, manager);
        return login;
    }

    @Override
    public void delManager(Integer id) throws Exception {
        managerDao.delManager(id);
    }

    @Override
    public ManagerLogin modifyRoleById(Integer id, Integer rid) throws Exception {
        managerDao.modifyRoleById(id, rid);
        Manager manager = managerDao.findById(id);
        login = ManagerServiceImpl.ManagerLogin(login, manager);
        return login;
    }

    @Override
    public PageObj pagelist(Integer pagenum, Integer pagesize) throws Exception {
        int a = pagesize * (pagenum - 1);// 计算出开始查询的位置
        Managerfind find = new Managerfind();
        //查出所有数据
        List<Manager> managers = managerDao.findAllManager(a,pagesize);
        int size = managerDao.mannum();

        List<Managerfind> lists = new ArrayList<>();
        for (Manager manager : managers) {
            find = new Managerfind();
            find.setId(manager.getMg_id());
            find.setUsername(manager.getMg_name());
            find.setMobile(manager.getMg_mobile());
            find.setType(manager.getMg_state());
            find.setEmail(manager.getMg_email());
            find.setCreate_time(manager.getMg_time());
            find.setRole_name(roleDao.NameById(manager.getRole_id()));
            if (manager.getMg_state() == 1) {
                find.setMg_state(true);
            } else if (manager.getMg_state() == 0) {
                find.setMg_state(false);
            }
            lists.add(find);
        }
        pobj.setUsers(lists);
        pobj.setTotal(size);
        pobj.setPagenum(pagenum);
        return pobj;
    }

    @Override
    public PageObj pagelistquery(String query, Integer pagenum, Integer pagesize) throws Exception {
        int a = pagesize*(pagenum-1);
        List<Manager> managers = managerDao.findByLikeName(query,a,pagesize);
        int size =  managerDao.manfindByName(query);
        Managerfind find = new Managerfind();
        List<Managerfind> lists = new ArrayList<>();
        for (Manager manager : managers) {
            find = new Managerfind();
            find.setId(manager.getMg_id());
            find.setUsername(manager.getMg_name());
            find.setMobile(manager.getMg_mobile());
            find.setType(manager.getMg_state());
            find.setEmail(manager.getMg_email());
            find.setCreate_time(manager.getMg_time());
            find.setRole_name(roleDao.NameById(manager.getRole_id()));
            if (manager.getMg_state() == 1) {
                find.setMg_state(true);
            } else if (manager.getMg_state() == 0) {
                find.setMg_state(false);
            }
            lists.add(find);
        }
        pobj.setUsers(lists);
        pobj.setPagenum(pagenum);
        pobj.setTotal(size);
        return pobj;
    }


    //将Manager填充进ManagerLogin
    public static ManagerLogin ManagerLogin(ManagerLogin login, Manager manager) {
        login.setId(manager.getMg_id());
        login.setRid(manager.getRole_id());
        login.setUsername(manager.getMg_name());
        login.setMobile(manager.getMg_mobile());
        login.setEmail(manager.getMg_email());
        return login;
    }
}
