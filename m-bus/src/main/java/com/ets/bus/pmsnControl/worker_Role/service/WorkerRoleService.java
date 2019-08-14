package com.ets.bus.pmsnControl.worker_Role.service;

import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;
import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.pmsnControl.worker_Role.dao.WorkerRoleDao;
import com.ets.bus.pmsnControl.worker_Role.entity.mb_worker_role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WorkerRoleService {
    @Resource
    WorkerRoleDao workerRoleDao;
    public void deleteRoleByWorkerId(String id){
        workerRoleDao.deleteRoleByWorkerId(id);
    }
    public void save(mb_worker_role mbWorkerRole){
        workerRoleDao.save(mbWorkerRole);
    }
    public List<String> getRoleNameByWorkerId(String id){
        return workerRoleDao.getRoleNameByWorkerId(id);
    }
    public List<mb_worker_role> getRoleByWorkerId(String id){
        return workerRoleDao.getRoleByWorkerId(id);
    }
    public List<mb_role> getRoles(String workerId){
        return  workerRoleDao.getRoles(workerId);
    }
    public void updateRoleByWorkerId(mb_worker worker){
        workerRoleDao.updateRoleByWorkerId(worker);
    }
    public mb_worker_role getRole(String workerId){
        return  workerRoleDao.getRole(workerId);
    };
}
