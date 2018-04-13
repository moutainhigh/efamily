package com.winterframework.efamily.server.core;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.efamily.base.enums.StatusCode;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.redis.RedisClient;
import com.winterframework.efamily.server.exception.ServerException;
import com.winterframework.efamily.server.protocol.Command;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.utils.HttpUtil;

@Service("serviceHandler")
public class ServiceHandler implements INettyHandler,IServiceHandler{
	private static final Logger log = LoggerFactory.getLogger(ServiceHandler.class); 
	
	@Resource(name="handlerDispatcher")
	private IHandlerDispatcher handlerDispatcher;
	@Resource(name="httpUtil")
	private HttpUtil httpUtil;
	@Resource(name="RedisClient")
	private RedisClient redisClient;	
	
	@Override
	public void active(ChannelHandlerContext ctx) throws ServerException{
		/**
		 * 1.获取句柄--handle
		 * 2.生成token
		 * 3.管理句柄
		 */
	}
	@Override 
	public void read(ChannelHandlerContext ctx,Object obj) throws ServerException{
		FmlRequest request = (FmlRequest) obj; 
		/*if(request.getCommand()==20744
				|| request.getCommand()==20725
				|| request.getCommand()==20844
				|| request.getCommand()==20187
				|| request.getCommand()==20197
				|| request.getCommand()==20830){
			FmlResponse res=new FmlResponse(request);
			res.setStatus(StatusCode.OK.getValue());
			ctx.channel().writeAndFlush(res);
			return;
		}*/
		FmlResponse res=null;
		log.info(request.toString());
		Context context=TokenManager.getTokenContext(request.getToken());
		if(null==context){
			context=new Context();
		} 
		log.info("has got context.");
		if(null!=context.getUserId() && !ctx.channel().equals(ChannelManager.get(context.getUserId(), context.getDeviceId()))){
			log.info("通道更换： userId="+context.getUserId()+";channelNew="+ctx.channel()+";channelOld="+ChannelManager.get(context.getUserId(), context.getDeviceId()));
			ChannelManager.saveForChannelSwitch(context.getUserId(), context.getDeviceId(),request.getToken(), ctx.channel());
		}
		if(request.getCommand()==Command.HEART_BEAT.getCode() || request.getCommand()==Command.DEVICE_HEARTBEAT.getCode()){
			res=new FmlResponse(request);
			res.setStatus(StatusCode.OK.getValue());
			ctx.channel().writeAndFlush(res);
			return;
		}		
		try{
			if(request.getCommand()==Command.LOGON.getCode() || request.getCommand()==Command.DEVICE_HELLO_NEW.getCode()){
				context.set("channel", ctx.channel());
			}
			res=handlerDispatcher.dispatch(context,request);
			if(null==res) return;	//没有响应  则不回写
		}catch(ServerException e){
			log.error("ServerException:",e);
			res=new FmlResponse(request);
			res.setStatus(e.getCode());		 
		}catch(Exception e){
			log.error("Exception:",e);
			res=new FmlResponse(request);
			res.setStatus(StatusCode.FAILED.getValue());			
		} 
		//设备的所有响应均返回status=0  除非更新新版软件
		
		String commandStr=res.getCommand()+"";
		if(res.getCommand()!=Command.DEVICE_HELLO.getCode() 
				&& res.getCommand()!=Command.DEVICE_HELLO_NEW.getCode() 
				&& res.getCommand()!=Command.DEVICE_HARDWARE.getCode() 
				&& commandStr.length()==5 && commandStr.substring(0,1).equals("2")){
			if(res.getStatus()!=StatusCode.UN_LOGIN.getValue()){
				res.setStatus(StatusCode.OK.getValue());
			}
		}
		log.info(res.toString());
		final ChannelFuture f=ctx.channel().writeAndFlush(res);
	}
	@Override
	public void inactive(ChannelHandlerContext ctx) throws ServerException {
		ChannelManager.remove(ctx.channel());
	}
	
	public void push(final Long userId, Long deviceId, final FmlResponse response) throws ServerException{
		log.info("userId="+userId+","+response.toString()); 
		Channel channel=ChannelManager.get(userId,deviceId);  
		if(null==channel){
			log.error("用户未登录，通道为空：userId = "+userId);
			return;
			//throw new ServerException(StatusCode.UN_CONNECT.getValue());
		}
		String token=TokenManager.getTokenForPush(userId, deviceId);
		if(null==token){
			log.info("token 为空，未登录，101。 userId = "+userId+" ; ");
			throw new ServerException(StatusCode.UN_LOGIN.getValue());
		}
		response.setToken(token);
		try{
			channel.writeAndFlush(response);
		}catch(Exception e){
			log.error("推送数据时出现异常：[ response = "+response.toString() +" ]");
			throw new ServerException(StatusCode.NETTY.getValue());
		}
	}
}
