package com.ets.system.role.web;

import com.ets.system.authority.entity.tb_authority;
import com.ets.system.authority.service.AuthorityService;
import com.ets.system.role.entity.tb_role;
import com.ets.system.role.service.RoleService;
import com.ets.system.role_authority.entity.tb_role_authority;
import com.ets.system.role_authority.service.RoleAuthorityService;
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
 * @create 2019-01-08 14:32
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @Resource
    RedisClientTemplate redisClientTemplate;
    @Resource
    RoleService roleService;
    @Resource
    AuthorityService authorityService;
    @Resource
    RoleAuthorityService raService;


    @RequestMapping("list")
    public String list(HttpServletRequest request)
    {
        return "system/role/role-list";
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

        List<tb_role> role = roleService.getRoles(map);
        long count = roleService.getCount();


        PageListData<tb_role> pageData = new PageListData<tb_role>();

        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(role);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }

    @RequestMapping("info")
    public String roleinfo(String id,HttpServletRequest request)
    {
        tb_role role = roleService.infoRole(id);

        List<String> authorityNames = raService.getAuthorityNameByRoleId(id);

        request.setAttribute("role", role);
        request.setAttribute("authorityNames", authorityNames);

        return "system/role/role-info";
    }

    @RequestMapping("input")
    public String aroleinput(String id,HttpServletRequest request)
    {
        tb_role role = null;
        if(id!=null && !id.equals(""))
        {
            role = roleService.infoRole(id);
        }
        request.setAttribute("role", role);

        return "system/role/role-input";
    }

    @RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(tb_role role)
    {

        Gson gson = new Gson();
        //检查角色名的唯一性
        int result = roleService.insetRole(role);
        return gson.toJson(""+result);
    }

    @RequestMapping(value="delete" )
    @ResponseBody
    public String delete(String id[])
    {

        roleService.deleteRole(id);
        Gson gson = new Gson();

        return gson.toJson("");
    }

    @RequestMapping(value="inAuthority" )
    public String inAuthority(String id,HttpServletRequest request)
    {
        List<tb_authority> authorityList = authorityService.getAllAuthority();

        List<tb_role_authority> roleAuthorityList = raService.getAuthorityByRoleId(id);


        request.setAttribute("list", authorityList);
        request.setAttribute("roleAuthorityList", roleAuthorityList);
        request.setAttribute("roleId", id);

        return "system/role/role-inputAuthority";
    }

    @RequestMapping(value="saveAuthority" )
    @ResponseBody
    public String saveAuthority(String [] ids, String roleId)
    {
        raService.deleteAythorityByRoleId(roleId);

        for (String id : ids) {
            tb_role_authority roleAuthority = new tb_role_authority();
            roleAuthority.setAuthorityId(id);
            roleAuthority.setRoleId(roleId);
            raService.save(roleAuthority);
        }

        redisClientTemplate.delSession();

        Gson gson = new Gson();
        return gson.toJson("操作成功");
    }

}
