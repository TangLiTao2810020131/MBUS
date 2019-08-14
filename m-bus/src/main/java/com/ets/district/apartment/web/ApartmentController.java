package com.ets.district.apartment.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.district.region.entity.tb_region;
import com.ets.district.region.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.PageListData;
import com.ets.district.apartment.entity.tb_apartment;
import com.ets.district.apartment.service.ApartmentService;
import com.google.gson.Gson;

/**
 * @author 吴浩
 * @create 2019-01-09 19:30
 */
@Controller
@RequestMapping("apartment")
public class ApartmentController {

    String baseUrl = "district/apartment/";
    
    @Resource
    ApartmentService apartmentService;
    @Resource
    RegionService regionService;
    @Autowired
    OperationLogService operationLogService;
    String moduleName="系统管理-公寓管理";

    @RequestMapping("list")
    public String list(HttpServletRequest request,String id)
    {
    	request.setAttribute("parentid", id);
        return baseUrl + "apartment-list";
    }
    /**
     * 公寓管理 layui表格加载数据
     * @param page
     * @param limit
     * @param code
     * @param name
     * @return
     */
    @RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(String parentId,int page,int limit,String code,String name)
    {
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
        map.put("code",code);
        map.put("apartmentname",name);
		map.put("page", (page) * limit);//oracle
		map.put("limit", (page-1) * limit);//oracle
		map.put("parentid", parentId);//oracle
		
		List<tb_apartment> apartment = apartmentService.getApartment(map);
		long count = apartmentService.getCount(map);
		
		PageListData<tb_apartment> pageData = new PageListData<tb_apartment>();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(apartment);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
    }
    /**
     * 根据id查询查询对应的公寓信息
     * @param id
     * @return
     */
    @RequestMapping(value="info" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String info(String id)
    {
    	tb_apartment apartment = apartmentService.info(id);
    	Gson gson = new Gson();
		String json = gson.toJson(apartment);
		return json;
    }
    /**
     * 跳转公寓详细信息页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="detail" ,produces = "application/json; charset=utf-8")
    public String detail(String id,String parentId,HttpServletRequest request)
    {
        tb_apartment apartment = apartmentService.info(id);
        tb_region region = regionService.info(parentId);
        request.setAttribute("region",region);
        request.setAttribute("apartment",apartment);
        //添加查看公寓详细信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看["+region.getName()+"-"+apartment.getName()+"]公寓信息");
        operationLogService.addLog(operationLog);
        return baseUrl+"apartment-info";
    }
    /**
     * 跳转公寓信息新增页面
     * @param parentid
     * @param request
     * @return
     */
    @RequestMapping("add")
    public String add(String parentid,HttpServletRequest request)
    {
    	request.setAttribute("parentid", parentid);

    	 return baseUrl + "apartment-add";
    }
    /**
     * 跳转公寓信息编辑页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("edit")
    public String edit(String id,HttpServletRequest request)
    {
        tb_apartment apartment = apartmentService.info(id);
        request.setAttribute("apartment",apartment);

    	 return baseUrl + "apartment-edit";
    }
    /**
     * 新增公寓信息
     * @param apartment
     * @return
     */
    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(tb_apartment apartment )
    {
        Gson gson = new Gson();
        apartmentService.save(apartment);
        tb_region region = regionService.info( apartment.getParentid());
        //添加新增公寓信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("新增["+region.getName()+"-"+apartment.getName()+"]公寓信息");
        operationLogService.addLog(operationLog);
        return gson.toJson("操作成功");
    }
    /**
     * 编辑公寓信息
     * @param apartment
     * @return
     */
    @RequestMapping(value="update" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String update(tb_apartment apartment)
    {
        Gson gson = new Gson();
        tb_region region = regionService.info( apartment.getParentid());
        //添加编辑公寓信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("编辑["+region.getName()+"-"+apartment.getName()+"]公寓信息");
        operationLogService.addLog(operationLog);
        apartmentService.edit(apartment);
        return gson.toJson("操作成功");
    }
    /**
     *批量删除公寓信息
     * @param id
     * @return
     */
    @RequestMapping(value="delete" )
    @ResponseBody
    public String delete(String id[])
    {
        //添加批量删除公寓信息的操作日志
        StringBuilder sb=new StringBuilder();
        for(String str:id){
            tb_apartment apartment = apartmentService.info(str);
            tb_region region = regionService.info(apartment.getParentid());
            sb.append(region.getName()+"-"+apartment.getName()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]公寓信息");
        operationLogService.addLog(operationLog);
    	apartmentService.deleteApartmentById(id);
    	Gson gson=new Gson();
        return gson.toJson("");
    }

    /**
     *检验公寓编码的唯一性
     * @param apartment
     * @return
     */
    @RequestMapping(value="isCheckApartmentCode" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public long  isCheckApartmentCode(tb_apartment apartment){
    return apartmentService.isCheckApartmentCode(apartment);
    }
    /*根据父级id得到对应的所有的公寓*/
    @RequestMapping(value="getApartment" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<tb_apartment> selectApartmentByParentId(String father){
        return apartmentService.selectApartmentByParentId(father);
    }

}
