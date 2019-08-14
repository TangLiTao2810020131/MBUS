package com.ets.bus.pmsnControl.roleMgt.web;

import com.ets.bus.pmsnControl.authorityMgt.entity.mb_authority;
import com.ets.bus.pmsnControl.authorityMgt.service.AuthorityMgtService;
import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;
import com.ets.bus.pmsnControl.roleMgt.service.RoleMgtService;
import com.ets.bus.pmsnControl.roleMgt_Authority.entity.mb_role_authority;
import com.ets.bus.pmsnControl.roleMgt_Authority.service.RoleMgtAuthorityService;
import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.system.shiro.cache.RedisClientTemplate;
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
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("rolemgt")
public class RoleMgtController {
    @Resource
    RoleMgtService roleMgtService;
    @Resource
    RoleMgtAuthorityService roleMgtAuthorityService;
     @Resource
    AuthorityMgtService authorityMgtService;
    @Resource
    RedisClientTemplate redisClientTemplate;
    @Resource
    OperationLogService operationLogService;


    String baseUrl="bus/pmsn-control/role-mgt/";
    String moduleName="权限控制-角色管理";
    /**
     * 获取角色信息的列表
     * @param request
     * @return
     */
    @RequestMapping("roleMgt")
    public String roleMgt(HttpServletRequest request)
    {
        //添加查看角色列表的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看角色列表");
        operationLogService.addLog(operationLog);
        return baseUrl+"role-mgt";
    }
    /**
     * 获取角色信息的列表数据
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

        List<mb_role> role = roleMgtService.getRoles(map);
        long count = roleMgtService.getCount();


        PageListData<mb_role> pageData = new PageListData<mb_role>();

        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(role);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }
    /**
     * 查看角色的详细信息
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("info")
    public String roleinfo(String id,HttpServletRequest request)
    {
        mb_role role = roleMgtService.infoRole(id);
       List<String> authorityNames = roleMgtAuthorityService.getAuthorityNameByRoleId(id);
        request.setAttribute("role", role);
        request.setAttribute("authorityNames", authorityNames);
        //添加查看角色详细信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看"+"["+role.getRolename()+"]"+"角色详细信息");
        operationLogService.addLog(operationLog);

        return baseUrl+"role-info";
    }
    /**
     * 跳转新增角色或者编辑角色信息的页面（判断id 是否为空 为空则是新增页面 反之 则为编辑页面）
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("input")
    public String aroleinput(String id,HttpServletRequest request)
    {
        mb_role role = null;
        if(id!=null && !id.equals(""))
        {
            role = roleMgtService.infoRole(id);
        }
        request.setAttribute("role", role);

        return baseUrl+"role-input";
    }
    /**
     * 保存或者编辑角色信息（判断id 是否为空 为空则是新增 反之 则为编辑））
     * @param role
     * @return
     */
    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(mb_role role)
    {
        roleMgtService.insetRole(role);
        Gson gson=new Gson();
        return gson.toJson("操作成功!");
    }
    /**
     * 批量删除角色信息
     * @param id
     * @return
     */
    @RequestMapping(value="delete" ,produces = "application/json; charset=utf-8" )
    @ResponseBody
    public String delete(String[] id)
    {
        StringBuilder sb=new StringBuilder();
        for(String str:id){
           mb_role role =roleMgtService.infoRole(str);
            sb.append(role.getRolename()+',');
        }
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]角色");
        operationLogService.addLog(operationLog);
        roleMgtService.deleteRole(id);
        Gson gson = new Gson();
        return gson.toJson("");
    }
    /**
     * 跳转分配权限的页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="inAuthority" )
    public String inAuthority(String id,HttpServletRequest request)
    {
     List<mb_authority> authorityList = authorityMgtService.getAllAuthority();

        List<mb_role_authority> roleAuthorityList =roleMgtAuthorityService.getAuthorityByRoleId(id);
        request.setAttribute("list", authorityList);
        request.setAttribute("roleAuthorityList", roleAuthorityList);
        request.setAttribute("roleId", id);

        return baseUrl+"role-inputAuthority";
    }
    /**
     * 保存分配的权限
     * @param ids
     * @param roleId
     * @return
     */
    @RequestMapping(value="saveAuthority",produces = "application/json; charset=utf-8" )
    @ResponseBody
    public String saveAuthority(String [] ids, String roleId)
    {
       roleMgtAuthorityService.deleteAythorityByRoleId(roleId);
       //添加分配权限的操作日志
       StringBuilder sb=new StringBuilder();
        for (String id : ids) {
            mb_role_authority roleAuthority = new mb_role_authority();
            roleAuthority.setAuthorityId(id);
            roleAuthority.setRoleId(roleId);
            roleMgtAuthorityService.save(roleAuthority);
            mb_authority authority = authorityMgtService.infoAuthority(id);
            sb.append(authority.getAuthorityname()+",");

        }
        mb_role role = roleMgtService.infoRole(roleId);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("分配["+role.getRolename()+"角色]["+sb.substring(0,sb.length()-1)+"]的权限");
        operationLogService.addLog(operationLog);
        redisClientTemplate.delSession();

        Gson gson = new Gson();
        return gson.toJson("");
    }
    /**
     * 检验角色名的唯一性
     * @param role
     * @return
     */
    @RequestMapping(value = "isCheckRolename",produces = "application/json; charset=utf-8")
    @ResponseBody
    public int isCheckRolename(mb_role role){
        return (int) roleMgtService.getCountRoleName(role.getRolename());
    }

}
