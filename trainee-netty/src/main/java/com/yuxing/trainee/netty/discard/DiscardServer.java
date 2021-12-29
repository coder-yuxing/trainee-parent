package com.yuxing.trainee.netty.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 抛弃服务器
 *
 * @author yuxing
 * @since 2021/12/29
 */
public class DiscardServer {

    /**
     * 服务器端口
     */
    private final int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // NioEventLoopGroup 用于处理I/O操作的多线程事件循环器，Netty提供了许多不同的EventLoopGroup的实现用于处理不同的传输
        // 本例中我们实现一个服务器应用，因此会有两个EventLoopGroup被使用？？ 为什么服务端应用需要两个？
        // boosGroup 用于接收请求进入的连接
        // workerGroup 用于处理已经被接收的连接
        // 一旦 “boss” 接收到连接，就会将连接信息注册到“worker”上
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // ServerBootstrap 是一个用于启动NIO服务的辅助启动类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // ChannelInitializer 类是一个特殊的处理类，它用于帮助使用者配置一个新的channel
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口
            ChannelFuture channelFuture = bootstrap.bind(this.port).sync();

            // 等待服务器 socket关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        new DiscardServer(8899).run();
    }
}
