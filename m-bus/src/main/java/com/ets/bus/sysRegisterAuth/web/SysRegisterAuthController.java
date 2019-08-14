package com.ets.bus.sysRegisterAuth.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("sysregisterauth")
public class SysRegisterAuthController {
	
    @RequestMapping("authBootPage")
    public String authBootPage(HttpServletRequest request)
    {

        return "bus/sys-register-auth/auth-bootPage";
    }

}
