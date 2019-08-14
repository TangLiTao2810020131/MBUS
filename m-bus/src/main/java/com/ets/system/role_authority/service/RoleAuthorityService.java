package com.ets.system.role_authority.service;

import com.ets.system.role_authority.dao.RoleAuthorityDao;
import com.ets.system.role_authority.entity.tb_role_authority;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 14:26
 */
@Service
@Transactional
public class RoleAuthorityService {

    @Resource
    RoleAuthorityDao raDao;

    public List<tb_role_authority> getAuthorityByRoleId(String id)
    {
        return raDao.getAuthorityByRoleId(id);
    }

    public void save(tb_role_authority entity)
    {
        entity.setId(UUIDUtils.getUUID());
        raDao.save(entity);
    }

    public void deleteAythorityByRoleId(String id)
    {
        raDao.deleteAythorityByRoleId(id);
    }

    public List<String> getAuthorityNameByRoleId(String roleId)
    {
        return raDao.getAuthorityNameByRoleId(roleId);
    }

}
