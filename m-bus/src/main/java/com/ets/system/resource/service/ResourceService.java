package com.ets.system.resource.service;

import com.ets.system.resource.dao.ResourceDao;
import com.ets.system.resource.entity.ZtreeNode;
import com.ets.system.resource.entity.tb_resource;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 14:09
 */
@Service
@Transactional
public class ResourceService {


    @Resource
    ResourceDao resourceDao;

    public List<tb_resource> getResources(Map map)
    {
        return resourceDao.getResources(map);
    }
    //判断新增还是编辑，并对资源名称进行唯一性判断
    public int insetResource(tb_resource resource)
    {
        long num = resourceDao.findResoure(resource.getResourcename());
        int result=1;

        if(resource.getId()!=null && !resource.getId().equals(""))
        {
            resourceDao.updateResource(resource);
            return result=-1;
        }
        else if (resourceDao.findResoure(resource.getResourcename()) == 0) {
            resource.setId(UUIDUtils.getUUID());
            resource.setCtime(DateTimeUtils.getnowdate());
            resourceDao.addResource(resource);
            return result=0;

        }
        return result;
    }

    public tb_resource infoResource(String id)
    {
        return resourceDao.infoResource(id);
    }

    public long getCount()
    {
        return resourceDao.getCount();
    }

    public void deleteResource(String id[])
    {
        resourceDao.deleteResource(id);
    }

    public tb_resource getParentResourctByPernetName(String pname)
    {
        return resourceDao.getParentResourctByPernetName(pname);
    }

    public tb_resource getParentResourctByPernetId(String pid)
    {
        return resourceDao.getParentResourctByPernetId(pid);
    }

    public List<tb_resource> getAllResource()
    {
        return resourceDao.getAllResource();
    }

    public List<tb_resource> getResourctByPernetId(String pid)
    {
        return resourceDao.getResourctByPernetId(pid);
    }

    public List<tb_resource> getRootResource()
    {
        return resourceDao.getRootResource();
    }

    public List<ZtreeNode> getZtreeNodeList()
    {
        List<ZtreeNode> list = new ArrayList<ZtreeNode>();
        List<tb_resource> resourceList = resourceDao.getAllResource();
        for(int i=0 ; i<resourceList.size() ; i++)
        {
            ZtreeNode node = new ZtreeNode();
            tb_resource resource = resourceList.get(i);

            node.setId(resource.getId());
            node.setpId(resource.getPid());
            node.setName(resource.getResourcename());
            node.setOpen(true);
            node.setChecked(false);
            list.add(node);
        }
        return list;
    }

    public List<tb_resource> getAllResourceUrl()
    {
        return resourceDao.getAllResourceUrl();
    }
}
