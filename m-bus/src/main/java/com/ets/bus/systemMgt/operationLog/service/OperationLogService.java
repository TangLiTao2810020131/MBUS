package com.ets.bus.systemMgt.operationLog.service;

import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.systemMgt.operationLog.dao.OperationLogDao;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OperationLogService {
    @Resource
    OperationLogDao operationLogDao;

    /**
     * 操作日志总集合
     * @param map
     * @return
     */
    public List<mb_operation_log> getOperaLogs(Map map){
        return  operationLogDao.getOperaLogs(map);
    }
    /**
     * 操作日志总记录数
     * @param map
     * @return
     */
    public long getCount(Map map){
        return operationLogDao.getCount(map);
    }
    /**
     * 添加操作日志
     * @param operationLog
     * @return
     */
    public void addLog(mb_operation_log operationLog){
        mb_worker workerSession= (mb_worker) SecurityUtils.getSubject().getSession().getAttribute("workerSession");
        operationLog.setWorkerName(workerSession.getWorkerName());
        operationLog.setId(UUIDUtils.getUUID());
        operationLog.setOperaTime(DateTimeUtils.getnowdate());
         operationLogDao.addLog(operationLog);
    }
    /**
     * 根据id查询对应的操作日志的信息
     * @param id
     * @return
     */
    public mb_operation_log infoLog(String id){
         return operationLogDao.infoLog(id);

    }
    /**
     * 根据id删除操作日志
     * @param id
     * @return
     */
    public void deleteLogById(String id){
        operationLogDao.deleteLogById(id);
    }


}
