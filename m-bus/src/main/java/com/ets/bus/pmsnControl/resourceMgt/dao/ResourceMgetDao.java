package com.ets.bus.pmsnControl.resourceMgt.dao;


import com.ets.bus.pmsnControl.resourceMgt.entity.mb_resource;

import java.util.List;
import java.util.Map;

public interface ResourceMgetDao {

	public List<mb_resource> getResources(Map map);
	public long getCount();
	public void addResource(mb_resource entity);
	public void deleteResource(String id[]);
	public void updateResource(mb_resource entity);
	public mb_resource infoResource(String id);

	public List<mb_resource> getParentResourctByPernetName(String pname);
	public mb_resource getParentResourctByPernetId(String pid);
	public List<mb_resource> getAllResource();
	public List<mb_resource> getResourctByPernetId(String pid);
	public List<mb_resource> getRootResource();
	public List<mb_resource> getAllResourceUrl();
	//检查资源名称的个数
	public long getResoureCount(String resoureName);

}
