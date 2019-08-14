package com.ets.bus.pmsnControl.authorityMgt.web;

import com.ets.bus.pmsnControl.authorityMgt.entity.mb_authority;
import com.ets.bus.pmsnControl.authorityMgt.service.AuthorityMgtService;
import com.ets.bus.pmsnControl.authorityMgt_Resoure.entity.mb_authority_resource;
import com.ets.bus.pmsnControl.authorityMgt_Resoure.service.AuthorityMgtResourceService;
import com.ets.bus.pmsnControl.resourceMgt.entity.ZtreeNode;
import com.ets.bus.pmsnControl.resourceMgt.entity.mb_resource;
import com.ets.bus.pmsnControl.resourceMgt.service.ResourceMgtService;
import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.system.shiro.cache.RedisClientTemplate;
import com.ets.utils.PageListData;
import com.google.gson.Gson;
import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("authorityMgt")
public class AuthorityMgtController {

    @Resource
    AuthorityMgtResourceService authorityMgtResourceService;
    @Resource
    RedisClientTemplate redisClientTemplate;
    @Resource
    AuthorityMgtService authorityMgtService;
    @Resource
    ResourceMgtService resourceMgtService;
    @Resource
    OperationLogService operationLogService;

    String baseUrl = "bus/pmsn-control/authority/";
    String moduleName="权限控制-权限管理";
    /**
     * 获取权限信息的列表
     * @param request
     * @return
     */
    @RequestMapping("list")
    public String list(HttpServletRequest request) {
        //添加查看权限列表的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看权限列表");
        operationLogService.addLog(operationLog);
        return baseUrl + "authority-list";
    }
    /**
     * 获取权限信息的列表数据
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "listData", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String listData(int page, int limit) {
        //System.out.println("page="+page+",limit="+limit);
        Map<String, Object> map = new HashMap<String, Object>();
        //		map.put("page", (page-1)*limit);//mysql
        //		map.put("limit", limit);//mysql
        map.put("page", (page) * limit);//oracle
        map.put("limit", (page - 1) * limit);//oracle

        List<mb_authority> authority = authorityMgtService.getAuthoritys(map);
        long count = authorityMgtService.getCount();


        PageListData<mb_authority> pageData = new PageListData<mb_authority>();

        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(authority);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }
    /**
     * 获取权限信息的详细信息（包括权限所分配的资源树）
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("info")
    public String authorityinfo(String id, HttpServletRequest request) {
        List<mb_authority_resource> arList = authorityMgtResourceService.getResourceByAuthorityId(id);
        if (arList != null && arList.size() > 0) {
            Gson gson = new Gson();
            List<ZtreeNode> resourceList =resourceMgtService.getZtreeNodeList();
            resourceList = authorityMgtResourceService.checkTreeNode(resourceList, arList);
            String tree = gson.toJson(resourceList);
            request.setAttribute("tree", tree);
        }
        mb_authority authority = authorityMgtService.infoAuthority(id);
        //添加查看权限详细信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看"+"["+authority.getAuthorityname()+"]"+"权限详细信息");
        operationLogService.addLog(operationLog);
        request.setAttribute("authority", authority);


        return baseUrl + "authority-info";
    }
    /**
     * 跳转新增权限或者编辑权限信息的页面（判断id 是否为空 为空则是新增页面 反之 则为编辑页面）
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("input")
    public String authorityinput(String id, HttpServletRequest request) {
        mb_authority authority = null;
        if (id != null && !id.equals("")) {
            authority = authorityMgtService.infoAuthority(id);
        }
        request.setAttribute("authority", authority);

        return baseUrl + "authority-input";
    }
    /**
     * 新增权限或者编辑权限信息的页面（判断id 是否为空 为空则是新增反之 则为编辑）
     * @param authority
     * @return
     */
    @RequestMapping(value = "save", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(mb_authority authority) {
        Gson gson = new Gson();
        authorityMgtService.insetResource(authority);
        return gson.toJson("操作成功");

    }
    /**
     * 批量删除权限信息
     * @param id
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(String id[]) {
        authorityMgtService.deleteAuthority(id);
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < id.length; i++) {
            mb_authority authority = authorityMgtService.infoAuthority(id[i]);
            sb.append(authority.getAuthorityname()+',');
            authorityMgtResourceService.deleteResourceByAuthorityId(id[i]);

        }
        //添加批量删除权限的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]权限");
        operationLogService.addLog(operationLog);
        Gson gson = new Gson();

        return gson.toJson("");
    }
    /**
     * 跳转为权限分配资源的页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "inResource")
    public String inResource(HttpServletRequest request, String id) {
        Gson gson = new Gson();

       List<ZtreeNode> resourceList = resourceMgtService.getZtreeNodeList();

        List<mb_authority_resource> arList = authorityMgtResourceService.getResourceByAuthorityId(id);
        if (arList != null && arList.size() > 0) {
            resourceList =authorityMgtResourceService.checkTreeNode(resourceList, arList);
        }


        String tree = gson.toJson(resourceList);
        request.setAttribute("tree", tree);
        request.setAttribute("aid", id);
        return baseUrl+"authority-inputResource";
    }
    /**
     * 保存为权限分配资源
     * @param ids
     * @param aid
     * @return
     */
    @RequestMapping(value = "saveResource", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String saveResource(String[] ids, String aid) {
        //System.out.println(ids);
        authorityMgtResourceService.deleteResourceByAuthorityId(aid);
        StringBuilder sb=new StringBuilder();
        for (String id : ids) {
            mb_authority_resource ar = new mb_authority_resource();
            ar.setAuthorityId(aid);
            ar.setResourceId(id);
            authorityMgtResourceService.save(ar);
            mb_resource resource = resourceMgtService.infoResource(id);
            sb.append(resource.getResourcename()+",");
        }
        Gson gson = new Gson();
        mb_authority authority = authorityMgtService.infoAuthority(aid);
        //添加分配资源的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("分配["+authority.getAuthorityname()+"权限]["+sb.substring(0,sb.length()-1)+"]的资源");
        redisClientTemplate.delSession();

        return gson.toJson("操作成功");
    }
    /**
     * 检验权限的名称的唯一性
     * @param authority
     * @return
     */
    @RequestMapping(value = "isCheckAuthorityname", produces = "application/json; charset=utf-8")
    @ResponseBody
    public int isCheckAuthorityname(mb_authority authority){
        return authorityMgtService.getAuthorityCount(authority);
    }
}
