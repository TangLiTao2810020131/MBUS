package com.ets.system.user.entity;

/**
 * @author 吴浩
 * @create 2019-01-08 11:28
 */
public class tb_user {

    /**
     *
     */

    private String id;
    private String username;
    private String password;
    private String realname;
    private String isclose;
    private String ctime;
    private String email;
    private String tel;

    public tb_user() {
        super();
    }

    public tb_user(String username, String password, String isclose, String ctime, String email, String tel) {
        super();
        this.username = username;
        this.password = password;
        this.isclose = isclose;
        this.ctime = ctime;
        this.email = email;
        this.tel = tel;
    }

    public tb_user(String id, String username, String password, String isclose, String ctime, String email,
                   String tel) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.isclose = isclose;
        this.ctime = ctime;
        this.email = email;
        this.tel = tel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIsclose() {
        return isclose;
    }

    public void setIsclose(String isclose) {
        this.isclose = isclose;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


}
