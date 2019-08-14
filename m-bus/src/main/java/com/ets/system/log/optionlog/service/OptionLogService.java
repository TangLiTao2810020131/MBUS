package com.ets.system.log.optionlog.service;

import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.system.log.optionlog.dao.OptionLogDao;
import com.ets.system.log.optionlog.entity.tb_option_log;
import com.ets.system.user.entity.tb_user;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 20:45
 */
@Service
@Transactional
public class OptionLogService {

    @Resource
    OptionLogDao logDao;

    public List<tb_option_log> getLogs(Map map)
    {
        return logDao.getLogs(map);
    }

    public long getCount(Map map)
    {
        return logDao.getCount(map);
    }

    public void addLog(tb_option_log log)
    {
        //tb_user user = (tb_user)SecurityUtils.getSubject().getSession().getAttribute("userSession");

        Object obj = SecurityUtils.getSubject().getSession().getAttribute("workerSession");
        if(obj instanceof mb_worker)
        {
            mb_worker user = (mb_worker)obj;
            log.setUsername(user.getWorkerName());
        }
        else
        {
            Map<String,String> map = (Map)obj;
            log.setUsername((String)map.get("CUSTOMERNAME")+"-"+(String)map.get("USERNAME"));
        }

        log.setId(UUIDUtils.getUUID());
        log.setOprtime(DateTimeUtils.getnowdate());
        logDao.addLog(log);
    }

    public tb_option_log infoLog(String id)
    {
        return logDao.infoLog(id);
    }

}
