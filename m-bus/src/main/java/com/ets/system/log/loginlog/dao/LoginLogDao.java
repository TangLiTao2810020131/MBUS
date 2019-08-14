package com.ets.system.log.loginlog.dao;

import com.ets.system.log.loginlog.entity.tb_login_log;

import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 20:16
 */
public interface LoginLogDao {

    public List<tb_login_log> getLogs(Map map);
    public long getCount(Map map);
    public void addLog(tb_login_log log);
    public tb_login_log infoLog(String id);

}
