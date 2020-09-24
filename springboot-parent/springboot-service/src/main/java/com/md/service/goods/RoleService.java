package com.md.service.goods;


import com.md.entity.UserVo.OnePerInfo;
import com.md.entity.UserVo.RoleAdd;
import com.md.entity.UserVo.RolePerInfo;

import java.util.List;

public interface RoleService {
    List<RolePerInfo> roleListPer() throws Exception;

    RoleAdd addRole(String roleName, String roleDesc) throws Exception;

    RoleAdd findById(Integer id) throws Exception;

    RoleAdd modifyRole(Integer id, String roleName, String roleDesc) throws Exception;

    String delRoleById(Integer id) throws Exception;

    String modifyRights(Integer roleId, String rids) throws Exception;

    List<OnePerInfo> delRightsById(Integer roleId, Integer rightId) throws Exception;
}
