package com.ets.bus.socket.test;

import com.ets.bus.socket.coder.ConcentratorDecoder;
import com.ets.bus.socket.coder.ConcentratorEncoder;
import com.ets.bus.socket.server.ConstantValue;
import com.ets.bus.socket.server.ServerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author 宋晨
 * @create 2019/5/7
 * 批量任务
 */
@Service
@Scope
public class NettyClientService {
    public void main() throws Exception{
//        System.out.println("启动集中器服务......");
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//        try {
//            ServerBootstrap serverBootstrap = new ServerBootstrap();
//            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//                    .childHandler(new NettyClientHandler());
//
//            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
//            channelFuture.channel().closeFuture().sync();
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//            System.out.println("集中器服务关闭！");
//        }
        new NettyClientTest().connect(8899, "127.0.0.1",TestClient.deviceId);
    }

    /**
     * 连接服务器
     *
     * @param port
     * @param host
     * @throws Exception
     */
    public void connect(int port, String host) throws Exception {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 客户端辅助启动类 对客户端配置
            Bootstrap b = new Bootstrap();
            b.group(group)//
                    .channel(NioSocketChannel.class)//
                    .option(ChannelOption.TCP_NODELAY, true)//
                    .handler(new InitMyChannelHandler());//
            // 异步链接服务器 同步等待链接成功
            ChannelFuture f = b.connect(host, port).sync();
            // 等待链接关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
            System.out.println("客户端优雅的释放了线程资源...");
        }

    }

}
