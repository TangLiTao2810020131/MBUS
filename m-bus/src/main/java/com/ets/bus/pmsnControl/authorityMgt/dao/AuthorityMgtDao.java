package com.ets.bus.pmsnControl.authorityMgt.dao;

import com.ets.bus.pmsnControl.authorityMgt.entity.mb_authority;

import java.util.List;
import java.util.Map;

public interface AuthorityMgtDao {

	public List<mb_authority> getAuthoritys(Map map);
	public void addAuthority(mb_authority entity);
	public void deleteAuthority(String id[]);
	public void updateAuthority(mb_authority entity);
	public mb_authority infoAuthority(String id);
	public long getCount();
	public List<mb_authority> getAllAuthority();
	//查看权限名的个数
	public long getAuthorityCount(String authorityname);


}
