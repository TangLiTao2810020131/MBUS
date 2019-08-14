package com.ets.system.shiro;


import com.ets.bus.pmsnControl.authorityMgt.service.AuthorityMgtService;
import com.ets.bus.pmsnControl.authorityMgt_Resoure.service.AuthorityMgtResourceService;
import com.ets.bus.pmsnControl.resourceMgt.entity.mb_resource;
import com.ets.bus.pmsnControl.resourceMgt.service.ResourceMgtService;
import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;
import com.ets.bus.pmsnControl.roleMgt.service.RoleMgtService;
import com.ets.bus.pmsnControl.roleMgt_Authority.entity.mb_role_authority;
import com.ets.bus.pmsnControl.roleMgt_Authority.service.RoleMgtAuthorityService;
import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.pmsnControl.workerMgt.service.WorkerMgtService;
import com.ets.bus.pmsnControl.worker_Role.service.WorkerRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author Windows User
 *
 */

public class ShiroRealm extends AuthorizingRealm{

	@Autowired
	public ResourceMgtService resourceMgtService;

	@Autowired
	public WorkerMgtService workerMgtService;
	@Resource
	@Autowired
	public RoleMgtService roleMgtService;

	@Autowired
	public WorkerRoleService workerRoleService;

	@Autowired
	public RoleMgtAuthorityService roleMgtAuthorityService;

	@Autowired
	public AuthorityMgtResourceService authorityMgtResourceService;

	@Autowired
	public AuthorityMgtService authorityMgtService;



	//@Autowired
	//public UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo");

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String username = upToken.getUsername();
		String password = new String(upToken.getPassword());


		Object principal = username;
		Object credentials = password;

		String realmName = getName();
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials , realmName);

	/*	UsernamePasswordToken upToken = (UsernamePasswordToken)token;

		//查询用户信息
		mb_worker user = workerMgtService.getWorkerByWorkerAccount(upToken.getUsername());
		//账号不存在
		if(user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		//账号锁定
		if(!"0".equals(user.getIsOpen())){
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());*/


		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Subject subject = SecurityUtils.getSubject();
		Object obj = subject.getSession().getAttribute("workerSession");
		if(obj instanceof mb_worker)
		{
			addPermission(info,(mb_worker)obj);
		}
		/*else if(obj instanceof Map)
		{
			addCustormetUserRole(info,(Map)obj);
		}*/


		return info;
	}

	public void addPermission(SimpleAuthorizationInfo info, mb_worker worker)
	{
		List<mb_role> roleList = workerRoleService.getRoles(worker.getId());
		if(roleList!=null && roleList.size()>0)
		{
			for(int i=0 ; i<roleList.size() ; i++)
			{
				mb_role role = roleList.get(i);
				List<mb_role_authority> authorityList = roleMgtAuthorityService.getAuthorityByRoleId(role.getId());
				if(authorityList!=null && authorityList.size()>0)
				{
					for(int j=0 ; j<authorityList.size() ; j++)
					{
						mb_role_authority roleAuthority = (mb_role_authority)authorityList.get(j);

						List<mb_resource> resourceList = authorityMgtResourceService.getResources(roleAuthority.getAuthorityId());
						if(resourceList!=null && resourceList.size()>0)
						{
							for(int k=0 ; k<resourceList.size() ; k++)
							{
								mb_resource resource = resourceList.get(k);
								info.addStringPermission(resource.getResourcename());
							}
						}
					}
				}
				info.addRole(role.getRolename());
			}
		}
	}


	/*public void addCustormetUserRole(SimpleAuthorizationInfo info,Map map)
	{
		String str = (String) map.get("TYPE");
		int type = Integer.parseInt(str);
		if(type <=2) info.addRole("超级管理员");
		if(type <=1) info.addRole("管理员");
		if(type <=0) info.addRole("员工");
	}*/
}
