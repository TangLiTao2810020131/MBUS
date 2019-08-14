package com.ets.bus.pmsnControl.worker_Role.dao;

import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;
import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.pmsnControl.worker_Role.entity.mb_worker_role;

import java.util.List;
import java.util.Map;

public interface WorkerRoleDao {
    public void deleteRoleByWorkerId(String id);
    public void save(mb_worker_role mbWorkerRole);
    public List<String> getRoleNameByWorkerId(String id);
    public List<mb_worker_role> getRoleByWorkerId(String id);
    public List<mb_role> getRoles(String workerId);
    void updateRoleByWorkerId(mb_worker worker);
    mb_worker_role getRole(String workerId);
}
