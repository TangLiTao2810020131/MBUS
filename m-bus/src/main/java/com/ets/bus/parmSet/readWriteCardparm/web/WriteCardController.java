package com.ets.bus.parmSet.readWriteCardparm.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("writecard")
public class WriteCardController {
	
    @RequestMapping("readWriteCardParm")
    public String readWriteCardParm(HttpServletRequest request)
    {

        return "bus/parm-set/read-writeCard-parm/read-writeCard-parm";
    }

}
