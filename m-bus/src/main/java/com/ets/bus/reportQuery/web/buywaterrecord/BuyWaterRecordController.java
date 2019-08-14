package com.ets.bus.reportQuery.web.buywaterrecord;

import com.ets.bus.reportQuery.entity.buywaterrecord.BuyWaterRecord;
import com.ets.bus.reportQuery.service.buywaterrecord.BuyWaterRecordService;
import com.ets.common.MyConstant;
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

@RequestMapping("/buyWaterRecord")
@Controller
public class BuyWaterRecordController
{
    @Autowired
    BuyWaterRecordService buyWaterRecordService;


    /**
     * layui 表格数据
     * @param request
     * @return
     */
    @RequestMapping("/listData.action")
    @ResponseBody
    public String listData(HttpServletRequest request)
    {
        Map<String,Object> map=buyWaterRecordService.getParameters(request);
        /**如果初始条件为空则使用默认分页******/
        int page = request.getParameter(MyConstant.PAGE_KEY) != null?Integer.parseInt(request.getParameter(MyConstant.PAGE_KEY)):MyConstant.PAGE_DEFULT;
        int limit = request.getParameter(MyConstant.LIMIT_KEY) != null? Integer.parseInt(request.getParameter(MyConstant.LIMIT_KEY)):MyConstant.LIMIT_DEFULT;
        /**mybatis开始分页******/
        PageHelper.startPage(page,limit);
        List<BuyWaterRecord> bList=buyWaterRecordService.getBuyWaterRecords(map);
        PageInfo<BuyWaterRecord> pageInfo=new PageInfo<BuyWaterRecord>(bList);
        /**mybatis分页结束******/
        PageListData<BuyWaterRecord> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(bList);
        return new Gson().toJson(pageData);
    }

    /**
     *  通过购水记录表ID查看购水记录
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/findBuyRecord.action")
    public String findBuyRecord(HttpServletRequest request,String id)
    {
        //根据购水表id查询购水信息
        BuyWaterRecord br=buyWaterRecordService.findBuyWaterRecordById(id);
        request.setAttribute("br",buyWaterRecordService.setBuyWaterType(br));
        return "bus/report-query/waterPay-record-find";
    }

    /**
     * 购水记录数据导出
     * @param request
     * @param response
     * @param str
     * @param name 文件导出后的标题名
     */
    @RequestMapping("/export.action")
    public void export(HttpServletRequest request, HttpServletResponse response,String str,String name)
    {
        buyWaterRecordService.export(request,response,str,name);
    }
}