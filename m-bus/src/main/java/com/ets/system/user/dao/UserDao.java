package com.ets.system.user.dao;

import com.ets.system.user.entity.tb_user;
import java.util.Map;
import java.util.List;

/**
 * @author 吴浩
 * @create 2019-01-08 11:32
 */
public interface UserDao {


    public List<tb_user> getUsers(Map map);

    public void addUser(tb_user entity);

    public void deleteUser(String id[]);

    public void updateUser(tb_user entity);

    public tb_user infoUser(String id);

    public long getCount(Map map);

    public void closeUser(String id[]);

    public void openUser(String id[]);

    public tb_user checkUser(Map<String,String> map);

    //验证唯一性
    public int isCheckUser(String username);

    //根据用户名得到用户
    public tb_user getUserByUserName(String username);

    //根据id去更新密码
    public void updatePassword(Map<String, String> map);

    //检查输入原密码是否正确
    public tb_user pwdCheck(String id);

    //对应的是index头部信息的用户编辑
    public void editUser(tb_user user);

    //重置密码
    public void restPassword(String id[]);

}
