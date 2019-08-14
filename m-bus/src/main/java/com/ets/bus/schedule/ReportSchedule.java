package com.ets.bus.schedule;

import com.ets.bus.reportQuery.service.ReportService;
import com.ets.bus.socket.server.BasicRedisDao;
import com.ets.utils.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author xuqiang
 * @date 2019/8/12 16:44
 */
@Component
public class ReportSchedule {
    private static final String key = "redisKey";
    @Autowired
    private ReportService reportService;

    @Autowired
    private BasicRedisDao redisDao;

    @Scheduled(cron = "${spring.schedule.cron}")
    public void batchInsert(){

        try{
            //分布式时候加锁执行
            //boolean flag = redisDao.getLock("key", "schedule",200L,50L,3000L);
            //if(flag){
                System.out.println("启动任务========");
                reportService.insertDaily();
            //}else {
             //   return;
            //}

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            redisDao.releaseLock(key);
        }



    }
}
