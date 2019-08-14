package com.ets.system.shiro;

import com.ets.bus.pmsnControl.resourceMgt.entity.mb_resource;
import com.ets.bus.pmsnControl.resourceMgt.service.ResourceMgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 14:34
 */
@Service
public class ShiroUtility {

    @Autowired
    public ResourceMgtService resourceMgtService;


    public LinkedHashMap<String, String> getMap()
    {
        LinkedHashMap<String, String> map = new LinkedHashMap<String,String>();
        //System.out.println("更新全部资源访问权限！");
        map.put("/resources/**", "anon"); //WEB资源文件，不进行验证
        map.put("/login/login.action", "anon"); //登陆，不进行验证
        map.put("/login/getVerify.action", "anon"); //登陆，不进行验证
       /* map.put("/custormerLogin/login.action", "anon"); //登陆，不进行验证
        map.put("/custormerLogin/loginCheck.action", "anon");
        map.put("/custormerLogin/loginSuccess.action", "anon");*/

        map.put("/login/loginCheck.action", "anon"); //登陆，不进行验证
        map.put("/login/loginSuccess.action", "anon"); //登陆跳转，不进行验证
        map.put("/websocket.action", "anon"); //websocket，不进行验证
        map.put("/jsp/business/websocket/**", "anon"); //登陆，不进行验证
        map.put("/test/**", "anon"); //测试，不进行验证



       List<mb_resource> resourceList = resourceMgtService.getAllResourceUrl();
        if(resourceList!=null && resourceList.size()>0)
        {
            for(int i=0 ; i<resourceList.size() ; i++)
            {
                mb_resource resource = (mb_resource)resourceList.get(i);
                map.put(resource.getResourceurl() , "perms["+resource.getResourcename()+"]");
            }
        }
        map.put("/**", "anon");
        return map;
    }

}
