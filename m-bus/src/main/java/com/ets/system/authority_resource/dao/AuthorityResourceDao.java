package com.ets.system.authority_resource.dao;

import com.ets.system.authority_resource.entity.tb_authority_resource;
import com.ets.system.resource.entity.tb_resource;

import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 14:16
 */
public interface AuthorityResourceDao {

    public List<tb_authority_resource> getResourceByAuthorityId(String id);
    public void save(tb_authority_resource ar);
    public void deleteResourceByAuthorityId(String authorityId);

    public List<tb_resource> getResources(String authorityId);
}
