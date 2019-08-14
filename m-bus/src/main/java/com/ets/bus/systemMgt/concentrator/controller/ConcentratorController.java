package com.ets.bus.systemMgt.concentrator.controller;

import com.ets.bus.systemMgt.clctApplMgt.entity.ClctApplMgt;
import com.ets.bus.systemMgt.clctApplMgt.service.ClctApplMgtService;
import com.ets.bus.systemMgt.concentrator.entity.Concentrator;
import com.ets.bus.systemMgt.concentrator.entity.RoomWaterInfo;
import com.ets.bus.systemMgt.concentrator.service.ConcentratorService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.waterMeterMgt.waterAddMgt.entity.RoomTypeVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.service.WaterPurchaseMgtService;
import com.ets.district.apartment.entity.tb_apartment;
import com.ets.district.apartment.service.ApartmentService;
import com.ets.district.region.entity.tb_region;
import com.ets.district.region.service.RegionService;
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
@RequestMapping("concentrator")
public class ConcentratorController
{
    @Autowired
    ConcentratorService concentratorService;
    @Autowired
    ClctApplMgtService clctApplMgtService;
    @Autowired
    ApartmentService apartmentService;
    @Autowired
    RegionService regionService;
    @Autowired
    WaterPurchaseMgtService waterPurchaseMgtService;
    @Autowired
    OperationLogService operationLogService;

    @RequestMapping("/list.action")
    public String list(HttpServletRequest request)
    {
        /**************添加操作日志start***************/
        mb_operation_log mol=new mb_operation_log();
        mol.setModuleName("设备管理-集中器管理-设置集中器");
        mol.setOperaContent("查看集中器列表");
        operationLogService.addLog(mol);
        /**************添加操作日志end****************/
        return "bus/system-mgt/concentrator/concentrator_list";
    }

    @RequestMapping("/listData.action")
    @ResponseBody
    public String listData(HttpServletRequest request, Concentrator concentrator, int page, int limit)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("page", (page)*limit);
        map.put("limit", (page-1)*limit);
        map.put("concentrator_num",request.getParameter("concentrator_num"));
        map.put("ip_address",request.getParameter("ip_address"));
        map.put("communication_mode",request.getParameter("communication_mode"));
        PageHelper.startPage(page,limit);
        List<Concentrator> list=concentratorService.getConcentrators(map);
        PageInfo<Concentrator> pageInfo=new PageInfo<Concentrator>(list);
        PageListData<Concentrator> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(list);
        return new Gson().toJson(pageData);
    }

    //跳转添加页面
    @RequestMapping("/addConcentratorPage.action")
    public String addConcentratorPage(HttpServletRequest request)
    {
        //从采集表中查询采集信息
        List<ClctApplMgt> cList=clctApplMgtService.findClctApplMgts();
        //获取所有的公寓信息
        List<tb_apartment> tList=apartmentService.findApartments();
        for(tb_apartment ta:tList)
        {
            //根据区域id查询区域信息
            tb_region tr=regionService.info(ta.getParentid());
            if(tr!=null)
            {
                ta.setName(tr.getName()+"/"+ta.getName());
            }
        }
        request.setAttribute("cList",cList);
        request.setAttribute("tList",tList);
        return "bus/system-mgt/concentrator/add";
    }

    //添加实现
    @RequestMapping("/addConcentrator.action")
    @ResponseBody
    public String addConcentrator(HttpServletRequest request,Concentrator concentrator)
    {
        concentratorService.addConcentrator(concentrator);
        Gson gson = new Gson();
        String json = gson.toJson("添加完成!");
        return json;
    }

    //跳转到编辑页面
    @RequestMapping("/editConcentratorPage.action")
    public String editConcentratorPage(HttpServletRequest request,String id)
    {
        Concentrator cc=concentratorService.findConcentrator(id);
        //从采集表中查询采集信息
        List<ClctApplMgt> cList=clctApplMgtService.findClctApplMgts();
        //获取所有的公寓信息
        List<tb_apartment> tList=apartmentService.findApartments();
        for(tb_apartment ta:tList)
        {
            //根据区域id查询区域信息
            tb_region tr=regionService.info(ta.getParentid());
            if(tr!=null)
            {
                ta.setName(tr.getName()+"/"+ta.getName());
            }
        }
        request.setAttribute("cList",cList);
        request.setAttribute("tList",tList);
        request.setAttribute("cc",cc);
        return "bus/system-mgt/concentrator/edit";
    }
    //编辑实现
    @RequestMapping("/editConcentrator.action")
    @ResponseBody
    public String editConcentrator(HttpServletRequest request,Concentrator concentrator)
    {
        concentratorService.editConcentrator(concentrator);
        Gson gson = new Gson();
        String json = gson.toJson("编辑完成!");
        return json;
    }

    //删除集中器
    @RequestMapping("/delConcentrators.action")
    @ResponseBody
    public String delConcentrators(HttpServletRequest request,String str,String concentratorId)
    {
        Gson gson = new Gson();
        String[] ids=str.split(",");
        for(String id:ids){
            //删除集中器之前查看集中器有没有绑定房间，绑定房间集中器不准删除
            if(concentratorService.findRoomByCobcentratorId(id).size()!=0)
            {
                return gson.toJson("0");
            }
        }
        concentratorService.delConcentrators(ids);
        return gson.toJson("删除成功!");
    }

    //查看集中器
    @RequestMapping("/findConcentratorPage.action")
    public String findConcentratorPage(HttpServletRequest request,String id)
    {
        Concentrator ct=concentratorService.findConcentrator(id);
        //根据采集表id查询采集信息
        ClctApplMgt cam=clctApplMgtService.findClctApplMgtById(ct.getCollect_id());
        ct.setCollect_name(cam.getCollect_name());
        //根据公寓id查询公寓信息
        try{
            ct.setApartment_name(apartmentService.info(ct.getApartment_id()).getName());
        }catch(NullPointerException ex){

        }
        request.setAttribute("ct",ct);
        return "bus/system-mgt/concentrator/concentrator_find";
    }

    //设置房间
    @RequestMapping("/setRoom.action")
    public String setRoom(HttpServletRequest request)
    {
        //查找所有的房间类型
        List<RoomTypeVo> rList=waterPurchaseMgtService.getRoomTypeList();
        request.setAttribute("roomTypeList",rList);
        request.setAttribute("concentratorId",request.getParameter("concentratorId"));
        return "bus/system-mgt/concentrator/set_room";
    }

    //查询房间关联水表信息(已经绑定集中器)
    @RequestMapping("/findRoomInfo.action")
    @ResponseBody
    public String findRoomInfo(HttpServletRequest request,int limit,int page,String concentratorId)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("room_num",request.getParameter("room_num"));
        map.put("room_type_id",request.getParameter("room_type_id"));
        map.put("apartment_name",request.getParameter("apartment_name"));
        map.put("floor_name",request.getParameter("floor_name"));
        map.put("concentratorId",concentratorId);
        PageHelper.startPage(Integer.parseInt(request.getParameter("page")),Integer.parseInt(request.getParameter("limit")));
        List<RoomWaterInfo> rList=concentratorService.findRoomInfo(map);
        PageInfo<RoomWaterInfo> pageInfo=new PageInfo<RoomWaterInfo>(rList);
        PageListData<RoomWaterInfo> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(rList);
        return new Gson().toJson(pageData);
    }

    //批量为集中器分配房间
    @ResponseBody
    @RequestMapping("/addRooomConcentrator.action")
    public String addRooomConcentrator(HttpServletRequest request,String str)
    {
        String[] ids=str.split(",");
        Map<String, Object> map=new HashMap<String,Object>();
        map.put("concentratorId",request.getParameter(("concentratorId")));
        map.put("ids",ids);
        concentratorService.addRooomConcentrator(map);
        Gson gson = new Gson();
        String json = gson.toJson("添加成功!");
        return json;
    }

    //表格数据导出
    @RequestMapping("/export.action")
    public void export(HttpServletRequest request, HttpServletResponse response,String str,String name)
    {
        concentratorService.export(request,response,str,name);
    }

    //检测集中器IP地址唯一性
    @RequestMapping("/checkConcentratorIp.action")
    @ResponseBody
    public String checkConcentratorIp(HttpServletRequest request,String ipAddress)
    {
        Concentrator concentraor=concentratorService.checkConcentratorIp(ipAddress);
        Gson gson=new Gson();
        if(concentraor==null)
        {
            return gson.toJson("0");//集中器IP地址不存在
        }else{
            return gson.toJson("1");//集中器IP地址已经存在
        }
    }

    //解除绑定
    @RequestMapping("/relieveRoom.action")
    public String reliveRoom(HttpServletRequest request)
    {
        //查找所有的房间类型
        List<RoomTypeVo> rList=waterPurchaseMgtService.getRoomTypeList();
        request.setAttribute("roomTypeList",rList);
        request.setAttribute("concentratorId",request.getParameter("concentratorId"));
        return "bus/system-mgt/concentrator/relive_room";
    }

    //查询房间关联水表信息(未绑定集中器)
    @RequestMapping("/findRoomInfoNotConcentrator.action")
    @ResponseBody
    public String findRoomInfoNotConcentrator(HttpServletRequest request,int limit,int page,String concentratorId)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("room_num",request.getParameter("room_num"));
        map.put("room_type_id",request.getParameter("room_type_id"));
        map.put("apartment_name",request.getParameter("apartment_name"));
        map.put("floor_name",request.getParameter("floor_name"));
        map.put("concentratorId",concentratorId);
        PageHelper.startPage(Integer.parseInt(request.getParameter("page")),Integer.parseInt(request.getParameter("limit")));
        List<RoomWaterInfo> rList=concentratorService.findRoomInfoNotConcentrator(map);
        PageInfo<RoomWaterInfo> pageInfo=new PageInfo<RoomWaterInfo>(rList);
        PageListData<RoomWaterInfo> pageData=new PageListData();
        pageData.setCode("0");
        pageData.setCount(pageInfo.getTotal());
        pageData.setMessage("");
        pageData.setData(rList);
        return new Gson().toJson(pageData);
    }

    //批量清空房间集中器信息
    @RequestMapping("/clearRoomConcentrator.action")
    @ResponseBody
    public String clearRoomConcentrator(HttpServletRequest request,String str)
    {
        String[] ids=str.split(",");
        concentratorService.clearRoomConcentrator(ids);
        return new Gson().toJson("操作成功");
    }
}