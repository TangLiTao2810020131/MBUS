package com.ets.system.log.optionlog.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.system.log.optionlog.entity.tb_option_log;
import com.ets.system.log.optionlog.service.OptionLogService;
import com.ets.utils.PageListData;
import com.google.gson.Gson;

/**
 * @author 吴浩
 * @create 2019-01-08 20:14
 */
@Controller
@RequestMapping("optionlog")
public class OptionLogController {


	@Resource
	OptionLogService logService;

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		//将查看"操作日志列表"信息保存到操作日志
		return "system/log/optionlog/optionlog-list";
	}

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,tb_option_log oprLogin)
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("username", oprLogin.getUsername());
			map.put("oprtime", oprLogin.getOprtime());
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle

			List<tb_option_log> logs = logService.getLogs(map);
			long count = logService.getCount(map);
		
		
		PageListData<tb_option_log> pageData = new PageListData<tb_option_log>();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(logs);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}
	
	@RequestMapping("info")
	public String loginfo(String id,HttpServletRequest request)
	{
		//System.out.println(id);
		tb_option_log loginfo = logService.infoLog(id);
		
		request.setAttribute("loginfo", loginfo);
		return "system/log/optionlog/optionlog-info";
	}

}
