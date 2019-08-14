package com.ets.bus.parmSet.roomTypeParm.web;

import javax.servlet.http.HttpServletRequest;

import com.ets.bus.parmSet.roomTypeParm.entity.RoomType;
import com.ets.bus.parmSet.roomTypeParm.service.RoomTypeService;
import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.entity.WaterMeterInfoMgt;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.service.WaterMeterInfoMgtService;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.ets.utils.PageListData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("roomtype")
public class RoomTypeController {

    @Autowired
    RoomTypeService roomTypeService;
    @Autowired
    WaterMeterInfoMgtService waterMeterInfoMgtService;
    @Autowired
    OperationLogService operationLogService;
    String moduleName="参数设置-房间类型参数";

    /**
     * 房价类型列表
     * @param request
     * @return
     */
    @RequestMapping("roomTypeParm")
    public String roomTypeParm(HttpServletRequest request)
    {
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看房间类型列表");
        operationLogService.addLog(operationLog);
        return "bus/parm-set/roomType-parm/roomType-parm";
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
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("page", (page) * limit);
        map.put("limit", (page-1) * limit);
        map.put("type_name",request.getParameter("type_name"));
        PageHelper.startPage(page,limit);
        List<RoomType> rList=roomTypeService.findRoomTypes(map);
        PageInfo<RoomType> pageInfo=new PageInfo<RoomType>(rList);
        PageListData<RoomType> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(rList);
        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }

    /**
     * 跳转到添加房间类型页面
     * @param request
     * @return
     */
    @RequestMapping("/addRoomTypePage.action")
    public String addRoomTypePage(HttpServletRequest request)
    {
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setOperaContent("查看房间类型参数");
        operationLog.setModuleName(moduleName);
       operationLogService.addLog(operationLog);
        return "bus/parm-set/roomType-parm/roomType-add";
    }

    /**
     * 添加房间类型逻辑实现
     * @param request
     * @param rt
     * @return
     */
    @RequestMapping("/addRoomType.action")
    @ResponseBody
    public String addRoomType(HttpServletRequest request,RoomType rt)
    {
        roomTypeService.addRoomType(rt);
        Gson gson = new Gson();
        String listJson = gson.toJson("添加成功");
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("新增["+rt.getType_name()+"]房间类型");
        operationLogService.addLog(operationLog);
        return listJson;
    }

    /**
     * 跳转到编辑房间类型页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editRoomTypePage.action")
    public String editRoomTypePage(HttpServletRequest request,String id)
    {
        //通过id查询房间类型信息
        RoomType rt=roomTypeService.findRoomType(id);
        request.setAttribute("rt",rt);
        return "bus/parm-set/roomType-parm/roomType-edit";
    }

    /**
     * 编辑房间类型逻辑实现（逻辑编辑，编辑一条记录前需要将原数据逻辑删除，后新增一条数据）
     * @param request
     * @param rt
     * @return
     */
    @RequestMapping("/editRoomType.action")
    @ResponseBody
    public String editRoomType(HttpServletRequest request,RoomType rt) {
        //逻辑删除编辑的房间信息
        roomTypeService.updateRoomTypeDelStatus(rt.getId());
        //新增一条数据房间类型保持不变
        roomTypeService.addRoomType(rt);
        Gson gson = new Gson();
        String listJson = gson.toJson("编辑成功");
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("编辑["+rt.getType_name()+"]房间类型");
        operationLogService.addLog(operationLog);
        return listJson;
    }

    /**
     * 逻辑删除房间类型
     * @param request
     * @param str
     * @param arr
     * @return
     */
    @RequestMapping("/delRoomType.action")
    @ResponseBody
    public String delRoomType(HttpServletRequest request,String str,String arr)
    {
        String[] ids=str.split(",");
        String[] typeNums=arr.split(",");
        Gson gson = new Gson();
        //删除之前判断房间类型是否被使用，已经使用不能被删除
        List<WaterMeterInfoMgt> wList=waterMeterInfoMgtService.findWatermeterInfoByRoomTypeId(typeNums);
        if(wList.size()!=0){
            return gson.toJson("no");
        }
        //添加删除日志
        StringBuilder sb=new StringBuilder();
        for(String st:ids){
            RoomType roomType = roomTypeService.infoRoomType(st);
            sb.append(roomType.getType_name()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]房间类型参数");
        operationLogService.addLog(operationLog);
        //批量删除房间类型（逻辑删除）批量更新房间类型删除状态
        roomTypeService.updateRoomTypeStatusPl(ids);
        String listJson = gson.toJson("yes");
        return listJson;
    }

    /**
     * 检测房间类型编码是否存在
     * @param request
     * @param roomTypeNum
     * @return
     */
    @RequestMapping("/checkRoomTypeNum.action")
    @ResponseBody
    public String checkRoomTypeNum(HttpServletRequest request,String roomTypeNum)
    {
        List<RoomType> rt=roomTypeService.checkRoomTypeNum(roomTypeNum);
        Gson gson=new Gson();
        if(rt.size()==0)
        {
            return gson.toJson("0");//房间类型编号不存在
        }else{
            return gson.toJson("1");//房间类型编号存在
        }
    }

    /**
     * 检测房间类型名称是否存在
     * @param request
     * @param roomTypeName
     * @return
     */
    @RequestMapping("/checkRoomTypeName.action")
    @ResponseBody
    public String checkRoomTypeName(HttpServletRequest request,String roomTypeName)
    {
        List<RoomType> rt=roomTypeService.checkRoomTypeName(roomTypeName);
        Gson gson=new Gson();
        if(rt.size()==0)
        {
            return gson.toJson("0");//房间类型名称不存在
        }else{
            return gson.toJson("1");//房间类型名称存在
        }
    }
}
