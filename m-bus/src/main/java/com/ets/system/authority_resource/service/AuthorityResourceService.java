package com.ets.system.authority_resource.service;

import com.ets.system.authority_resource.dao.AuthorityResourceDao;
import com.ets.system.authority_resource.entity.tb_authority_resource;
import com.ets.system.resource.entity.ZtreeNode;
import com.ets.system.resource.entity.tb_resource;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 14:17
 */
@Service
@Transactional
public class AuthorityResourceService {

    @Resource
    AuthorityResourceDao arDao;

    public List<tb_authority_resource> getResourceByAuthorityId(String id)
    {
        return arDao.getResourceByAuthorityId(id);
    }

    public void save(tb_authority_resource ar)
    {
        ar.setId(UUIDUtils.getUUID());
        arDao.save(ar);
    }

    public void deleteResourceByAuthorityId(String authorityId)
    {
        arDao.deleteResourceByAuthorityId(authorityId);
    }

    public List<ZtreeNode> checkTreeNode(List<ZtreeNode> resourceList , List<tb_authority_resource> arList)
    {
        for (tb_authority_resource ar : arList) {

            for (ZtreeNode ztreeNode : resourceList) {
                if(ar.getResourceId().equals(ztreeNode.getId()))
                {
                    ztreeNode.setChecked(true);
                }
            }
        }
        return resourceList;
    }

    public List<tb_resource> getResources(String authorityId)
    {
        return arDao.getResources(authorityId);
    }

}
