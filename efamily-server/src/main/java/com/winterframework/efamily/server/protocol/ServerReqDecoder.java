package com.winterframework.efamily.server.protocol;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.utils.CharsetFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class ServerReqDecoder extends ReplayingDecoder<Void> { // (1)
	private static final Logger log = LoggerFactory.getLogger(ServerReqDecoder.class); 

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {		
		try {
			if (in.readableBytes() < 57) {
				log.error("ServerReqDecoder ：：：：：：：：： in.readableBytes() < 57");
				return;
			}

			byte encode = in.readByte();
			byte encrypt = in.readByte();
			byte version = in.readByte();
			byte clinetType=in.readByte();
			byte extend = in.readByte();
			Long sessionid = in.readLong();
			int status = in.readInt();
			int command = in.readInt();
			byte [] contents=new byte [32];
			in.readBytes(contents);
			String toten=new String(contents);
			int length = in.readInt(); // 数据包长
			if (in.readableBytes() < length) {
				in.resetReaderIndex();
				return;
			}
			
//		ctx.pipeline().remove(this);	
//		byte [] file=new byte [length];
//		ByteBuf dataBuffer=in.readBytes(file);
			FmlRequest request = new FmlRequest();
			request.setEncode(encode);
			request.setEncrypt(encrypt);
			request.setVersion(version);
			request.setClinetType(clinetType);
			request.setExtend(extend);
			request.setCommand(command);
			request.setSessionId(sessionid);
			request.setStatus(status);
			request.setToken(toten);
			request.setLength(length);
			
			ByteBuf dataBuffer =null;
			try{
				dataBuffer=in.readBytes(length);
				request.setData(ProtocolUtil.decode(encode, dataBuffer));
			}catch(Exception e){
				log.error("decode error:"+dataBuffer==null?"":new String(dataBuffer.array(),CharsetFactory.getCharset(encode)));
	    		throw e;
			}finally{
				if(dataBuffer!=null){
					dataBuffer.release();
				}
			}
			request.setIp(ProtocolUtil.getClientIp(ctx.channel()));
			
			log.debug("=============== 输入参数  ========================");
			log.debug("= encode = "+encode);
			log.debug("= encrypt = "+encrypt);
			log.debug("= version = "+version);
			log.debug("= clinetType = "+clinetType);
			log.debug("= extend = "+extend);
			log.debug("= command = "+command);
			log.debug("= sessionid = "+sessionid);
			log.debug("= result = "+status);
			log.debug("= toten = "+toten);
			log.debug("= length = "+length);
			for (Map.Entry<String, String> entry : request.getData().entrySet()) {
				log.debug("= [key= " + entry.getKey() + "] [ value= " + entry.getValue()+" ]");
			}
			log.debug("=============== 输入结束  ========================");
			
			
			out.add(request);
		} catch (Exception e) {
			log.error("ServerReqDecoder",e);
		}
		
		
//		for(Object o:out){
//			System.out.println(o.toString());
//		}
		
	}
	
}