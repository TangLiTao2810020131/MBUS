package com.ets.district.floor.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.district.apartment.entity.tb_apartment;
import com.ets.district.apartment.service.ApartmentService;
import com.ets.district.region.entity.tb_region;
import com.ets.district.region.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.PageListData;
import com.ets.district.floor.entity.tb_floor;
import com.ets.district.floor.service.FloorService;
import com.google.gson.Gson;

/**
 * @author 吴浩
 * @create 2019-01-09 19:30
 */
@Controller
@RequestMapping("floor")
public class FloorController {

    String baseUrl = "district/floor/";
    
    @Resource
    FloorService floorService;
    @Resource
    ApartmentService apartmentService;
    @Resource
    RegionService regionService;
    @Autowired
    OperationLogService operationLogService;
    String moduleName="系统管理-楼栋管理";

    @RequestMapping("list")
    public String list(HttpServletRequest request,String id)
    {
    	request.setAttribute("parentid", id);
        return baseUrl + "floor-list";
    }
    /**
     * 楼栋管理 layui表格加载数据
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
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code",code);
		map.put("floorname",name);
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("page", (page) * limit);//oracle
		map.put("limit", (page-1) * limit);//oracle
		map.put("parentid", parentid);//oracle
		
		List<tb_floor> floor = floorService.getfloor(map);
		long count = floorService.getCount(map);
		
		PageListData<tb_floor> pageData = new PageListData<tb_floor>();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(floor);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
    }
    /**
     * 根据id查询查询对应的楼栋信息
     * @param id
     * @return
     */
    @RequestMapping(value="info" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String info(String id)
    {
        tb_floor floor = floorService.info(id);
    	Gson gson = new Gson();
		String json = gson.toJson(floor);
		return json;
    }
    /**
     * 跳转楼栋详细信息页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="detail" ,produces = "application/json; charset=utf-8")
    public String detail(String id,HttpServletRequest request,String parentId)
    {
        tb_floor floor = floorService.info(id);
        tb_apartment apartment = apartmentService.info(parentId);
        tb_region region = regionService.info(apartment.getParentid());
        request.setAttribute("floor",floor);
        request.setAttribute("apartment",apartment);
        request.setAttribute("region",region);
        //添加查看楼栋详细信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看["+region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"]楼栋信息");
        operationLogService.addLog(operationLog);
        return baseUrl+"floor-info";
    }

    /**
     * 跳转楼栋新增页面
     * @param parentid
     * @param request
     * @return
     */
    @RequestMapping("add")
    public String add(String parentid,HttpServletRequest request)
    {
    	request.setAttribute("parentid", parentid);
    	 return baseUrl + "floor-add";
    }
    /**
     * 跳转楼栋编辑页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("edit")
    public String edit(String id,HttpServletRequest request)
    {
        tb_floor floor = floorService.info(id);
        request.setAttribute("floor",floor);
        return baseUrl + "floor-edit";
    }
    /**
     * 新增楼栋信息
     * @param floor
     * @return
     */
    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(tb_floor floor )
    {
        tb_apartment apartment = apartmentService.info(floor.getParentid());
        tb_region region = regionService.info(apartment.getParentid());
        Gson gson = new Gson();
        floorService.save(floor);
        //添加新增楼层信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("新增["+region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"]楼层信息");
        operationLogService.addLog(operationLog);
        return gson.toJson("操作成功");
    }
    /**
     * 编辑楼栋信息
     * @param floor
     * @return
     */
    @RequestMapping(value="update" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String update(tb_floor floor)
    {
        tb_apartment apartment = apartmentService.info(floor.getParentid());
        tb_region region = regionService.info(apartment.getParentid());
        Gson gson = new Gson();
        floorService.edit(floor);
        //添加编辑楼层信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("编辑["+region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"]楼层信息");
        operationLogService.addLog(operationLog);
        return gson.toJson("操作成功");
    }
    /**
     * 批量删除楼栋信息
     * @param id
     * @return
     */
    @RequestMapping(value="delete" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String delete(String id[])
    {
        //添加批量删除楼栋信息的操作日志
        StringBuilder sb=new StringBuilder();
        for(String str:id){
            tb_floor floor = floorService.info(str);
            tb_apartment apartment = apartmentService.info(floor.getParentid());
            tb_region region = regionService.info(apartment.getParentid());
            sb.append(region.getName()+"-"+apartment.getName()+"-"+floor.getName()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]楼栋信息");
        operationLogService.addLog(operationLog);

    	floorService.deleteFloorById(id);
    	Gson gson=new Gson();
        return gson.toJson("");
    }
    /**
     * 检验楼栋编码的唯一性
     * @param floor
     * @return
     */
    @RequestMapping(value="isCheckFloorCode" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckFloorCode(tb_floor floor){
     return floorService.isCheckFloorCode(floor);
    }

    @RequestMapping(value="getFloor" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<tb_floor> getFloor(String father){
        return floorService.selectFloorByParentId(father);
    }


}
