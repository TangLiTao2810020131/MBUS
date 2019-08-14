package com.ets.bus.pmsnControl.authorityMgt_Resoure.dao;

import com.ets.bus.pmsnControl.authorityMgt_Resoure.entity.mb_authority_resource;
import com.ets.bus.pmsnControl.resourceMgt.entity.mb_resource;

import java.util.List;

public interface AuthorityMgtResourceDao {

	public List<mb_authority_resource> getResourceByAuthorityId(String id);
	public void save(mb_authority_resource ar);
	public void deleteResourceByAuthorityId(String authorityId);
	
	public List<mb_resource> getResources(String authorityId);
}
