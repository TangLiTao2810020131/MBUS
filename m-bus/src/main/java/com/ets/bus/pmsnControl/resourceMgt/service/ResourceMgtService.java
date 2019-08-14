package com.ets.bus.pmsnControl.resourceMgt.service;


import com.ets.bus.pmsnControl.resourceMgt.dao.ResourceMgetDao;
import com.ets.bus.pmsnControl.resourceMgt.entity.ZtreeNode;
import com.ets.bus.pmsnControl.resourceMgt.entity.mb_resource;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ResourceMgtService {

    @Resource
    ResourceMgetDao resourceMgetDao;
    @Resource
    OperationLogService operationLogService;

    public List<mb_resource> getResources(Map map) {
        return resourceMgetDao.getResources(map);
    }

    public long getCount() {
        return resourceMgetDao.getCount();
    }

    public void insetResource(mb_resource resource) {
        if (resource.getId() != null && !resource.getId().equals("")) {
            resourceMgetDao.updateResource(resource);
            //添加资源编辑日志
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName("权限控制-资源管理");
            operationLog.setOperaContent("编辑"+"["+resource.getResourcename()+"]"+"资源信息");
            operationLogService.addLog(operationLog);

        } else {
            resource.setId(UUIDUtils.getUUID());
            resource.setCtime(DateTimeUtils.getnowdate());
            resourceMgetDao.addResource(resource);  //添加资源编辑日志
            mb_operation_log operationLog=new mb_operation_log();
            operationLog.setModuleName("权限控制-资源管理");
            operationLog.setOperaContent("新增"+"["+resource.getResourcename()+"]"+"资源信息");
            operationLogService.addLog(operationLog);



        }
    }

    public mb_resource infoResource(String id) {
        return resourceMgetDao.infoResource(id);
    }


    public void deleteResource(String id[]) {
        resourceMgetDao.deleteResource(id);
    }

    public List<mb_resource> getParentResourctByPernetName(String pname) {
        return resourceMgetDao.getParentResourctByPernetName(pname);
    }

    public mb_resource getParentResourctByPernetId(String pid) {
        return resourceMgetDao.getParentResourctByPernetId(pid);
    }

    public List<mb_resource> getAllResource() {
        return resourceMgetDao.getAllResource();
    }

    public List<mb_resource> getResourctByPernetId(String pid) {
        return resourceMgetDao.getResourctByPernetId(pid);
    }

    public List<mb_resource> getRootResource() {
        return resourceMgetDao.getRootResource();
    }

    public List<ZtreeNode> getZtreeNodeList() {
        List<ZtreeNode> list = new ArrayList<ZtreeNode>();
        List<mb_resource> resourceList = resourceMgetDao.getAllResource();
        for (int i = 0; i < resourceList.size(); i++) {
            ZtreeNode node = new ZtreeNode();
            mb_resource resource = resourceList.get(i);

            node.setId(resource.getId());
            node.setpId(resource.getPid());
            node.setName(resource.getResourcename());
            node.setOpen(true);
            node.setChecked(false);
            list.add(node);
        }
        return list;
    }

    public List<mb_resource> getAllResourceUrl() {
        return resourceMgetDao.getAllResourceUrl();
    }


    public int getResoureCount(mb_resource resource) {
        return (int) resourceMgetDao.getResoureCount(resource.getResourcename());
    }
	/*
	 * 通过递归的方式拿到tree结构
	public List<ResourceTree> getResourceTreeList(List<tb_resource> list)
	{
		List<ResourceTree> tree = new ArrayList<ResourceTree>();
		for(int i=0 ; i<list.size() ; i++)
		{
			tb_resource resource = list.get(i);
			ResourceTree resourceTree = new ResourceTree();
			
			resourceTree.setId(resource.getId());
			resourceTree.setPid(resource.getPid());
			resourceTree.setName(resource.getResourcename());
			resourceTree.setUrl(resource.getResourceurl());
			
			resourceTree = getResourceTree(resourceTree);
			tree.add(resourceTree);
		}
		
		return tree;
	}
	
	
	public ResourceTree getResourceTree(ResourceTree resourceTree)
	{
		List<tb_resource> childrenList = getResourctByPernetId(resourceTree.getId());
		if(childrenList!=null && childrenList.size()>0)
		{
			List<ResourceTree> tempList = new ArrayList<ResourceTree>();
			
			for(int i=0 ; i<childrenList.size() ; i++)
			{
				ResourceTree tempNode = new ResourceTree();
				tb_resource resource = childrenList.get(i);
				
				tempNode.setId(resource.getId());
				tempNode.setPid(resource.getPid());
				tempNode.setName(resource.getResourcename());
				tempNode.setUrl(resource.getResourceurl());
				
				tempList.add(tempNode);
				
				getResourceTree(tempNode);
			}
			resourceTree.setChildren(tempList);
		}
		return resourceTree;
	}
	*/

}
