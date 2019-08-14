package com.ets.system.log.loginlog.web;

import com.ets.system.log.loginlog.entity.tb_login_log;
import com.ets.system.log.loginlog.service.LoginLogService;
import com.ets.utils.PageListData;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 20:31
 */
@Controller
@RequestMapping("loginlog")
public class LoginLogController {

    @Resource
    LoginLogService LoginLogService;

    @RequestMapping("list")
    public String list(HttpServletRequest request)
    {
        //将查看"登录日志列表"信息保存到操作日志
        return "system/log/loginlog/loginlog-list";
    }

    @RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(int page, int limit, tb_login_log logLogin)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username", logLogin.getUsername());
        map.put("ipaddress", logLogin.getIpaddress());
        map.put("logintime", logLogin.getLogintime());
        map.put("region", logLogin.getRegion());
        map.put("city", logLogin.getCity());
        map.put("isp", logLogin.getIsp());
        map.put("ostype", logLogin.getOstype());
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
        map.put("page", (page)*limit);//oracle
        map.put("limit", (page-1)*limit);//oracle

        List<tb_login_log> logs = LoginLogService.getLogs(map);
        long count = LoginLogService.getCount(map);


        PageListData<tb_login_log> pageData = new PageListData<tb_login_log>();

        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(logs);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }

    @RequestMapping("info")
    public String loginfo(String id, HttpServletRequest request)
    {
        //System.out.println(id);
        tb_login_log loginfo = LoginLogService.infoLog(id);

        request.setAttribute("loginfo", loginfo);
        return "system/loginlog/loginlog/log-info";
    }

}
