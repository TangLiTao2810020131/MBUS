package com.ets.district.room.web;

import com.ets.bus.parmSet.roomTypeParm.dao.RoomTypeDao;
import com.ets.bus.parmSet.roomTypeParm.entity.RoomType;
import com.ets.bus.parmSet.roomTypeParm.service.RoomTypeService;
import com.ets.bus.parmSet.sysRunningParm.entity.SysRunningParm;
import com.ets.bus.parmSet.sysRunningParm.service.SysRunningParmService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.entity.WaterMeterInfoMgt;
import com.ets.bus.systemMgt.watermeterRoomInfoMgt.service.WaterMeterInfoMgtService;
import com.ets.common.PageListData;
import com.ets.district.apartment.entity.tb_apartment;
import com.ets.district.apartment.service.ApartmentService;
import com.ets.district.floor.entity.tb_floor;
import com.ets.district.floor.service.FloorService;
import com.ets.district.layer.entity.tb_layer;
import com.ets.district.layer.service.LayerService;
import com.ets.district.region.entity.tb_region;
import com.ets.district.region.service.RegionService;
import com.ets.district.room.entity.tb_room;
import com.ets.district.room.service.RoomService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.awt.SystemColor.info;

/**
 * @author 吴浩
 * @create 2019-01-09 19:30
 */
@Controller
@RequestMapping("room")
public class RoomController {

    String baseUrl = "district/room/";
    
    @Resource
    RoomService roomService;
    @Resource
    LayerService layerService;
    @Resource
    FloorService floorService;
    @Resource
    ApartmentService apartmentService;
    @Resource
    RegionService regionService;
    @Resource
    RoomTypeService roomTypeService;
    @Resource
    RoomTypeDao roomTypeDao;
    @Resource
    WaterMeterInfoMgtService waterMeterInfoMgtService;
    @Resource
    OperationLogService operationLogService;

    String moduleName="区域管理-房间管理";
    /**
     * layui 加载页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("list")
    public String list(HttpServletRequest request,String id)
    {
    	request.setAttribute("parentid", id);
        return baseUrl + "room-list";
    }
    /**
     * layui 表格页面数据
     * @param parentid
     * @param page
     * @param limit
     * @param code
     * @param name
     * @return
     */
    @RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(String parentid,int page,int limit,String code,String name)
    {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code",code);
		map.put("roomname",name);
		map.put("page", (page) * limit);//oracle
		map.put("limit", (page-1) * limit);//oracle
		map.put("parentid", parentid);//oracle
		List<tb_room> room = roomService.getroom(map);
		long count = roomService.getCount(map);
		
		PageListData<tb_room> pageData = new PageListData<tb_room>();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(room);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
    }
    /**
     * 根据id查询出对应的房间信息
     * @param id
     * @return
     */
    
    @RequestMapping(value="info" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String info(String id)
    {
    	tb_room room = roomService.info(id);
    	Gson gson = new Gson();
		String json = gson.toJson(room);
		return json;
    }
    /**
     * 查询数据出放假所在区域楼栋楼层数据 跳转到详细信息页面
     * @param id
     * @param request
     * @param parentId
     * @return
     */
    @RequestMapping(value="detail" ,produces = "application/json; charset=utf-8")
    public String detail(String id,HttpServletRequest request,String parentId)
    {
        tb_room room = roomService.info(id);
        tb_layer layer = layerService.info(parentId);
        tb_floor floor = floorService.info(layer.getParentid());
        tb_apartment apartment = apartmentService.info(floor.getParentid());
        tb_region region = regionService.info(apartment.getParentid());
        request.setAttribute("room",room);
        request.setAttribute("layer",layer);
        request.setAttribute("floor",floor);
        request.setAttribute("apartment",apartment);
        request.setAttribute("region",region);
        //添加职员查看房间详细信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看["+region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"-"+layer.getName()+"-"+room.getName()+"]房间信息");
        operationLogService.addLog(operationLog);
        tb_room tbRoom = roomService.getDetails(id);

        request.setAttribute("tbRoom",tbRoom);
        return baseUrl+"room-info";
    }
    /**
     * 跳转新增页面
     * 查询出房间类型的集合 新增进行选择
     * @param request
     * @param parentid
     * @return
     */
    @RequestMapping("add")
    public String add(String parentid,HttpServletRequest request)
    {
    	request.setAttribute("parentid", parentid);
        List<RoomType> roomTypeList = roomTypeService.getAllRoomType();
        request.setAttribute("roomTypeList",roomTypeList);
        return baseUrl + "room-add";
    }
    /**
     * 跳转编辑页面
     * 查询出房间类型的集合 新增进行选择
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("edit")
    public String edit(String id,HttpServletRequest request)
    {
        List<RoomType> roomTypeList = roomTypeDao.getAllRoomType();
        request.setAttribute("roomTypeList",roomTypeList);
        WaterMeterInfoMgt info = waterMeterInfoMgtService.getInfoByRoomId(id);
        request.setAttribute("info",info);
        tb_room room = roomService.info(id);
        request.setAttribute("room",room);
        return baseUrl + "room-edit";
    }
    /**
     * 新增房间信息
     * @param room
     * @param roomTypeId
     * @return
     */
    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(tb_room room,String roomTypeId)
    {
        Gson gson = new Gson();
        roomService.save(room,roomTypeId);
        //添加职员新增房间的操作日志
        String name = getName(room);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("新增"+name);
        operationLogService.addLog(operationLog);
        return gson.toJson("操作成功");
    }
    /**
     * 编辑房间信息
     * @param room
     * @param roomTypeId
     * @return
     */
    @RequestMapping(value="update" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String update(tb_room room,String roomTypeId)
    {
        Gson gson = new Gson();
        String name = getName(room);
        //添加职员编辑房间信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("编辑"+name);
        operationLogService.addLog(operationLog);
        roomService.edit(room,roomTypeId);
        return gson.toJson("操作成功");
    }
    /**
     * 批量删除房间信息
     * @param id
     * @return
     */
    @RequestMapping(value="delete",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String delete(String id[])
    {
        //添加职员批量删除房间信息的操作日志
        StringBuilder sb=new StringBuilder();
        for(String str:id){
            tb_room room = roomService.info(str);
            tb_layer layer = layerService.info(room.getParentid());
            tb_floor floor = floorService.info(layer.getParentid());
            tb_apartment apartment = apartmentService.info(floor.getParentid());
            tb_region region = regionService.info(apartment.getParentid());
            sb.append(region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"-"+layer.getName()+"-"+room.getName()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]房间信息");
        operationLogService.addLog(operationLog);
    	roomService.deleteRoomById(id);
    	Gson gson=new Gson();
        return gson.toJson("");
    }

    /**
     * 检查房间编号的唯一性
     * @param room
     * @return
     */
   @RequestMapping(value="isCheckRoomCode" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckRoomCode(tb_room room){
        return roomService.isCheckRoomCode(room);
   }
    /**
     * 检查房间号的唯一性
     * @param room
     * @return
     */
    @RequestMapping(value="isCheckRoomNum" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckRoomNum(tb_room room){
        return roomService.isCheckRoomNum(room);
    }

    @RequestMapping(value="getRoom" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<tb_room> getRoom(String father){
        return roomService.selectRoomByParentId(father);
    }

    public String getName(tb_room room){
        tb_layer layer = layerService.info(room.getParentid());
        tb_floor floor = floorService.info(layer.getParentid());
        tb_apartment apartment = apartmentService.info(floor.getParentid());
        tb_region region = regionService.info(apartment.getParentid());
        return "["+region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"-"+layer.getName()+"-"+room.getName()+"]房间信息";
    }


}
