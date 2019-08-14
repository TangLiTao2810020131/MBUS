package com.ets.system.log.optionlog.dao;

import com.ets.system.log.loginlog.entity.tb_login_log;
import com.ets.system.log.optionlog.entity.tb_option_log;

import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 20:42
 */
public interface OptionLogDao {

    public List<tb_option_log> getLogs(Map map);
    public long getCount(Map map);
    public void addLog(tb_option_log log);
    public tb_option_log infoLog(String id);


}
