package com.ets.bus.warning.web;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.warning.entity.Warning;
import com.ets.bus.warning.service.WarningService;
import com.ets.utils.PageListData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("warning")
public class WarningController
{
    @Autowired
    WarningService warningService;
    @Autowired
    OperationLogService operationLogService;

    /**
     * 报警记录列表
     * @param request
     * @return
     */
    @RequestMapping("/list.action")
    public String list(HttpServletRequest request)
    {
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-报警记录");
        mol.setOperaContent("查看报警记录列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/report-query/warning-record";
    }

    /**
     * 报警信息(根据报警类型分别查询
     * @param request
     * @param
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/warningData.action")
    @ResponseBody
    public String lowWater(HttpServletRequest request,int page,int limit)
    {
        PageHelper.startPage(page,limit);
        List<Warning> cellList=warningService.selectAllCell(warningService.getRequestParameter(request));
        PageInfo<Warning> pageInfo=new  PageInfo<Warning>(cellList);
        PageListData<Warning> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(cellList);
        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }

    //根据id查询报警信息
    @RequestMapping("/findWarningById.action")
    public String findWarningById(HttpServletRequest request,String id)
    {
        //根据id查询报警信息
        Warning waring=warningService.findWarningById(id);
        request.setAttribute("br",waring);
        return "bus/report-query/warning-record-find";
    }

    //报警记录导出
    @RequestMapping("/export.action")
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        warningService.export(request,response,str,name);
    }
}