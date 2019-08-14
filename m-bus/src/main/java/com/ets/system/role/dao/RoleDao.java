package com.ets.system.role.dao;

import com.ets.system.role.entity.tb_role;

import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 14:29
 */
public interface RoleDao {

    public List<tb_role> getRoles(Map map);

    public void addRole(tb_role entity);

    public void deleteRole(String id[]);

    public void updateRole(tb_role entity);

    public tb_role infoRole(String id);

    public long getCount();

    public List<tb_role> getAllRoles();

    //查询资源名称的个数
    public long findRole(String roleName);


}
