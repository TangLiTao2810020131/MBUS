package com.ets.bus.reportQuery.web;

import com.ets.bus.reportQuery.entity.historyrecord.HistoryRecord;
import com.ets.bus.reportQuery.service.HistoryRecord.HistoryRecordService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("waterCollectRecord")
public class HistoryRecordController
{
    @Autowired
    HistoryRecordService historyRecordService;
    @Autowired
    OperationLogService operationLogService;

    /**
     *历史抄表列表
     * @param request
     * @return
     */
    @RequestMapping("/list.action")
    public String list(HttpServletRequest request)
    {
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-历史抄表");
        mol.setOperaContent("查看历史抄表列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/report-query/waterCollect-record";
    }

    /**
     * layui 表格数据
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/listData.action")
    @ResponseBody
    public String listData(HttpServletRequest request,int page,int limit)
    {
        Map<String,Object> map=historyRecordService.getRequestParams(request);
        PageHelper.startPage(page,limit);
        //查询所有的抄表记录
        List<HistoryRecord> hrList=historyRecordService.selectHistoryRecords(map);
        PageInfo<HistoryRecord> pageInfo=new PageInfo<HistoryRecord>(hrList);
        PageListData<HistoryRecord> pageListData=new PageListData<HistoryRecord>();
        pageListData.setData(hrList);
        pageListData.setCount(pageInfo.getTotal());
        pageListData.setMessage("");
        pageListData.setCode("0");
        return new Gson().toJson(pageListData);
    }

    /**
     * 根据历史抄表记录表id查看历史抄表记录信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/findHistoryRecordById.action")
    public String findHistoryRecordById(HttpServletRequest request,String id)
    {
        HistoryRecord br=historyRecordService.findHistoryRecordById(id);
        request.setAttribute("br",br);
        return "bus/report-query/waterCollect-record-find";
    }

    /**
     * 历史抄表信息导出
     * @param request
     * @param response
     * @param str
     * @param name
     */
    @RequestMapping("/export.action")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        historyRecordService.export(request,response,str,name);
    }
}