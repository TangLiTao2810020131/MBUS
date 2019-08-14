package com.ets.system.user.service;

import com.ets.utils.DateTimeUtils;
import com.ets.utils.UUIDUtils;
import com.ets.system.user.dao.UserDao;
import com.ets.system.user.entity.tb_user;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 11:35
 */
@Service
public class UserService {

    private static Logger logger = Logger.getLogger(UserService.class);

    @Resource
    UserDao userDao;

    public List<tb_user> getUsers(Map map)
    {
        return userDao.getUsers(map);
    }

    public void addUser(tb_user user)
    {
        userDao.addUser(user);
    }

    public void deleteUser(String id[])
    {
        StringBuilder sb=new StringBuilder();
        for (String str : id) {
            tb_user user = infoUser(str);
            sb.append(user.getUsername()+",");
        }
        userDao.deleteUser(id);
    }
    public void updateUser(tb_user user)
    {
        userDao.updateUser(user);
    }
    //新增时进行加密的操作
    public void insetUser(tb_user user)
    {
        if (user.getId()!=null && !user.getId().equals("")){
            userDao.updateUser(user);
        }else {
        	  user.setId(UUIDUtils.getUUID());
              user.setCtime(DateTimeUtils.getnewsdate());
              userDao.addUser(user);
        }
    }
    public tb_user infoUser(String id)
    {
        return userDao.infoUser(id);
    }

    public long getCount(Map map)
    {
        return userDao.getCount(map);
    }

    public void closeUser(String id[])
    {
        for (String str : id) {
            tb_user user = infoUser(str);
        }
        userDao.closeUser(id);
    }
    public void openUser(String id[])
    {
        for (String str : id) {
            tb_user user = infoUser(str);
        }
        userDao.openUser(id);
    }
    public tb_user checkUser(Map<String,String> map)
    {
        return userDao.checkUser(map);
    }
    //检查用户名是否存在
    public int isCheckUserName(String username) {
        int num = 0;
        num = userDao.isCheckUser(username);
        return num;
    }
    //根据用户名查询用户
    public tb_user getUserByUserName(String username){
        tb_user user = userDao.getUserByUserName(username);
        return user;
    }
    //修改密码
    public void updatePassword(Map<String,String> map){
        userDao.updatePassword(map);
    }
    //对应的是index头部信息的用户编辑
    public void editUser(tb_user user){
        userDao.editUser(user);
    }
    //检查输入原密码是否正确
    public tb_user pwdCheck(String id){
        tb_user user = userDao.pwdCheck(id);
        return  user;
    }
    //重置密码
    public void restPassword(String id[])
    {
        StringBuilder sb=new StringBuilder();
        for (String str : id) {
            tb_user user = infoUser(str);
            sb.append(user.getUsername()+",");
        }
        userDao.restPassword(id);

    }

}
