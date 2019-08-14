package com.ets.district.region.web;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.common.PageListData;
import com.ets.common.dtree.Data;
import com.ets.common.dtree.DtreeEntity;
import com.ets.district.apartment.service.ApartmentService;
import com.ets.district.floor.service.FloorService;
import com.ets.district.region.entity.tb_region;
import com.ets.district.region.service.RegionService;
import com.ets.district.room.service.RoomService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-09 19:30
 */
@Controller
@RequestMapping("region")
public class RegionController {

    String baseUrl = "district/region/";
    
    @Resource
    RegionService regionService;
    @Resource
    ApartmentService apartmentService;
    @Resource
    FloorService floorService;
    @Resource
    RoomService roomService;
    @Autowired
    OperationLogService operationLogService;
    String moduleName="系统管理-区域管理";
    /**
     * 加载区域---房间的树页面
     * @param request
     * @return
     */
    @RequestMapping("tree")
    public String tree(HttpServletRequest request){
        return  baseUrl+"region-tree";
    }
    /**
     * 点击树的节点 加载对应节点下的树的数据
     * @param request
     * @param level
     * @param parentId
     * @return
     */
    @RequestMapping(value="treeData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public DtreeEntity treeData(HttpServletRequest request, String nodeId , String parentId , String isLeaf , String context , String level, String spared)
    {

        DtreeEntity dtreeEntity = null;

        if(level==null)
        {
           /* dtreeEntity = remoteService.getProvinces();*/
            dtreeEntity=regionService.getTreeHead();
            return dtreeEntity;

        }
        else if(level.equals("1")){
            /*dtreeEntity = remoteService.getCitys(parentId);*/
            dtreeEntity = regionService.getRegions();

        }
        else if(level.equals("2"))
        {
           /* dtreeEntity = remoteService.getAreas(parentId);*/
            dtreeEntity = regionService.getApartments(parentId);


        }
        else if(level.equals("3"))
        {
            /* dtreeEntity = remoteService.getAreas(parentId);*/
            dtreeEntity=regionService.getFloors(parentId);

        }
        if(level.equals("4"))
       {
        /* dtreeEntity = remoteService.getAreas(parentId);*/
        dtreeEntity=regionService.getLayers(parentId);
       }


        return dtreeEntity;
    }

    //树的展示数据
    @RequestMapping(value="treeDataApartment" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public DtreeEntity treeDataApartment(HttpServletRequest request, String nodeId , String parentId , String isLeaf , String context , String level, String spared)
    {

        DtreeEntity dtreeEntity = null;

        if(level==null)
        {
            /* dtreeEntity = remoteService.getProvinces();*/
            dtreeEntity=regionService.getTreeHead();
            return dtreeEntity;

        }
        else if(level.equals("1")){
            /*dtreeEntity = remoteService.getCitys(parentId);*/
            dtreeEntity = regionService.getRegions();

        }
        else if(level.equals("2"))
        {
            /* dtreeEntity = remoteService.getAreas(parentId);*/
            dtreeEntity = regionService.getApartments(parentId);
            for (Data item : dtreeEntity.getData()){
                item.setIsLast(true);
            }
        }
        return dtreeEntity;
    }

    /**
     * 点击树的节点 加载对应节点所加载的页面路径
     * @param request
     * @param level
     * @param parentId
     * @return
     */
    @RequestMapping("list")
    public String list(HttpServletRequest request , String level,String parentId)
    {
        //职员查看区域管理列表信息对应的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName("系统管理-区域管理");
        operationLog.setOperaContent("查看区域列表信息");
        operationLogService.addLog(operationLog);
        System.out.println(parentId);
        request.setAttribute("parentid",parentId);
        if (level==null || level.equals("1")){
            operationLog.setModuleName("系统管理-区域管理");
            operationLog.setOperaContent("查看区域列表信息");
            operationLogService.addLog(operationLog);
            return baseUrl+"region-list";

        }
        else if (level.equals("2")){
            operationLog.setModuleName("系统管理-区域管理");
            operationLog.setOperaContent("查看公寓列表信息");
            operationLogService.addLog(operationLog);
            return "district/apartment/apartment-list";
        }
        else if (level.equals("3")){
            operationLog.setModuleName("系统管理-区域管理");
            operationLog.setOperaContent("查看楼栋列表信息");
            operationLogService.addLog(operationLog);
            return "district/floor/floor-list";

        }
        else if (level.equals("4")){
            operationLog.setModuleName("系统管理-区域管理");
            operationLog.setOperaContent("查看楼层列表信息");
            operationLogService.addLog(operationLog);
            return "district/layer/layer-list";

        }else if(level.equals("5")){
            operationLog.setModuleName("系统管理-区域管理");
            operationLog.setOperaContent("查看房间列表信息");
            operationLogService.addLog(operationLog);
            return "district/room/room-list";
        }

        return null;
    }
    /**
     * 区域管理 layui表格加载数据
     * @param page
     * @param limit
     * @param code
     * @param name
     * @return
     */
    @RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(int page,int limit,String code,String name)
    {
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code",code);
		map.put("regionname",name);
		map.put("page", (page) * limit);//oracle
		map.put("limit", (page-1) * limit);//oracle
		
		List<tb_region> region = regionService.getRegion(map);
		long count = regionService.getCount();
		
		PageListData<tb_region> pageData = new PageListData<tb_region>();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(region);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
    }
    /**
     * 根据id查询查询对应的区域信息
     * @param id
     * @return
     */
    @RequestMapping(value="info" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String info(String id)
    {
    	tb_region region = regionService.info(id);
    	return new Gson().toJson(region);
    }
    /**
     * 跳转区域详细信息页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="detail" ,produces = "application/json; charset=utf-8")
    public String detail(String id,HttpServletRequest request)
    {
        tb_region region = regionService.info(id);
        request.setAttribute("region",region);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看["+region.getName()+"]区域信息");
        operationLogService.addLog(operationLog);
        return baseUrl+"region-info";
    }
    /**
     * 跳转区域新增页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("add")
    public String add(String id,HttpServletRequest request)
    {
        return baseUrl + "region-add";
    }
    /**
     * 跳转区域编辑页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("edit")
    public String edit(String id,HttpServletRequest request)
    {
        tb_region region = regionService.info(id);
        request.setAttribute("region",region);
        return baseUrl + "region-edit";
    }
    /**
     * 新增区域信息
     * @param region
     * @return
     */
    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(tb_region region)
    {
        Gson gson = new Gson();
        regionService.save(region);
        //添加新增区域信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setOperaContent("新增["+region.getName()+"]区域");
        operationLog.setModuleName(moduleName);
        operationLogService.addLog(operationLog);
        return gson.toJson("操作成功");
    }
    /**
     * 编辑区域信息
     * @param region
     * @return
     */
    @RequestMapping(value="update" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String update(tb_region region)
    {
        Gson gson = new Gson();
        regionService.edit(region);
        //添加编辑区域信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setOperaContent("编辑["+region.getName()+"]区域");
        operationLog.setModuleName(moduleName);
        operationLogService.addLog(operationLog);
        return gson.toJson("操作成功");
    }
    /**
     * 批量删除区域信息
     * @param id
     * @return
     */
    @RequestMapping(value="delete" ,produces = "application/json; charset=utf-8" )
    @ResponseBody
    public String delete(String id[])
    {
        //添加批量删除区域信息的操作日志
        StringBuilder sb=new StringBuilder();
        for(String str:id){
            tb_region region= regionService.info(str);
            sb.append(region.getName()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]区域");
        operationLogService.addLog(operationLog);
        regionService.deleteRegionById(id);
        Gson gson = new Gson();
        return gson.toJson("");
    }
    /**
     * 根据区域编号查询该区域的个数 检验区域的唯一性
     * @param region
     * @return
     */
    @RequestMapping(value="isCheckRegionCode" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public long isCheckRegionCode(tb_region region)
    {
        return  regionService.isCheckRegionCode(region);

    }

}
