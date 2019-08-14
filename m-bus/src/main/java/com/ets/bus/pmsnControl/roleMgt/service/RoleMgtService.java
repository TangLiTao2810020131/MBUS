package com.ets.bus.pmsnControl.roleMgt.service;

import com.ets.bus.pmsnControl.roleMgt.dao.RoleMgtDao;
import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleMgtService {
    @Autowired
    RoleMgtDao roleMgtDao;
    @Resource
    OperationLogService operationLogService;


    public List<mb_role> getRoles(Map map)
    {
        return roleMgtDao.getRoles(map);
    }

    //判断用户进行编辑操作还是新增操作
    public void insetRole(mb_role role)
    {
        if (role.getId()!=null && !role.getId().equals("")){
            roleMgtDao.updateRole(role);
            //添加角色编辑日志
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName("权限控制-角色管理");
            operationLog.setOperaContent("编辑"+"["+role.getRolename()+"]"+"角色信息");
            operationLogService.addLog(operationLog);
        }else {
            role.setCtime(DateTimeUtils.getnowdate());
            role.setId(UUIDUtils.getUUID());
            roleMgtDao.addRole(role);
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName("权限控制-角色管理");
            operationLog.setOperaContent("新增"+"["+role.getRolename()+"]"+"角色信息");
            operationLogService.addLog(operationLog);
        }
    }

    public mb_role infoRole(String id)
    {
        return roleMgtDao.infoRole(id);
    }

    public long getCount()
    {
        return roleMgtDao.getCount();
    }

    public void deleteRole(String id[])
    {
        roleMgtDao.deleteRole(id);
    }

    public List<mb_role> getAllRoles()
    {
        return roleMgtDao.getAllRoles();
    }
    public long getCountRoleName(String rolename){return roleMgtDao.getCountRoleName(rolename);}

}
