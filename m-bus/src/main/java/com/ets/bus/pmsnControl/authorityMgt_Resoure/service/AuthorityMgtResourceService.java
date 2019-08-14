package com.ets.bus.pmsnControl.authorityMgt_Resoure.service;


import com.ets.bus.pmsnControl.authorityMgt_Resoure.dao.AuthorityMgtResourceDao;
import com.ets.bus.pmsnControl.authorityMgt_Resoure.entity.mb_authority_resource;
import com.ets.bus.pmsnControl.resourceMgt.entity.ZtreeNode;
import com.ets.bus.pmsnControl.resourceMgt.entity.mb_resource;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AuthorityMgtResourceService {

    @Resource
    AuthorityMgtResourceDao authorityMgtResourceDao;

    public List<mb_authority_resource> getResourceByAuthorityId(String id) {
        return authorityMgtResourceDao.getResourceByAuthorityId(id);
    }

    public void save(mb_authority_resource ar) {
        ar.setId(UUIDUtils.getUUID());
        authorityMgtResourceDao.save(ar);
    }

    public void deleteResourceByAuthorityId(String authorityId) {
        authorityMgtResourceDao.deleteResourceByAuthorityId(authorityId);
    }

    public List<ZtreeNode> checkTreeNode(List<ZtreeNode> resourceList, List<mb_authority_resource> arList) {
        for (mb_authority_resource ar : arList) {

            for (ZtreeNode ztreeNode : resourceList) {
                if (ar.getResourceId().equals(ztreeNode.getId())) {
                    ztreeNode.setChecked(true);
                }
            }
        }
        return resourceList;
    }

    public List<mb_resource> getResources(String authorityId) {
        return authorityMgtResourceDao.getResources(authorityId);
    }
}
