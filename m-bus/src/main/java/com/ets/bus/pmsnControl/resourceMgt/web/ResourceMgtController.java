package com.ets.bus.pmsnControl.resourceMgt.web;



import com.ets.bus.pmsnControl.resourceMgt.entity.mb_resource;
import com.ets.bus.pmsnControl.resourceMgt.service.ResourceMgtService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.PageListData;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("resourceMgt")
public class ResourceMgtController {

	@Resource
    ResourceMgtService resourceMgtService;
	@Resource
	OperationLogService operationLogService;

String baseUrl="bus/pmsn-control/resource/";
String moduleName="权限控制-资源管理";
     /**
	 * 获取资源信息的列表
	 * @param request
	 * @return
	 */
@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		//添加查看资源列表的操作日志
		mb_operation_log operationLog=new mb_operation_log();
		operationLog.setModuleName(moduleName);
		operationLog.setOperaContent("查看资源列表");
		operationLogService.addLog(operationLog);
		return baseUrl+"resource-list";
	}
	/**
	 * 获取资源信息的列表数据
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit)
	{
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle

		List<mb_resource> resources = resourceMgtService.getResources(map);
		long count = resourceMgtService.getCount();


		PageListData<mb_resource> pageData = new PageListData<mb_resource>();

		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(resources);

		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}
	/**
	 * 获取权限信息的详细信息
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("info")
	public String resourceinfo(String id,HttpServletRequest request)
	{
		//System.out.println(id);
		mb_resource resource = resourceMgtService.infoResource(id);
		request.setAttribute("resource", resource);
		if(resource.getPid()!=null && !resource.getPid().equals("") && !resource.getPid().equals("0"))
		{
			String pname = resourceMgtService.getParentResourctByPernetId(resource.getPid()).getResourcename();
			request.setAttribute("pname", pname);
		}
		//添加查看资源详细信息的操作日志
		mb_operation_log operationLog=new mb_operation_log();
		operationLog.setModuleName(moduleName);
		operationLog.setOperaContent("查看"+"["+resource.getResourcename()+"]"+"资源详细信息");
		operationLogService.addLog(operationLog);
		return baseUrl+"resource-info";
	}
	/**
	 * 跳转新增资源或者编辑资源信息的页面（判断id 是否为空 为空则是新增页面 反之 则为编辑页面）
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("input")
	public String resourceinput(String id,HttpServletRequest request)
	{
		mb_resource resource = null;
		if(id!=null && !id.equals(""))
		{
			resource = resourceMgtService.infoResource(id);
			if(resource.getPid()!=null && !resource.getPid().equals("") && !resource.getPid().equals("0"))
		    {
				String pname = resourceMgtService.getParentResourctByPernetId(resource.getPid()).getResourcename();
				request.setAttribute("pname", pname);
			}

		}
		request.setAttribute("resource", resource);

		return baseUrl+"resource-input";
	}
	/**
	 * 新增资源或者编辑资源信息（判断id 是否为空 为空则是新增 反之 则为编辑）
	 * @param resource
	 * @param pname
	 * @return
	 */
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(mb_resource resource, String pname)
	{
		Gson gson = new Gson();
		if(pname!=null && !pname.equals(""))
		{
			List<mb_resource>  resourceList= resourceMgtService.getParentResourctByPernetName(pname);
			if(resourceList.size()==0)
			{
				return gson.toJson("上级资源名称不存在！");
			}else if(resourceList.size()==1){
				mb_resource mbResource = resourceList.get(0);
				resource.setPid(mbResource.getId());
			}else {
			for(mb_resource mbResource:resourceList){
				if (mbResource.getPid() != null){
					resource.setPid(mbResource.getId());
				}
			}
			}
		}
		resourceMgtService.insetResource(resource);

		return gson.toJson("操作成功");
	}
	/**
	 * 批量删除资源信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[])
	{
		//添加批量删除的操作日志
		StringBuilder sb=new StringBuilder();
		for(String str:id){
            mb_resource resource = resourceMgtService.infoResource(str);
            sb.append(resource.getResourcename()+",");
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]资源");
        operationLogService.addLog(operationLog);
		resourceMgtService.deleteResource(id);
		Gson gson = new Gson();

		return gson.toJson("");
	}
	/**
	 * 查看资源树
	 * @param request
	 * @return
	 */
	@RequestMapping(value="tree" )
	public String tree(HttpServletRequest request)
	{
		Gson gson = new Gson();
		request.setAttribute("tree", gson.toJson(resourceMgtService.getZtreeNodeList()));
		//添加查看资源树的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看资源树");
        operationLogService.addLog(operationLog);

		return baseUrl+"resource-tree";
	}
	/**
	 * 检验操作资源名称唯一性
	 * @param resource
	 * @return
	 */
	@RequestMapping(value = "isCheckResourcename", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCheckResourcename(mb_resource resource){
		return resourceMgtService.getResoureCount(resource);
	}

}
