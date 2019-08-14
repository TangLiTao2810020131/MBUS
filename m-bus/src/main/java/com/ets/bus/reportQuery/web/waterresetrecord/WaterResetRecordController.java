package com.ets.bus.reportQuery.web.waterresetrecord;

import com.ets.bus.reportQuery.entity.waterresetrecord.WaterResetRecord;
import com.ets.bus.reportQuery.service.waterresetrecord.WaterResetRecordService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.utils.PageListData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("waterResetRecord")
public class WaterResetRecordController
{
    @Autowired
    WaterResetRecordService waterResetRecordService;
    @Autowired
    WaterPurchaseMgtService waterPurchaseMgtService;

    private static final Logger logger = Logger.getLogger(WaterResetRecordController.class);

    /**
     * 清零记录列表信息
     * @param request
     * @return
     */
    @RequestMapping("/index.action")
    public String list(HttpServletRequest request)
    {
        List<RoomTypeVo> roomTypeList=null;
        try{
            //查询房间类型
            roomTypeList=waterPurchaseMgtService.getRoomTypeList();
        }catch(NullPointerException ex){
            logger.error("查询房间类型报错");
        }
        request.setAttribute("roomTypeList",roomTypeList);
        return "bus/report-query/water-reset-record";
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
        Map<String,Object> map=waterResetRecordService.getParamters(request);
        PageHelper.startPage(page,limit);
        List<WaterResetRecord> wList=waterResetRecordService.selectWaterResetRecord(map);
        PageInfo<WaterResetRecord> pageInfo=new  PageInfo<WaterResetRecord>(wList);
        PageListData<WaterResetRecord> pageListData=new PageListData<WaterResetRecord>();
        pageListData.setData(wList);
        pageListData.setCount(pageInfo.getTotal());
        pageListData.setMessage("");
        pageListData.setCode("0");
        return new Gson().toJson(pageListData);
    }

    /**
     * 根据清零记录表ID查询清零记录信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/findWaterReset.action")
    public String findWaterReset(HttpServletRequest request,String id)
    {
        WaterResetRecord br=null;
        List<RoomTypeVo> roomTypeList=null;
        try{
            //查询房间类型
            roomTypeList=waterPurchaseMgtService.getRoomTypeList();
            br=waterResetRecordService.findWaterReset(id);
        }catch(NullPointerException ex){
            logger.error("查看清零记录或查询房间类型报错");
        }
        request.setAttribute("br",br);
        request.setAttribute("roomTypeList",roomTypeList);
        return "bus/report-query/water-reset-record-find";
    }

    /**
     * 清零记录信息导出
     * @param request
     * @param response
     * @param str
     * @param name
     */
    @RequestMapping("/export.action")
    public void export(HttpServletRequest request, HttpServletResponse response, String str, String name)
    {
        try{
            waterResetRecordService.export(request,response,str,name);
        }catch(Exception ex){
            logger.error("清零记录报表导出报错");
        }

    }
}