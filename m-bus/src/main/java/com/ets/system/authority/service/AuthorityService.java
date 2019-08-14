package com.ets.system.authority.service;

import com.ets.system.authority.dao.AuthorityDao;
import com.ets.system.authority.entity.tb_authority;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 14:02
 */
@Service
@Transactional
public class AuthorityService {

    @Resource
    AuthorityDao authorityDao;

    public List<tb_authority> getAuthoritys(Map map)
    {
        return authorityDao.getAuthoritys(map);
    }
    //检查权限名称的唯一性并且判断是编辑操作还是新增操作
    public int insetResource(tb_authority authority)
    {
        long isnum = authorityDao.findAuthority(authority.getAuthorityname());
        int result=1;
        if(authority.getId()!=null && !authority.getId().equals(""))
        {
            authorityDao.updateAuthority(authority);
            return result=-1;
        }
        else if (isnum==0) {
            authority.setId(UUIDUtils.getUUID());
            authority.setCtime(DateTimeUtils.getnowdate());
            authorityDao.addAuthority(authority);
            return result=0;
        }
        return result;
    }

    public tb_authority infoAuthority(String id)
    {
        return authorityDao.infoAuthority(id);
    }

    public long getCount()
    {
        return authorityDao.getCount();
    }

    public void deleteAuthority(String id[])
    {
        authorityDao.deleteAuthority(id);
    }

    public List<tb_authority> getAllAuthority()
    {
        return authorityDao.getAllAuthority();
    }
    public long findAuthority(String authorityname){
        return  authorityDao.findAuthority(authorityname);
    }

}
