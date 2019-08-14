package com.ets.bus.pmsnControl.roleMgt_Authority.dao;

import com.ets.bus.pmsnControl.roleMgt_Authority.entity.mb_role_authority;

import java.util.List;

public interface RoleMgtAuthorityDao {

    public List<mb_role_authority> getAuthorityByRoleId(String id);

    public void save(mb_role_authority entity);

    public void deleteAythorityByRoleId(String id);

    public List<String> getAuthorityNameByRoleId(String roleId);



}
