package com.ets.system.role.service;

import com.ets.system.role.dao.RoleDao;
import com.ets.system.role.entity.tb_role;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 14:30
 */
@Service
@Transactional
public class RoleService {


    @Resource
    RoleDao roleDao;

    public List<tb_role> getRoles(Map map)
    {
        return roleDao.getRoles(map);
    }

    //判断用户进行编辑操作还是新增操作
    public int insetRole(tb_role role)
    {
        long num = roleDao.findRole(role.getRolename());
        int result=1;

        if(role.getId()!=null && !role.getId().equals(""))
        {
            roleDao.updateRole(role);
            return result=-1;
        }
        else if (roleDao.findRole(role.getRolename()) == 0) {
            role.setId(UUIDUtils.getUUID());
            role.setCtime(DateTimeUtils.getnowdate());
            roleDao.addRole(role);
            return result=0;

        }
        return result;
    }

    public tb_role infoRole(String id)
    {
        return roleDao.infoRole(id);
    }

    public long getCount()
    {
        return roleDao.getCount();
    }

    public void deleteRole(String id[])
    {
        roleDao.deleteRole(id);
    }

    public List<tb_role> getAllRoles()
    {
        return roleDao.getAllRoles();
    }

}
