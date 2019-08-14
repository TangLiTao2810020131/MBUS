package com.ets.bus.systemMgt.waterMeterMgt.web;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.systemMgt.waterMeterMgt.entity.RoomWaterMeterVo;
import com.ets.bus.systemMgt.waterMeterMgt.entity.WaterMeterMgt;
import com.ets.bus.systemMgt.waterMeterMgt.service.WaterMeterMgtService;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.service.WaterMeterInfoMgtService;
import com.ets.common.MyConstant;
import com.ets.district.region.dao.RegionDao;
import com.ets.district.room.service.RoomService;
import com.ets.utils.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("waterMeterMgt")
public class WaterMeterMgtContorller {
    @Resource
    WaterMeterMgtService waterMeterMgtService;
    @Autowired
    RoomService roomService;
    @Autowired
    RegionDao regionDao;
    @Autowired
    WaterMeterInfoMgtService waterMeterInfoMgtService;
    @Autowired
    OperationLogService operationLogService;

    String baseUrl="bus/system-mgt/waterMeterMgt/";
    String moduleName="系统管理-水表管理";
    /**
     * 水表信息列表 加载页面
     * @param request
     * @return
     */
    @RequestMapping("waterMeterMgt")
    public String waterMeterMgt(HttpServletRequest request)
    {
        //添加查看水表信息列表的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看水表信息列表");
        operationLogService.addLog(operationLog);
        return baseUrl+"waterMeterMgt";
    }
    /**
     * 水表信息列表的数据加载
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(HttpServletRequest request, WaterMeterMgt waterMeterMgt, int page, int limit)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("page", (page)*limit);
        map.put("limit", (page-1)*limit);
        map.put("water_meter_id",request.getParameter("water_meter_id"));
        map.put("type",request.getParameter("type"));
        map.put("create_time",request.getParameter("create_time"));
        List<WaterMeterMgt> allWaterMeter =waterMeterMgtService.getWaterMeterMgts(map);
        long count=waterMeterMgtService.getCount(map);
        PageListData<WaterMeterMgt> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(allWaterMeter);
        return new Gson().toJson(pageData);
    }
    /**
     * 跳转新增水表页面
     * @param request
     * @return
     */
    @RequestMapping("addWaterMeterMgt")
    public String addWaterMeterMgtPage(HttpServletRequest request)
    {
      /*  List<tb_region> regionList=regionDao.getRegions();
        request.setAttribute("regionList",regionList);*/
        return baseUrl+"add";
    }
    /**
     * 新增水表信息
     * @param request
     * @return
     */
    @RequestMapping(value = "saveWaterMeterMgt", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String saveWaterMeterMgt(HttpServletRequest request, WaterMeterMgt waterMeterMgt)
    {
        waterMeterMgt.setId(UUIDUtils.getUUID());
        waterMeterMgt.setCreate_time(DateTimeUtils.getnowdate());
        waterMeterMgt.setDelStatus(0);
        waterMeterMgtService.saveWaterMeter(waterMeterMgt);
        //添加新增水表信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("新增编号为["+waterMeterMgt.getWater_meter_id()+"]水表信息");
        operationLogService.addLog(operationLog);

        return new Gson().toJson("新增成功");
    }
    /**
     * 跳转编辑水表的页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("edit")
    public String edit(HttpServletRequest request,String id)
    {
        WaterMeterMgt waterMeter = waterMeterMgtService.getWaterMeterById(id);
        request.setAttribute("waterMeter",waterMeter);
        return baseUrl+"edit";
    }
    /**
     * 检验水表编号的唯一性（根据水表编号查询对应的数量）
     * @param waterMeterMgt
     * @return
     */
    @RequestMapping(value = "isCheckWaterMeterId", produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckWaterMeterId(WaterMeterMgt waterMeterMgt){
        return waterMeterMgtService.isCheckWaterMeterId(waterMeterMgt.getWater_meter_id());
    }
    /**
     * 编辑水表信息
     * @param request
     * @param waterMeterMgt
     * @return
     */
    @RequestMapping(value = "editWaterMeterMgt", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String editWaterMeterMgt(HttpServletRequest request,WaterMeterMgt waterMeterMgt)
    {
        //添加编辑水表信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("编辑编号为["+waterMeterMgt.getWater_meter_id()+"]水表信息");
        operationLogService.addLog(operationLog);
      waterMeterMgtService.updateWaterMeter(waterMeterMgt);
        return new Gson().toJson("编辑成功");
    }
    /**
     * 批量删除水表信息（在删除之前检验是否绑定房间）
     * @param id
     * @return
     */
    @RequestMapping(value = "delWaterMeterMgt", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String delWaterMeterMgt(String[] id)
    {
        //添加批量删除删除日志
        StringBuilder sb=new StringBuilder();
        for(String st:id){
            WaterMeterMgt waterMeterMgt = waterMeterMgtService.getWaterMeterById(st);
            sb.append(waterMeterMgt.getWater_meter_id()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除水表编号为["+sb.substring(0,sb.length()-1)+"]水表信息");
        operationLogService.addLog(operationLog);
        waterMeterMgtService.delWaterMeterMgts(id);
        return new Gson().toJson("删除成功");
    }

    /**
     * 跳转绑定房间的页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("bindRoom")
    public String bindRoom(HttpServletRequest request,String id)
    {
        return baseUrl+"bindRoom";
    }
    /**
     * 绑定房间的页面树的数据
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "bindRoomData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String bindRoomData(HttpServletRequest request, int page, int limit )
    {
        String level=request.getParameter("level");
        String parentId=request.getParameter("parentId");
        Map<String, Object> map = new HashMap<String, Object>();
        List<RoomWaterMeterVo> allRoom=null;
        long allRoomCount=0;
        map.put("page", (page) * limit);
        map.put("limit", (page - 1) * limit);
        if ( level == null || level.equals("1") ){

        } else if (level.equals("2")){
            map.put("regionId",parentId);

    }else if (level.equals("3")){
            map.put("apartmentId",parentId);

        }else if (level.equals("4")){
            map.put("buildId",parentId);

        }else if (level.equals("5")){
            map.put("floorId",parentId);

        }
        allRoom= waterMeterMgtService.getAllRoom(map);
        allRoomCount = waterMeterMgtService.getAllRoomCount(map);

     PageListData<RoomWaterMeterVo> pageData = new PageListData();
        pageData.setCode("0");
        pageData.setCount(allRoomCount);
        pageData.setMessage("");
        pageData.setData(allRoom);
        return  new Gson().toJson(pageData);
    }

    /**
     * 批量解除水表信息（在解除绑定之前检验是否绑定房间）
     * @param id
     * @return
     */
    @RequestMapping(value = "cancelBind", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String cancelBind(String[] id)
    {
        //添加批量删除删除日志
        StringBuilder sb=new StringBuilder();
        for(String st:id){
            WaterMeterMgt waterMeterMgt = waterMeterMgtService.getWaterMeterById(st);
            sb.append(waterMeterMgt.getWater_meter_id()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("解除绑定水表编号为["+sb.substring(0,sb.length()-1)+"]水表信息");
        operationLogService.addLog(operationLog);
        waterMeterMgtService.cancelBind(id);
        return new Gson().toJson("解除绑定成功");
    }

    /**
     * 跳转查看水表信息的详情页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "waterMeterRoomInfo", produces = "application/json; charset=utf-8")
    public String waterMeterRoomInfo(String id,HttpServletRequest request){
        RoomWaterMeterVo roomWaterMeterVo =waterMeterMgtService.waterMeterRoomInfo(id);
        request.setAttribute("roomWaterMeterVo",roomWaterMeterVo);
        //添加查看水表详情的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看水表编号为["+roomWaterMeterVo.getWater_meter_id()+"]详细信息");
        operationLogService.addLog(operationLog);
        return baseUrl+"info";
    }

    @RequestMapping(value = "waterImport")
    public String waterImport(HttpServletRequest request){

        return baseUrl+"water-import";
    }

    /**
     * 下载模板
     * @param request
     * @param response
     */
    @RequestMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        FileUploadUtil.download(MyConstant.EXCEL_TEM_ADDWATER,response);

    }

    @PostMapping("upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file){
        waterMeterMgtService.upload(file);
        return null;
    }
}
