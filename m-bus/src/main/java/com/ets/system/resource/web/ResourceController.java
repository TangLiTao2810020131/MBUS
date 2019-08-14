package com.ets.system.resource.web;

import com.ets.system.resource.entity.tb_resource;
import com.ets.system.resource.service.ResourceService;
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

/**
 * @author 吴浩
 * @create 2019-01-08 14:11
 */
@Controller
@RequestMapping("resource")
public class ResourceController {


    @Resource
    ResourceService resourceService;


    @RequestMapping("list")
    public String list(HttpServletRequest request)
    {


        return "system/resource/resource-list";
    }

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

        List<tb_resource> resources = resourceService.getResources(map);
        long count = resourceService.getCount();


        PageListData<tb_resource> pageData = new PageListData<tb_resource>();

        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(resources);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }

    @RequestMapping("info")
    public String resourceinfo(String id,HttpServletRequest request)
    {
        //System.out.println(id);
        tb_resource resource = resourceService.infoResource(id);
        request.setAttribute("resource", resource);
        if(resource.getPid()!=null && !resource.getPid().equals("") && !resource.getPid().equals("0"))
        {
            String pname = resourceService.getParentResourctByPernetId(resource.getPid()).getResourcename();
            request.setAttribute("pname", pname);
        }

        //将用户查看"资源管理"列表中"xxx资源信息"保存到操作日志

        return "system/resource/resource-info";
    }

    @RequestMapping("input")
    public String resourceinput(String id,HttpServletRequest request)
    {
        tb_resource resource = null;
        if(id!=null && !id.equals(""))
        {
            resource = resourceService.infoResource(id);
            if(resource.getPid()!=null && !resource.getPid().equals("") && !resource.getPid().equals("0"))
            {
                String pname = resourceService.getParentResourctByPernetId(resource.getPid()).getResourcename();
                request.setAttribute("pname", pname);
            }

        }
        request.setAttribute("resource", resource);

        return "system/resource/resource-input";
    }

    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(tb_resource resource,String pname)
    {

        Gson gson = new Gson();
        if(pname!=null && !pname.equals(""))
        {
            tb_resource parentResource = resourceService.getParentResourctByPernetName(pname);
            if(parentResource==null)
            {
                return gson.toJson("上级资源名称不存在！");
            }
            resource.setPid(parentResource.getId());
        }

        //检查资源名称的唯一性
        int result = resourceService.insetResource(resource);
        return gson.toJson(""+result);
    }

    @RequestMapping(value="delete" )
    @ResponseBody
    public String delete(String id[])
    {
        //将删除"xxx资源"保存到操作日志
        StringBuilder sb=new StringBuilder();
        for(String str:id)
        {
            tb_resource resource=resourceService.infoResource(str);
            sb.append(resource.getResourcename()+",");
        }

        resourceService.deleteResource(id);
        Gson gson = new Gson();

        return gson.toJson("");
    }

    @RequestMapping(value="tree" )
    public String tree(HttpServletRequest request)
    {
        Gson gson = new Gson();
        request.setAttribute("tree", gson.toJson(resourceService.getZtreeNodeList()));
        return "system/resource/resource-tree";
    }


}
