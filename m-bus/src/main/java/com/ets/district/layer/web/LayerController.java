package com.ets.district.layer.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.district.apartment.entity.tb_apartment;
import com.ets.district.apartment.service.ApartmentService;
import com.ets.district.floor.entity.tb_floor;
import com.ets.district.floor.service.FloorService;
import com.ets.district.region.entity.tb_region;
import com.ets.district.region.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.PageListData;
import com.ets.district.layer.entity.tb_layer;
import com.ets.district.layer.service.LayerService;
import com.google.gson.Gson;

/**
 * @author 吴浩
 * @create 2019-01-09 19:30
 */
@Controller
@RequestMapping("layer")
public class LayerController {

    String baseUrl = "district/layer/";
    
    @Resource
    LayerService layerService;
    @Resource
    RegionService regionService;
    @Resource
    ApartmentService apartmentService;
    @Resource
    FloorService floorService;
    @Autowired
    OperationLogService operationLogService;
    String moduleName="区域管理-楼层管理";

    @RequestMapping("list")
    public String list(HttpServletRequest request,String id)
    {
    	request.setAttribute("parentid", id);
        return baseUrl + "layer-list";
    }
    /**
     * 楼层管理 layui表格加载数据
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
		map.put("layername",name);
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("page", (page) * limit);//oracle
		map.put("limit", (page-1) * limit);//oracle
		map.put("parentid", parentid);//oracle
		
		List<tb_layer> layer = layerService.getlayer(map);
		long count = layerService.getCount(map);
		
		PageListData<tb_layer> pageData = new PageListData<tb_layer>();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(layer);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
    }
    /**
     * 根据id查询查询对应的楼层
     * 信息
     * @param id
     * @return
     */
    @RequestMapping(value="info" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String info(String id)
    {
    	tb_layer layer = layerService.info(id);
    	
    	Gson gson = new Gson();
		String json = gson.toJson(layer);
		return json;
    }
    /**
     * 跳转楼层详细信息页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="detail" ,produces = "application/json; charset=utf-8")
    public String detail(String id,String parentId,HttpServletRequest request)
    {
        tb_layer layer = layerService.info(id);
        tb_floor floor = floorService.info(parentId);
        tb_apartment apartment = apartmentService.info(floor.getParentid());
        tb_region region = regionService.info(apartment.getParentid());
         request.setAttribute("layer",layer);
        request.setAttribute("floor",floor);
        request.setAttribute("apartment",apartment);
        request.setAttribute("region",region);
        //添加查看楼层详细信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看["+region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"-"+layer.getName()+"]楼层信息");
        operationLogService.addLog(operationLog);
        return baseUrl+"layer-info";
    }
    /**
     * 跳转楼层新增页面
     * @param parentid
     * @param request
     * @return
     */
    @RequestMapping("add")
    public String add(String parentid,HttpServletRequest request)
    {
    	request.setAttribute("parentid", parentid);
    	 return baseUrl + "layer-add";
    }
    /**
     * 跳转楼层编辑页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("edit")
    public String edit(String id,HttpServletRequest request)
    {
        tb_layer layer = layerService.info(id);
        request.setAttribute("layer",layer);
        return baseUrl + "layer-edit";
    }
    /**
     * 新增楼层信息
     * @param layer
     * @return
     */
    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(tb_layer layer )
    {

        tb_floor floor = floorService.info(layer.getParentid());
        tb_apartment apartment = apartmentService.info(floor.getParentid());
        tb_region region = regionService.info(apartment.getParentid());
        Gson gson = new Gson();
        layerService.save(layer);
        //添加新增楼层信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("新增["+region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"-"+layer.getName()+"]楼层信息");
        operationLogService.addLog(operationLog);
        return gson.toJson("操作成功");
    }
    /**
     * 编辑楼层信息
     * @param layer
     * @return
     */
    @RequestMapping(value="update" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String update(tb_layer layer)
    {
        tb_floor floor = floorService.info(layer.getParentid());
        tb_apartment apartment = apartmentService.info(floor.getParentid());
        tb_region region = regionService.info(apartment.getParentid());
        //添加编辑楼层信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("编辑["+region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"-"+layer.getName()+"]楼层信息");
        operationLogService.addLog(operationLog);
        Gson gson = new Gson();
        layerService.edit(layer);
        return gson.toJson("操作成功");
    }
    /**
     * 批量删除楼层信息
     * @param id
     * @return
     */
    @RequestMapping(value="delete" )
    @ResponseBody
    public String delete(String id[])
    {
        //添加批量删除楼层信息的操作日志
        StringBuilder sb=new StringBuilder();
        for(String str:id){
            tb_layer layer = layerService.info(str);
            tb_floor floor = floorService.info(layer.getParentid());
            tb_apartment apartment = apartmentService.info(floor.getParentid());
            tb_region region = regionService.info(apartment.getParentid());
            sb.append(region.getName()+"-"+apartment.getName()+"-"+floor.getName()+"-"+layer.getName()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]楼层信息");
        operationLogService.addLog(operationLog);

    	layerService.deleteLayerById(id);
    	Gson gson=new Gson();
        return gson.toJson("");
    }
    /**
     * 检验楼层编码的唯一性
     * @param layer
     * @return
     */
    @RequestMapping(value="isCheckLayerCode" ,produces = "application/json; charset=utf-8")
    @ResponseBody
   public long isCheckLayerCode(tb_layer layer){
        return layerService.isCheckLayerCode(layer);
    }
    @RequestMapping(value="getLayer" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<tb_layer> getLayer(String father){
        return layerService.selectLayerByParentId(father);
    }

}
