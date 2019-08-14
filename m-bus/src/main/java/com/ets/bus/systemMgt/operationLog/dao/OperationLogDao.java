package com.ets.bus.systemMgt.operationLog.dao;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;

import java.util.List;
import java.util.Map;

public interface OperationLogDao {

    /**
     * 操作日志总集合
     * @param map
     * @return
     */
    public List<mb_operation_log> getOperaLogs(Map map); /**
     * 操作日志总记录数
     * @param map
     * @return
     */
    public long getCount(Map map);
    /**
     * 添加操作日志
     * @param operationLog
     * @return
     */
    public void addLog(mb_operation_log operationLog);
    /**
     * 根据id查询对应的操作日志的信息
     * @param id
     * @return
     */
    public mb_operation_log infoLog(String id);
    /**
     * 根据id删除操作日志
     * @param id
     * @return
     */
    public void deleteLogById(String id);

}
