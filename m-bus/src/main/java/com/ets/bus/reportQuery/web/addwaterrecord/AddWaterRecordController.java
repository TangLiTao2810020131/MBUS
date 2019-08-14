package com.ets.bus.reportQuery.web.addwaterrecord;

import com.ets.bus.reportQuery.entity.addwaterrecord.AddWaterRecord;
import com.ets.bus.reportQuery.service.addwaterrecord.AddWaterRecordService;
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
@RequestMapping("addWaterRecord")
public class AddWaterRecordController
{
    @Autowired
    AddWaterRecordService addWaterRecordService;

    private final static int SUPPLEMENT_STATUS_0=0;//未下发
    private final static int SUPPLEMENT_STATUS_1=1;//下发成功
    private final static int SUPPLEMENT_STATUS_2=2;//下发失败

    /**
     * layui 表格数据
     * @param request
     * @return
     */
    @RequestMapping("/listData.action")
    @ResponseBody
    public String listData(HttpServletRequest request)
    {
        Map<String,Object> map=addWaterRecordService.getParamters(request);
        PageHelper.startPage(Integer.parseInt(request.getParameter("page")),Integer.parseInt(request.getParameter("limit")));
        List<AddWaterRecord> aList=addWaterRecordService.getAddWaterRecords(map);
        PageInfo<AddWaterRecord> pageInfo=new PageInfo<AddWaterRecord>(aList);
        PageListData<AddWaterRecord> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(aList);
        return new Gson().toJson(pageData);
    }

    /**
     * 根据补水表ID查看补水记录
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/findAddRecord.action")
    public String findAddRecord(HttpServletRequest request,String id)
    {
        //根据补水表id查询购水信息
        AddWaterRecord br=addWaterRecordService.findAddRecord(id);
        request.setAttribute("br",br);
        return "bus/report-query/waterAdd-dtls-find";
    }

    /**
     * 补水记录信息导出
     * @param request
     * @param response
     * @param str
     * @param name
     */
    @RequestMapping("/export.action")
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        addWaterRecordService.export(request,response,str,name);
    }
}