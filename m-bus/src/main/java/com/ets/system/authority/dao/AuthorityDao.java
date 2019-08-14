package com.ets.system.authority.dao;

import com.ets.system.authority.entity.tb_authority;

import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 11:56
 */
public interface AuthorityDao {


    public List<tb_authority> getAuthoritys(Map map);
    public void addAuthority(tb_authority entity);
    public void deleteAuthority(String id[]);
    public void updateAuthority(tb_authority entity);
    public tb_authority infoAuthority(String id);
    public long getCount();
    public List<tb_authority> getAllAuthority();
    //查看权限名的个数
    public long findAuthority(String authorityname);



}
