package com.ets.bus.pmsnControl.authorityMgt.service;

import com.ets.bus.pmsnControl.authorityMgt.dao.AuthorityMgtDao;
import com.ets.bus.pmsnControl.authorityMgt.entity.mb_authority;

import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AuthorityMgtService {

	@Resource
	AuthorityMgtDao authorityMgtDao;
	@Resource
	OperationLogService operationLogService;
	
	public List<mb_authority> getAuthoritys(Map map)
	{
		return authorityMgtDao.getAuthoritys(map);
	}
	//检查权限名称的唯一性并且判断是编辑操作还是新增操作
	public void insetResource(mb_authority authority)
	{
		if(authority.getId()!=null && !authority.getId().equals(""))
		{
			//添加角色编辑日志
			mb_operation_log operationLog=new mb_operation_log();
			operationLog.setModuleName("权限控制-权限管理");
			operationLog.setOperaContent("编辑"+"["+authority.getAuthorityname()+"]"+"权限信息");
			operationLogService.addLog(operationLog);
			authorityMgtDao.updateAuthority(authority);

		}
		else  {
			authority.setId(UUIDUtils.getUUID());
			authority.setCtime(DateTimeUtils.getnowdate());
			authorityMgtDao.addAuthority(authority);
			mb_operation_log operationLog=new mb_operation_log();
			operationLog.setModuleName("权限控制-权限管理");
			operationLog.setOperaContent("新增"+"["+authority.getAuthorityname()+"]"+"权限信息");
			operationLogService.addLog(operationLog);

		}

	}
	
	public mb_authority infoAuthority(String id)
	{
		return authorityMgtDao.infoAuthority(id);
	}
	
	public long getCount()
	{
		return authorityMgtDao.getCount();
	}
	
	public void deleteAuthority(String id[])
	{
		authorityMgtDao.deleteAuthority(id);
	}
	
	public List<mb_authority> getAllAuthority()
	{
		return authorityMgtDao.getAllAuthority();
	}
	public int getAuthorityCount(mb_authority authority){
		return (int) authorityMgtDao.getAuthorityCount(authority.getAuthorityname());
	}
}
