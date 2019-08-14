package com.ets.bus.pmsnControl.workerMgt.service;

import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;
import com.ets.bus.pmsnControl.workerMgt.dao.WorkerMgtDao;
import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkerMgtService {
    @Autowired
    WorkerMgtDao workerMgtDao;

    public List<mb_worker> getWorkers(Map map){
        return workerMgtDao.getWorkers(map);
    }
    public long getCount(Map map)
    {
        return workerMgtDao.getCount(map);
    }
    public void saveWorker(mb_worker mbWorker){
        workerMgtDao.insertWorker(mbWorker);
    }
    public int isCheckWorker(String workerAccount){
        return workerMgtDao.isCheckWorker(workerAccount);
    }
    public void deleteWorkerById(String[] id){
        workerMgtDao.deleteWorkerById(id);
    }
    public void closeWorkerById(String[] id){
        workerMgtDao.closeWorkerById(id);
    }
    public void openWorkerById(String[] id){
        workerMgtDao. openWorkerById(id);
    }

    public void restPassword(String[] id){
        Map map=new HashMap();
        String password=new SimpleHash("MD5", "123456", "1024").toHex();
        map.put("id",id);
        map.put("password",password);
        workerMgtDao.restPassword(map);
    }
    public mb_worker infoWorker(String id){
        return workerMgtDao.infoWorker(id);
    }
    public void updateWorkerById(mb_worker mbWorker){
        workerMgtDao.updateWorkerById(mbWorker);
    }
    public mb_worker getWorkerByWorkerAccount(String workerAccount){
        return workerMgtDao.getWorkerByWorkerAccount(workerAccount);
    }
    public void updatePssword(Map map){
        workerMgtDao.updatePssword(map);
    }

}
