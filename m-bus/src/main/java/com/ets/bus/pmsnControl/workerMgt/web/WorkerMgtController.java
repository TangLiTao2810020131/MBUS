package com.ets.bus.pmsnControl.workerMgt.web;

import com.ets.bus.pmsnControl.roleMgt.entity.mb_role;
import com.ets.bus.pmsnControl.roleMgt.service.RoleMgtService;
import com.ets.bus.pmsnControl.workerMgt.entity.mb_worker;
import com.ets.bus.pmsnControl.workerMgt.service.WorkerMgtService;
import com.ets.bus.pmsnControl.worker_Role.entity.mb_worker_role;
import com.ets.bus.pmsnControl.worker_Role.service.WorkerRoleService;
import com.ets.bus.systemMgt.operationLog.entity.mb_operation_log;
import com.ets.bus.systemMgt.operationLog.service.OperationLogService;
import com.ets.system.shiro.cache.RedisClientTemplate;
import com.ets.utils.DateTimeUtils;
import com.ets.utils.Message;
import com.ets.utils.PageListData;
import com.ets.utils.UUIDUtils;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-01-08 14:04
 */
@Controller
@RequestMapping("workermgt")
public class WorkerMgtController {
    @Autowired
    WorkerMgtService workerMgtService;
    @Autowired
    RoleMgtService roleMgtService;
    @Resource
    WorkerRoleService workerRoleService;
    @Resource
    RedisClientTemplate redisClientTemplate;
    @Resource
    OperationLogService operationLogService;


    String baseUrl="bus/pmsn-control/worker-mgt/";
    String moduleName="权限控制-职员管理";

    /**
     * 获取职员信息的列表
     * @param request
     * @return
     */
    @RequestMapping("workerMgt")
    public String workerMgt(HttpServletRequest request)
    {
       //添加查看职员列表的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看职员列表");
        operationLogService.addLog(operationLog);
        return baseUrl+"worker-mgt";
    }
    /**
     * 获取职员信息的列表数据
     * @param page
     * @param limit
     * @param workerAccount
     * @param workerName
     * @param realName
     * @param identity
     * @param email
     * @param ctime
     * @param isclose
     * @return
     */
    //对职员信息进行分页
    @RequestMapping(value = "listData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listData(int page, int limit, String workerAccount, String workerName,String realName,String identity,String email, String ctime, String isclose) {
        //System.out.println("page="+page+",limit="+limit);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("workerAccount", workerAccount);
        map.put("workerName", workerName);
        map.put("realName", realName);
        map.put("identity", identity);
        map.put("email", email);
        map.put("ctime", ctime);
        map.put("isclose", isclose);
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
        map.put("page", (page) * limit);//oracle
        map.put("limit", (page - 1) * limit);//oracle

       /* List<tb_user> users = userService.getUsers(map);
        long count = userService.getCount(map);*/
        List<mb_worker> workers = workerMgtService.getWorkers(map);
        long count = workerMgtService.getCount(map);

        PageListData<mb_worker> pageData = new PageListData<mb_worker>();
        pageData.setCode("0");
        pageData.setCount(count);
        pageData.setMessage("");
        pageData.setData(workers);

        Gson gson = new Gson();
        String listJson = gson.toJson(pageData);
        return listJson;
    }
    /**
     * 跳转职员新增页面
     * @param request
     * @return
     */
    @RequestMapping("workerSave")
    public String workerSave(HttpServletRequest request){
        List<mb_role> roles = roleMgtService.getAllRoles();
        request.setAttribute("roles",roles);
        return baseUrl+"add";
    }
    /**
     * 职员新增
     * @param mbWorker
     * @return
     */
    @RequestMapping(value = "save", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(mb_worker mbWorker){
     mbWorker.setCtime(new Timestamp(System.currentTimeMillis()));
     mbWorker.setId(UUIDUtils.getUUID());
     mbWorker.setPassword(new SimpleHash("MD5", mbWorker.getPassword(), "1024").toHex());
     workerMgtService.saveWorker(mbWorker);
     mb_worker_role worker_role=new mb_worker_role();
     worker_role.setId(UUIDUtils.getUUID());
     worker_role.setWorkerId(mbWorker.getId());
     worker_role.setRoleId(mbWorker.getRoleId());
     workerRoleService.save(worker_role);
     //新增用户添加操作日志
     mb_worker workerSession= (mb_worker) SecurityUtils.getSubject().getSession().getAttribute("workerSession");
     mb_operation_log operationLog=new mb_operation_log();
     operationLog.setWorkerName(workerSession.getWorkerName());
     operationLog.setModuleName(moduleName);
     operationLog.setOperaContent("新增"+"["+mbWorker.getWorkerName()+"]"+"账号信息");
     operationLogService.addLog(operationLog);
     Gson gson=new Gson();
     return gson.toJson("操作成功");
    }
    /**
     * 检验职员账号的唯一性
     * @param mbWorker
     * @return
     */
    @RequestMapping(value = "isCheckWorker", produces = "application/json; charset=utf-8")
    @ResponseBody
    public int isCheckWorker(mb_worker mbWorker){
        return workerMgtService.isCheckWorker(mbWorker.getWorkerAccount());
    }
    /**
     * 批量删除职员
     * @param id
     * @return
     */
    @RequestMapping(value="delete", produces = "application/json; charset=utf-8" )
    @ResponseBody
    public String delete(String[] id)
    {

        StringBuilder sb=new StringBuilder();
        for(String str:id){
            mb_worker worker = workerMgtService.infoWorker(str);
            sb.append(worker.getWorkerName()+',');
        }
        //添加批量删除职员的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("删除["+sb.substring(0,sb.length()-1)+"]职员");
        operationLogService.addLog(operationLog);
        workerMgtService.deleteWorkerById(id);
        Gson gson = new Gson();
        return gson.toJson("");
    }
    /**
     * 批量关闭职员
     * @param id
     * @return
     */
    @RequestMapping(value = "close")
    @ResponseBody
    public String close(String id[]) {
        workerMgtService.closeWorkerById(id);
        //添加日志
        StringBuilder sb=new StringBuilder();
        for(String str:id){
            mb_worker worker = workerMgtService.infoWorker(str);
            sb.append(worker.getWorkerName()+',');
        }
        //添加批量关闭职员的操作日志
         mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("关闭["+sb.substring(0,sb.length()-1)+"]职员");
        operationLogService.addLog(operationLog);
        Gson gson = new Gson();
        return gson.toJson("");
    }
    /**
     * 批量开启职员
     * @param id
     * @return
     */
    @RequestMapping(value = "open")
    @ResponseBody
    public String open(String id[]) {
        workerMgtService.openWorkerById(id);
        //添加日志
        StringBuilder sb=new StringBuilder();
        for(String str:id){
            mb_worker worker = workerMgtService.infoWorker(str);
            sb.append(worker.getWorkerName()+',');
        }
        //添加批量开启职员的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("开启["+sb.substring(0,sb.length()-1)+"]职员");
        operationLogService.addLog(operationLog);
        Gson gson = new Gson();
        return gson.toJson("");
    }
    /**
     * 批量重置职员的登录密码
     * @param id
     * @return
     */
    @RequestMapping(value = "restPassword", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String restPassword(String id[]) {
        workerMgtService.restPassword(id);
        //添加批量重置职员密码的操作日志
        StringBuilder sb=new StringBuilder();
        for(String str:id){
            mb_worker worker = workerMgtService.infoWorker(str);
            sb.append(worker.getWorkerName()+',');
        }
        //添加批量重置职员密码的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("重置["+sb.substring(0,sb.length()-1)+"]职员密码");
        operationLogService.addLog(operationLog);
        Gson gson = new Gson();
        for(int i=0;i<id.length;i++){
            mb_worker worker = workerMgtService.infoWorker(id[i]);
            redisClientTemplate.del("shiro_redis_session:" + worker.getWorkerAccount());//删除重新分配角色的用户
        }
        return gson.toJson("");
    }
    /**
     * 查看职员详细信息
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "info", produces = "application/json; charset=utf-8")
    public String info(String id,HttpServletRequest request) {
       mb_worker worker = workerMgtService.infoWorker(id);
        List<String> list = workerRoleService.getRoleNameByWorkerId(id);
        request.setAttribute("worker",worker);
        request.setAttribute("list",list);
        //添加查看详细信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("查看"+"["+worker.getWorkerName()+"]"+"职员详细信息");
        operationLogService.addLog(operationLog);
        Gson gson = new Gson();
        return baseUrl+"info";
    }
    /**
     * 跳转职员信息编辑页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "workerEdit", produces = "application/json; charset=utf-8")
    public String workerEdit(String id,HttpServletRequest request) {
        mb_worker worker =workerMgtService.infoWorker(id);
        request.setAttribute("worker",worker);
        List<mb_role> roles = roleMgtService.getAllRoles();
        request.setAttribute("roles",roles);
        mb_worker_role mbRole = workerRoleService.getRole(id);
        request.setAttribute("mbRole",mbRole);
        return baseUrl+"edit";
    }
    /**
     * 职员信息编辑
     * @param mbWorker
     * @return
     */
    @RequestMapping(value = "edit", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String edit(mb_worker mbWorker) {
        workerMgtService.updateWorkerById(mbWorker);
        workerRoleService.updateRoleByWorkerId(mbWorker);
        redisClientTemplate.del("shiro_redis_session:" + mbWorker.getWorkerName());//删除重新分配角色的用户
        //添加编辑职员信息的操作日志
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("编辑"+"["+mbWorker.getWorkerName()+"]"+"账户信息");
        operationLogService.addLog(operationLog);
        Gson gson=new Gson();
        return gson.toJson("操作成功");
    }
    /**
     * 跳转分配角色的页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "inRole")
    public String inRole(String id, HttpServletRequest request) {
        List<mb_role> roleList = roleMgtService.getAllRoles();
        List<mb_worker_role> workerRoleList =workerRoleService.getRoleByWorkerId(id);
        request.setAttribute("workerId", id);
        request.setAttribute("list", roleList);
       request.setAttribute("workerRoleList", workerRoleList);

        return baseUrl+"worker-inRole";
    }
    /**
     * 保存分配角色
     * @param workerId
     * @param ids
     * @return
     */
    @RequestMapping(value = "saveRole")
    @ResponseBody
    public String saveRole(String workerId, String[] ids) {
            workerRoleService.deleteRoleByWorkerId(workerId);

        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            mb_worker_role workerRole = new mb_worker_role();
            workerRole.setWorkerId(workerId);
            workerRole.setRoleId(id);
            workerRole.setId(UUIDUtils.getUUID());

            //根据角色ID查找角色信息
            mb_role role = roleMgtService.infoRole(id);
            sb.append(role.getRolename() + ",");
            workerRoleService.save(workerRole);
        }
        //添加分配角色的操作日志
        mb_worker worker = workerMgtService.infoWorker(workerId);
        mb_operation_log operationLog=new mb_operation_log();
        operationLog.setModuleName(moduleName);
        operationLog.setOperaContent("分配["+worker.getWorkerName()+"职员],["+sb.substring(0,sb.length()-1)+"]角色");
        operationLogService.addLog(operationLog);
//删除重新分配角色的用户
        redisClientTemplate.del("shiro_redis_session:" + worker.getWorkerAccount());
        Gson gson = new Gson();
        return gson.toJson("操作成功");
    }
    /**
     * 修改密码检验输入密码是否正确
     * @param id
     * @param oldPwd
     * @return
     */
    @RequestMapping(value = "isCheckOldPwd", produces = "application/json; charset=utf-8")
    @ResponseBody
    public int isCheckOldPwd(String id,String oldPwd){

        mb_worker worker = workerMgtService.infoWorker(id);
        String password = new SimpleHash("MD5", oldPwd, "1024").toHex();
        if (worker.getPassword().equals(password)){
            return 1;
        }
        return 0;

    }
    /**
     * 保存新密码
     * @param id
     * @param userPwd
     * @return
     */
    @RequestMapping(value = "savePassWord", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String savePassWord(String id,String userPwd){
        Gson gson=new Gson();
        Map<String,String> map=new HashMap<String,String>();
        try{
        String password = new SimpleHash("MD5", userPwd, "1024").toHex();
        map.put("id",id);
        map.put("password",password);
        workerMgtService.updatePssword(map);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return gson.toJson(new Message("1","修改成功"));
    }











}
