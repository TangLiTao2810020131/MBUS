package com.ets.bus.pmsnControl.roleMgt.dao;

import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;

import java.util.List;
import java.util.Map;

public interface RoleMgtDao {
    public List<mb_role> getRoles(Map map);
    public void addRole(mb_role entity);
    public void deleteRole(String id[]);
    public void updateRole(mb_role entity);
    public mb_role infoRole(String id);
    public long getCount();
    public List<mb_role> getAllRoles();
    public long getCountRoleName(String rolename);
}
