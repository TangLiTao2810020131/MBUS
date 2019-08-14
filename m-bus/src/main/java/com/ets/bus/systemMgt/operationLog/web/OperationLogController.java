package com.ets.bus.systemMgt.operationLog.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("operationlog")
public class OperationLogController {
    @Resource
    OperationLogService operationLogService;
    String baseUrl="bus/system-mgt/operation-log/";
    String moduleName="系统管理-职工操作日志";

    /**
     * layui 表格展示
     * @param request
     * @return
     */
    @RequestMapping("operationLog")
    public String operationLog(HttpServletRequest request)
    {
        //添加查看操作日志列表的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看操作日志列表");
        operationLogService.addLog(operationLog);

        return baseUrl+"operation-log";
    }
    /**
     * layui 表格展示数据
     * @param page
     * @param limit
     * @param startdate
     * @param enddate
     * @param workerName
     * @return
     */
    @RequestMapping(value = "listData",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(int page,int limit,String startdate,String enddate,String workerName){
        Map<String,Object> map=new HashMap<String,Object>();
        Gson gson=new Gson();
        try {
            map.put("page", (page)*limit);//oracle
            map.put("limit", (page-1)*limit);//oracle

            if(startdate != null && !"".equals(startdate)){
                startdate += " 00:00:00";
            }

            if(enddate != null && !"".equals(enddate)){
                enddate += " 23:59:59";
            }

            map.put("startdate", startdate);
            map.put("enddate", enddate);
            map.put("workerName",workerName);

            List<mb_operation_log> equipment =operationLogService.getOperaLogs(map);//操作日志集合
            long count = operationLogService.getCount(map);
            PageListData<mb_operation_log> pageData = new PageListData<mb_operation_log>();
            pageData.setCode("0");
            pageData.setCount(count);
            pageData.setMessage("");
            pageData.setData(equipment);
            return gson.toJson(pageData);

        }catch (Exception e){
            e.printStackTrace();
            return gson.toJson(new Message("2","操作失败!"));

        }
    }
    /**
     * 查看操作日志的详细信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "info",produces = "application/json; charset=utf-8")
    public String info(HttpServletRequest request,String id){
        mb_operation_log operationLog = operationLogService.infoLog(id);
        request.setAttribute("loginfo",operationLog);
        return baseUrl+"details-list";

    }
    /**
     * 删除操作日志
     * @param operationLog
     * @return
     */
    @RequestMapping(value="delete", produces = "application/json; charset=utf-8" )
    @ResponseBody
    public String delete(mb_operation_log operationLog)
    {
        operationLogService.deleteLogById(operationLog.getId());
        Gson gson = new Gson();
        return gson.toJson("");
    }


}
