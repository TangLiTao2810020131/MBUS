package com.ets.bus.socket.log.service;

import com.ets.bus.socket.log.dao.SocketLogDao;
import com.ets.bus.socket.log.entity.SocketLogBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author 宋晨
 * @create 2019/4/9
 * Scoket连接日志服务
 */
@Service
@Transactional
public class SocketLogService {
    @Resource
    private SocketLogDao socketLogDao;

    /**
     * 插入连接日志
     * @param socketLog
     */
    public void insertSocketLog(SocketLogBean socketLog){
        socketLogDao.insertSocketLog(socketLog);
    }

}
