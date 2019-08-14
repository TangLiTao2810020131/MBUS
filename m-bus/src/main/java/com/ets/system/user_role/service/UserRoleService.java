package com.ets.system.user_role.service;

import com.ets.system.role.entity.tb_role;
import com.ets.system.user_role.dao.UserRoleDao;
import com.ets.system.user_role.entity.tb_user_role;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 14:54
 */
@Service
@Transactional
public class UserRoleService {

    @Resource
    UserRoleDao urDao;

    public List<tb_user_role> getRoleByUserId(String id)
    {
        return urDao.getRoleByUserId(id);
    }

    public void save(tb_user_role entity)
    {
        entity.setId(UUIDUtils.getUUID());
        urDao.save(entity);
    }

    public void deleteRoleByUserId(String id)
    {
        urDao.deleteRoleByUserId(id);
    }

    public List<String> getRoleNameByUserId(String userId)
    {
        return urDao.getRoleNameByUserId(userId);
    }

    public List<tb_role> getRoles(String userId)
    {
        return urDao.getRoles(userId);
    }

}
