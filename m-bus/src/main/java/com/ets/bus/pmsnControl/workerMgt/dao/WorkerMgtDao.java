package com.ets.bus.pmsnControl.workerMgt.dao;

import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;
import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;

import java.util.List;
import java.util.Map;

public interface WorkerMgtDao {
    public List<mb_worker> getWorkers(Map map);
    public long getCount(Map map);
    public void insertWorker(mb_worker mbWorker);
    public int isCheckWorker(String workerAccount);
    public void deleteWorkerById(String[] id);
    public void closeWorkerById(String[] id);
    public void openWorkerById(String[] id);
    public void restPassword(Map map);
    public mb_worker infoWorker(String id);
    public void updateWorkerById(mb_worker mbWorker);
    public mb_worker getWorkerByWorkerAccount(String workerAccount);
    public void updatePssword(Map map);




}
