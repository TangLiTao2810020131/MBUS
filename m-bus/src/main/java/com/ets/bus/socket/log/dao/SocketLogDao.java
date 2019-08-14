package com.ets.bus.socket.log.dao;

import com.ets.bus.socket.log.entity.SocketLogBean;

/**
 * @author 宋晨
 * @create 2019/4/9
 * 购水管理Dao
 */
public interface SocketLogDao {

    /**
     * 插入连接日志
     * @param socketLog
     */
    void insertSocketLog(SocketLogBean socketLog);

}
