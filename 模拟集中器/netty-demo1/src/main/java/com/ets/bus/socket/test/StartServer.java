package com.ets.bus.socket.test;

import com.ets.bus.socket.server.SocketServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @create 2019- 04-08 17:39
 */
@Service
public class StartServer implements InitializingBean {

    @Autowired
    protected ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private NettyClientService nettyClientService;

    public void afterPropertiesSet() throws Exception {
        taskExecutor.execute(new Runnable(){
            public void run() {
                try {
                    nettyClientService.main();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
