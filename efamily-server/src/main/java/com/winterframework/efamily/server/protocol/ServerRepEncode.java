package com.winterframework.efamily.server.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerRepEncode extends MessageToByteEncoder<FmlResponse> {
	private static final Logger log = LoggerFactory.getLogger(ServerRepEncode.class); 
	
	@Override
	protected void encode(ChannelHandlerContext ctx, FmlResponse msg, ByteBuf out) throws Exception {
		try {
			ByteBuffer headBuffer = ByteBuffer.allocate(57);
			/**
			 * 先组织报文头
			 */
			headBuffer.put(msg.getEncode());
			headBuffer.put(msg.getEncrypt());
			headBuffer.put(msg.getVersion());
			headBuffer.put(msg.getClinetType());
			headBuffer.put(msg.getExtend());
			headBuffer.putLong(msg.getSessionId());
			headBuffer.putInt(msg.getStatus());
			headBuffer.putInt(msg.getCommand());
			headBuffer.put(msg.getToken().getBytes());
			
			/**
			 * 组织报文的数据部分
			 */
			ByteBuf dataBuffer =null;
			ByteBuf totalBuffer =null;
			try{
				dataBuffer=ProtocolUtil.encode(msg.getEncode(), msg.getData(), ctx);
				if(dataBuffer!=null){//有些接口只需返回数据头 map中没数据
					int length = dataBuffer.readableBytes();
					headBuffer.putInt(length);
				}else{
					headBuffer.putInt(0);
				}
				/**
				 * 非常重要
				 * ByteBuffer需要手动flip()，ChannelBuffer不需要
				 */
				headBuffer.flip();
				totalBuffer = ctx.alloc().buffer();
				totalBuffer.writeBytes(headBuffer);
				if(dataBuffer!=null){
					totalBuffer.writeBytes(dataBuffer);
				}
				totalBuffer.writeBytes(Delimiters.getDelimiter());
				out.writeBytes(totalBuffer);
			
				log.debug("*************** 输出参数  ************************");
				log.debug("* encode = "+msg.getEncode());
				log.debug("* encrypt = "+msg.getEncrypt());
				log.debug("* version = "+msg.getVersion());
				log.debug("* clinetType = "+msg.getClinetType());
				log.debug("* extend = "+msg.getExtend());
				log.debug("* command = "+msg.getCommand());
				log.debug("* sessionid = "+msg.getSessionId());
				log.debug("* result = "+msg.getStatus());
				log.debug("* toten = "+msg.getToken());
				if(dataBuffer!=null){
					log.debug("* length = "+dataBuffer.readableBytes());
				}
				if( msg.getData()!=null){
					for (Map.Entry<String, String> entry : msg.getData().entrySet()) {
						log.debug("* [key= " + entry.getKey() + "] [ value= " + entry.getValue()+" ]");
					}
				}else{
					log.debug("* msg.getData = null ");
				}
				
				
				log.debug("*************** 输出结束 *************************");
				msg=null;
			}finally{
				if(dataBuffer!=null){
					dataBuffer.release();
					dataBuffer=null;
				}
				if(totalBuffer!=null){
					totalBuffer.release();
					totalBuffer=null;
				}
			}
		} catch (Exception e) {
			log.error("ServerRepEncode",e);
			throw e;
		}
	
	}

}