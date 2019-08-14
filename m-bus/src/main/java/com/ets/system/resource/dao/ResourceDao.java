package com.ets.system.resource.dao;

import com.ets.system.resource.entity.tb_resource;

import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 14:08
 */
public interface ResourceDao {

    public List<tb_resource> getResources(Map map);

    public void addResource(tb_resource entity);

    public void deleteResource(String id[]);

    public void updateResource(tb_resource entity);

    public tb_resource infoResource(String id);

    public long getCount();

    public tb_resource getParentResourctByPernetName(String pname);

    public tb_resource getParentResourctByPernetId(String pid);

    public List<tb_resource> getAllResource();

    public List<tb_resource> getResourctByPernetId(String pid);

    public List<tb_resource> getRootResource();

    public List<tb_resource> getAllResourceUrl();

    //检查资源名称的个数
    public long findResoure(String resoureName);


}
