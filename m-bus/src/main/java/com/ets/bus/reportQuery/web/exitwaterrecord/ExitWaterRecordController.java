package com.ets.bus.reportQuery.web.exitwaterrecord;

import com.ets.bus.reportQuery.entity.exitwaterrecord.ExitWaterRecord;
import com.ets.bus.reportQuery.service.exitwaterrecord.ExitWaterRecordService;
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
@RequestMapping("exitWaterRecord")
public class ExitWaterRecordController
{
    @Autowired
    ExitWaterRecordService exitWaterRecordService;

    /**
     * layui 表格数据
     * @param request
     * @return
     */
    @RequestMapping("/listData.action")
    @ResponseBody
    public String listData(HttpServletRequest request)
    {
        Map<String,Object> map=exitWaterRecordService.getParamters(request);
        PageHelper.startPage(Integer.parseInt(request.getParameter("page")),Integer.parseInt(request.getParameter("limit")));
        List<ExitWaterRecord> eList=exitWaterRecordService.getExitWaterRecords(map);
        PageInfo<ExitWaterRecord> pageInfo=new PageInfo<ExitWaterRecord>(eList);
        PageListData<ExitWaterRecord> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(eList);
        return new Gson().toJson(pageData);
    }

    /**
     * 根据退水表ID查询退水信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/findExitRecord.action")
    public String findExitRecord(HttpServletRequest request,String id)
    {
        //根据退水表id查询购水信息
        ExitWaterRecord br=exitWaterRecordService.findExitRecord(id);
        request.setAttribute("br",br);
        return "bus/report-query/waterQuit-dtls-find";
    }

    /**
     * 退水记录表格数据导出
     * @param request
     * @param response
     * @param str
     * @param name
     */
    @RequestMapping("/export.action")
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        exitWaterRecordService.export(request,response,str,name);
    }
}