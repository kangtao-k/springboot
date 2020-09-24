package com.md.service.goods;

import com.md.common.PageObj;
import com.md.entity.UserVo.ManagerLogin;
import com.md.entity.UserVo.ManagerModifyType;
import com.md.entity.UserVo.Manageradd;

public interface ManagerService {
    ManagerLogin loginManager(String username, String password) throws Exception;

    boolean addManager(String username, String password, String email, String mobile) throws Exception;

    Manageradd findByName(String username) throws Exception;

    ManagerModifyType modifyType(Integer uId, int mg_state) throws Exception;

    ManagerLogin findById(Integer id)throws Exception;

    ManagerLogin modifyById(Integer id, String email, String mobile)throws Exception;

    void delManager(Integer id)throws Exception;

    ManagerLogin modifyRoleById(Integer id, Integer rid)throws Exception;

    PageObj pagelist(Integer pagenum, Integer pagesize) throws Exception;

    PageObj pagelistquery(String query, Integer pagenum, Integer pagesize) throws Exception;
}
