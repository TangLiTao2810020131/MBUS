package com.ets.system.log.loginlog.service;

import com.ets.system.log.loginlog.dao.LoginLogDao;
import com.ets.system.log.loginlog.entity.IpBean;
import com.ets.system.log.loginlog.entity.tb_login_log;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.HttpUtils;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 吴浩
 * @create 2019-01-08 20:17
 */
@Service
@Transactional
public class LoginLogService {

    @Resource
    LoginLogDao logDao;

    //static  RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).setConnectionRequestTimeout(60000).build();

    public List<tb_login_log> getLogs(Map map)
    {
        return logDao.getLogs(map);
    }

    public long getCount(Map map)
    {
        return logDao.getCount(map);
    }

    public void addLog(HttpServletRequest request, String username)throws Exception
    {
        tb_login_log log = new tb_login_log();

        String agent = request.getHeader("user-agent");
        StringTokenizer st = new StringTokenizer(agent,";");
        st.nextToken();
        String useros = st.nextToken();

        String ipaddress = getIp(request);
        if(isInner(ipaddress)) //外网
        {
            String ipJson = getIpInfo(ipaddress);
            IpBean ipBean = new Gson().fromJson(ipJson,IpBean.class);
            if(ipBean != null){
                log.setCity(ipBean.getData().getCity());
                log.setIsp(ipBean.getData().getIsp());
                log.setRegion(ipBean.getData().getRegion());
            }else{
                log.setCity("服务器");
                log.setIsp("服务器");
                log.setRegion("服务器");
            }
        }
        else //内网
        {
            log.setCity("内网");
            log.setIsp("内网");
            log.setRegion("内网");

        }

        log.setIpaddress(ipaddress);
        log.setUsername(username);
        log.setOstype(useros);
        log.setId(UUIDUtils.getUUID());
        log.setLogintime(DateTimeUtils.getnowdate());
        logDao.addLog(log);
    }

    public tb_login_log infoLog(String id)
    {
        return logDao.infoLog(id);
    }

    public String getIpInfo(String ip)throws Exception
    {
        String host = "https://api01.aliyun.venuscn.com";
        String path = "/ip";
        String method = "GET";
        String appcode = "503d3e206a174c77a919d78cfe073b2d";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip", ip);

        String str = null;
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            str = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
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

    //判断是否内网IP
    public boolean isInner(String ip)
    {
        String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";//正则表达式=。 =、懒得做文字处理了、
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }



}
