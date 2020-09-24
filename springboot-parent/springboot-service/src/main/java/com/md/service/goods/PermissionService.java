package com.md.service.goods;


import com.md.entity.UserVo.MenusPer;
import com.md.entity.UserVo.PerTree;
import com.md.entity.UserVo.Perfind;

import java.util.List;

public interface PermissionService {
    List<Perfind> findPerlist() throws Exception;

    List<PerTree> findPertree() throws Exception;

    MenusPer menusPer()throws Exception;
}
