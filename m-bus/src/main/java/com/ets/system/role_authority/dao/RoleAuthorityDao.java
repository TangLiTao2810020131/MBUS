package com.ets.system.role_authority.dao;

import com.ets.system.role_authority.entity.tb_role_authority;

import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 14:24
 */
public interface RoleAuthorityDao {

    public List<tb_role_authority> getAuthorityByRoleId(String id);

    public void save(tb_role_authority entity);

    public void deleteAythorityByRoleId(String id);

    public List<String> getAuthorityNameByRoleId(String roleId);

}
