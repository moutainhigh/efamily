package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.protocol.ProtocolUtil;

public class CliRepDecoder extends ReplayingDecoder<Void> { // (1)
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { 
		if (in.readableBytes() < 57) { 
			return;
		}
		in.markReaderIndex();
		byte encode = in.readByte();
		byte encrypt = in.readByte();
		byte version = in.readByte();
		byte clinetType = in.readByte();
		byte extend = in.readByte();
		long sessionid = in.readLong();
		int status = in.readInt();
		int command = in.readInt(); 
		byte [] contents=new byte [32];
		ByteBuf tos=in.readBytes(contents);
		String toten=new String(contents);
		
		int length = in.readInt(); // 数据包长
		if (in.readableBytes() < length) {
			in.resetReaderIndex();
			return;
		}
		ByteBuf dataBuffer =in.readBytes(length);

		FmlResponse response = new FmlResponse();
		response.setEncode(encode);
		response.setEncrypt(encrypt);
		response.setVersion(version);
		response.setClinetType(clinetType);
		response.setExtend(extend);
		response.setSessionId(sessionid);
		response.setStatus(status);
		response.setCommand(command);
		response.setToken(toten);
		Map<String,String> data=new HashMap<String,String>();
		try{
			data=ProtocolUtil.decode(encode, dataBuffer);
		}catch(Exception e){
			System.out.println("client decode error."+e);
		}
		response.setData(data);
		response.setIp(ProtocolUtil.getClientIp(ctx.channel()));
		out.add(response);
	}
}