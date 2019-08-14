package com.ets.bus.pmsnControl.roleAuz.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("roleauz")
public class RoleAuzController {
	
    @RequestMapping("roleAuz")
    public String roleAuz(HttpServletRequest request)
    {

        return "bus/pmsn-control/role-auz/role-auz";
    }

}
