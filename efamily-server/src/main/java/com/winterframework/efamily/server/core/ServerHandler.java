package com.winterframework.efamily.server.core; 

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.modules.utils.SpringContextHolder;

@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger log = LoggerFactory.getLogger(ServerHandler.class); 
 
	private static final INettyHandler handler=(INettyHandler)SpringContextHolder.getBean("serviceHandler");
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
		cause.printStackTrace();
		//ctx.close();
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception { 
		try { 
			super.channelRead(ctx, msg);
			handler.read(ctx, msg);
			log.info("ok"); 
		} catch (Exception e) {
			log.error(e.getMessage()); 
		}
//		f.addListener(new ChannelFutureListener() {
//			@Override
//			public void operationComplete(final ChannelFuture future) {
//				assert f == future;
////				ctx.close();
//			}
//		});
	}
	public void channelActive(final ChannelHandlerContext ctx)  throws Exception{  
		log.info("active");
		super.channelActive(ctx);
		handler.active(ctx);
		/*
		f.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) {
				assert f == future;
				ctx.close();
			}
		}); */// (4)
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		handler.inactive(ctx);
	}
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) {  
            IdleStateEvent e = (IdleStateEvent) evt;  
            if (e.state() == IdleState.READER_IDLE) {  
            	String tokenKey=ChannelManager.getBy(ctx.channel());
            	tokenKey=null==tokenKey?"":new String(Base64Util.getBytesFromBASE64(tokenKey));
                log.info("READER_IDLE 读超时.tokenKey="+tokenKey);
                ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {  
                log.info("WRITER_IDLE 写超时");  
            }  
        }  
	}

}