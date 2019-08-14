package com.ets.bus.systemMgt.loginLogMgt.dao;

import com.ets.bus.systemMgt.loginLogMgt.entity.mb_login_log;

import java.util.List;
import java.util.Map;

public interface LoginLogMgtDao {
    /**
     * 登录日志总集合
     * @param map
     * @return
     */
    public List<mb_login_log> getLogs(Map map);
    /**
     * 登录日志总记录数
     * @param map
     * @return
     */
    public long getCount(Map map);
    /**
     * 添加操作日志
     * @param log
     * @param log
     * @return
     */
    public void addLog(mb_login_log log);
    /**
     * 根据id查询对应的登录日志的信息
     * @param id
     * @return
     */
    public mb_login_log infoLog(String id);
}
