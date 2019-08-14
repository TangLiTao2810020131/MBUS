package com.ets.bus.systemMgt.loginLogMgt.service;

import com.ets.bus.systemMgt.loginLogMgt.dao.LoginLogMgtDao;
import com.ets.bus.systemMgt.loginLogMgt.entity.mb_login_log;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LoginLogMgtService {
    @Autowired
    LoginLogMgtDao loginLogDao;

    /**
     * 登录日志总集合
     * @param map
     * @return
     */
    public List<mb_login_log> getLogs(Map map){
        return loginLogDao.getLogs(map);
    }
    /**
     * 登录日志总记录数
     * @param map
     * @return
     */
    public long getCount(Map map)
    {
        return loginLogDao.getCount(map);
    }
    /**
     * 根据id查询对应的登录日志的信息
     * @param id
     * @return
     */
    public mb_login_log infoLog(String id)
    {
        return loginLogDao.infoLog(id);
    }
    /**
     * 添加操作日志
     * @param request
     * @param log
     * @return
     */
    public void addLog(HttpServletRequest request, mb_login_log log)throws Exception
    {
        String ipaddress = getIp(request);
        log.setIpaddress(ipaddress);
        loginLogDao.addLog(log);
    }

    public  String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
