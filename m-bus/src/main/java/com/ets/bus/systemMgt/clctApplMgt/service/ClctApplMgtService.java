package com.ets.bus.systemMgt.clctApplMgt.service;

import com.ets.bus.systemMgt.clctApplMgt.dao.ClctApplMgtDao;
import com.ets.bus.systemMgt.clctApplMgt.entity.ClctApplMgt;
import com.ets.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClctApplMgtService
{
    @Autowired
    ClctApplMgtDao clctApplMgtDao;

    public List<ClctApplMgt> getClctApplMgts(Map<String, Object> map)
    {
        return clctApplMgtDao.getClctApplMgts(map);
    }

    public long getCount(Map<String, Object> map)
    {
        return clctApplMgtDao.getCount(map);
    }

    public void addClctApplMgt(ClctApplMgt clctApplMgt)
    {
        clctApplMgt.setId(UUIDUtils.getUUID());
        clctApplMgt.setConcentrator_id("");
        clctApplMgt.setInstruction_number("");
        clctApplMgt.setDel_status("0");
        clctApplMgtDao.addClctApplMgt(clctApplMgt);
    }

    public ClctApplMgt findClctApplMgtById(String id)
    {
        return clctApplMgtDao.findClctApplMgtById(id);
    }

    public void delClctApplMgts(String[] ids)
    {
        clctApplMgtDao.delClctApplMgts(ids);
    }

    public void editClctApplMgt(ClctApplMgt clctApplMgt)
    {
        clctApplMgtDao.editClctApplMgt(clctApplMgt);
    }

    public String findInstruction(String id)
    {
        return clctApplMgtDao.findInstruction(id);
    }

    public List<ClctApplMgt> findClctApplMgts()
    {
        return clctApplMgtDao.findClctApplMgts();
    }

    public ClctApplMgt checkApplicationNumber(String applicationNumber)
    {
        return clctApplMgtDao.checkApplicationNumber(applicationNumber);
    }

    public ClctApplMgt checkApplicationName(String collectName)
    {
        return clctApplMgtDao.checkApplicationName(collectName);
    }

    public void updateClctApplMgtDelStatus(String[] ids)
    {
        clctApplMgtDao.updateClctApplMgtDelStatus(ids);
    }
}