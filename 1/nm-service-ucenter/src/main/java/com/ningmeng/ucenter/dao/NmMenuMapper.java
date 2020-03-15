package com.ningmeng.ucenter.dao;

import com.ningmeng.framework.domain.ucenter.NmMenu;

import java.util.List;

/**
 * Created by 周周 on 2020/3/11.
 */
public interface NmMenuMapper{

      public List<NmMenu> selectPermissionByUserId(String id);
}
