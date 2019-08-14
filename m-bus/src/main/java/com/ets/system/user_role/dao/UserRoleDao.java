package com.ets.system.user_role.dao;

import com.ets.system.role.entity.tb_role;
import com.ets.system.user_role.entity.tb_user_role;

import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 14:54
 */
public interface UserRoleDao {

    public List<tb_user_role> getRoleByUserId(String id);

    public void save(tb_user_role entity);

    public void deleteRoleByUserId(String id);

    public List<String> getRoleNameByUserId(String userId);

    public List<tb_role> getRoles(String userId);

}
