package com.ets.bus.pmsnControl.roleMgt_Authority.service;

import com.ets.bus.pmsnControl.roleMgt_Authority.dao.RoleMgtAuthorityDao;
import com.ets.bus.pmsnControl.roleMgt_Authority.entity.mb_role_authority;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoleMgtAuthorityService {

    @Resource
    RoleMgtAuthorityDao roleMgtAuthorityDao;

    public List<mb_role_authority> getAuthorityByRoleId(String id)
    {
        return roleMgtAuthorityDao.getAuthorityByRoleId(id);
    }

    public void save(mb_role_authority entity)
    {
        entity.setId(UUIDUtils.getUUID());
        roleMgtAuthorityDao.save(entity);
    }

    public void deleteAythorityByRoleId(String id)
    {
        roleMgtAuthorityDao.deleteAythorityByRoleId(id);
    }

    public List<String> getAuthorityNameByRoleId(String roleId)
    {
        return roleMgtAuthorityDao.getAuthorityNameByRoleId(roleId);
    }

}
