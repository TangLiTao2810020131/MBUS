package com.ets.bus.pmsnControl.buildingMgt.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("buildingmgt")
public class BuildingMgtController {
    String baseUrl="bus/pmsn-control/building-mgt/";
	
    @RequestMapping("buildingMgt")
    public String buildingMgt(HttpServletRequest request)
    {

        return baseUrl+"building-mgt";
    }

}
