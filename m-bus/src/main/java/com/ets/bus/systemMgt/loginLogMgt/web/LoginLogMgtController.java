package com.ets.bus.systemMgt.loginLogMgt.web;


import com.ets.bus.systemMgt.loginLogMgt.entity.mb_login_log;
import com.ets.bus.systemMgt.loginLogMgt.service.LoginLogMgtService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.PageListData;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("loginLog")
public class LoginLogMgtController {
    @Autowired
    LoginLogMgtService logService;
    @Resource
    OperationLogService operationLogService;

    String baseUrl="bus/system-mgt/Login_log/";
    String moduleName="系统管理-登录日志";

    /**
     * layui 表格展示
     * @param request
     * @return
     */
    @RequestMapping("list")
    public String list(HttpServletRequest request)
    {
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看登录日志列表");
        operationLogService.addLog(operationLog);return baseUrl+"log-list";
    }
    /**
     * layui 表格展示数据
     * @param page
     * @param limit
     * @param loginLog
     * @return
     */
    @RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(int page, int limit, mb_login_log loginLog)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("workerAccount",loginLog.getWorkerAccount());
        map.put("workerName",loginLog.getWorkerName());
        map.put("ipaddress",loginLog.getIpaddress());
        map.put("loginTime",loginLog.getLoginTime());
        map.put("logintState",loginLog.getLoginState());
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
        map.put("page", (page)*limit);//oracle
        map.put("limit", (page-1)*limit);//oracle

        List<mb_login_log> logs = logService.getLogs(map);
        long count = logService.getCount(map);


        PageListData<mb_login_log> pageData = new PageListData<mb_login_log>();

        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(logs);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }
    /**
     * 查看登录日志的详细信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("info")
    public String loginfo(String id, HttpServletRequest request)
    {
        mb_login_log loginfo=logService.infoLog(id);
        request.setAttribute("loginfo", loginfo);
        return baseUrl+"log-info";
    }
}
