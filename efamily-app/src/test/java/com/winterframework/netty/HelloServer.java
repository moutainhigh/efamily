package com.winterframework.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelPipeline;

import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.util.concurrent.Executors;

/** 
 
 * Netty 服务端代码 
 
 * 
 
 * @author lihzh 
 
 * @alia OneCoder 
 
 * @blog http://www.coderli.com 
 
 */  
  
public class HelloServer {  
  
   
  
    public static void main(String args[]) {  
  
        /*// Server服务启动器  
  
        ServerBootstrap bootstrap = new ServerBootstrap(  
  
                new NioServerSocketChannelFactory(  
  
                        Executors.newCachedThreadPool(),  
  
                        Executors.newCachedThreadPool()));  
  
        // 设置一个处理客户端消息和各种消息事件的类(Handler)  
  
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {  
  
            @Override  
  
            public ChannelPipeline getPipeline() throws Exception {  
  
                return Channels.pipeline(new HelloServerHandler());  
  
            }  
  
        });  
  
        // 开放8000端口供客户端访问。  
  
        bootstrap.bind(new InetSocketAddress(8000));  */
  
    }  
}
  
   