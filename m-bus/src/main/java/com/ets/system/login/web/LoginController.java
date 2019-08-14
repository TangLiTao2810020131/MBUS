package com.ets.system.login.web;

import com.ets.bus.systemMgt.loginLogMgt.entity.mb_login_log;
import com.ets.bus.systemMgt.loginLogMgt.service.LoginLogMgtService;
import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.pmsnControl.workerMgt.service.WorkerMgtService;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.Message;
import com.ets.utils.ShiroUtils;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@Controller
@RequestMapping("login")
public class LoginController {

    @Resource
    WorkerMgtService workerMgtService;
    @Autowired
    LoginLogMgtService loginLogService;

    @RequestMapping(value = "getVerify", method = RequestMethod.GET)
    @ResponseBody
    public void getVerify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("login")
    public String login()
    {
        SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
        return "login";
    }

    @RequestMapping("logOut")
    public String logOut()
    {
        SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
        return "redirect:/login/login.action";
    }

    /**
     * 登录验证
     * @param request 请求
     * @param worker 用户信息
     * @return  验证信息
     */
    @RequestMapping(value="loginCheck" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String loginCheck(HttpServletRequest request, mb_worker worker,String inputStr) {
        //从session中获取随机数
        /*String random = (String) session.getAttribute("RANDOMVALIDATECODEKE");*/
        String random = (String) request.getSession().getAttribute("randomcode_key");
        Gson gson = new Gson();
        SecurityUtils.getSubject().logout();
        Subject subject = SecurityUtils.getSubject();
        //转换一下password的格式
        String password = new SimpleHash("MD5", worker.getPassword(), "1024").toHex();
        mb_worker dbworker = workerMgtService.getWorkerByWorkerAccount(worker.getWorkerAccount());
        /*if(random.equals(inputStr)){*/
        if (dbworker != null) {
            if (dbworker.getIsOpen().equals("0")) {
                if (password.equals(dbworker.getPassword())) {
                    if (!subject.isAuthenticated()) {
                        UsernamePasswordToken token = new UsernamePasswordToken(dbworker.getWorkerAccount(), dbworker.getPassword());
                        try {
                            // 执行登录.
                            subject.login(token);
                            SecurityUtils.getSubject().getSession().setAttribute("workerSession", dbworker);
                            mb_login_log loginLog = new mb_login_log();
                            loginLog.setWorkerAccount(dbworker.getWorkerAccount());
                            loginLog.setWorkerName(dbworker.getWorkerName());
                            loginLog.setId(UUIDUtils.getUUID());
                            loginLog.setLoginTime(DateTimeUtils.getnowdate());
                            loginLog.setLoginState("登录成功");
                            loginLogService.addLog(request, loginLog);
                            return gson.toJson(new Message("0", "登录成功！"));

                        }
                        // 所有认证时异常的父类.
                        catch (Exception ae) {
                            ae.printStackTrace();
                            return gson.toJson(new Message("1", "系统异常，请稍后重试！"));
                        }
                    }
                } else {
                    return gson.toJson(new Message("2", "密码错误！"));
                }
            } else {
                return gson.toJson(new Message("4", "该账号被禁用，请联系管理员！"));
            }

        } else {
            return gson.toJson(new Message("3", "用户不存在！"));
        }
   /* }
        else{
            return gson.toJson(new Message("5", "验证码输入不正确！"));
    }*/
        return null;
    }

    /**
     * 跳转到首页
     * @return 首页或登录页
     */
    @RequestMapping("loginSuccess")
    public String loginSuccess()
    {
        try {
            Subject subject = SecurityUtils.getSubject();
            mb_worker worker = (mb_worker) subject.getSession().getAttribute("workerSession");
            if(worker != null){
                return "index";
            }else{
                return "redirect:/login/login.action";
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value="11" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String userLogin(String workerAccount,String password,String inputStr){
        Gson gson = new Gson();
        //String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        String kaptcha = ShiroUtils.getKaptcha("KAPTCHA_SESSION_KEY");
//        if(!inputStr.equalsIgnoreCase(kaptcha)){
//            return "验证码不正确";
//        }

        try{
            Subject subject = SecurityUtils.getSubject();
            String md5password = new SimpleHash("MD5",password , "1024").toHex();
            UsernamePasswordToken token = new UsernamePasswordToken(workerAccount, md5password);
            subject.login(token);
            return gson.toJson(new Message("0", "登录成功！"));
        }catch (UnknownAccountException e) {
            return gson.toJson(new Message("1", "系统异常，请稍后重试！"));
        }catch (IncorrectCredentialsException e) {
            return gson.toJson(new Message("2", "用户名或密码错误！"));
        }catch (LockedAccountException e) {
            return gson.toJson(new Message("4", "该账号被禁用，请联系管理员！"));
        }catch (AuthenticationException e) {
            return gson.toJson(new Message("3", "用户不存在！"));
        }

    }

    
    @RequestMapping("main")
    public String list(HttpServletRequest request)
    {
        return "main";
    }
    
}
