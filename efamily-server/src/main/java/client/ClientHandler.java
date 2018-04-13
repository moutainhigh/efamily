package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.efamily.server.protocol.FmlResponse;
import com.winterframework.efamily.server.protocol.ProtocolUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	private ByteBuf buf;

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		// TODO Auto-generated method stub
		IdleStateEvent e = (IdleStateEvent) evt;
        switch (e.state()) {
            case WRITER_IDLE:
                //ctx.writeAndFlush(pingMsg);
                System.out.println("send ping to server----------");
                break;
            default:
                break;
        }
		super.userEventTriggered(ctx, evt);
	}
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		buf = ctx.alloc().buffer(4); // (1)
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {
		buf.release(); // (1)
		buf = null;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(ctx.channel().isRegistered());
		System.out.println(ProtocolUtil.getClientIp( ctx.channel())); 
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		FmlResponse m = (FmlResponse) msg; 
		String c= m.getValue("mediaContent");
		//System.out.println(c);
		//String cc=new String(Base64Util.getBytesFromBASE64(c));
		//TestImageBinary.buff2Image(Base64Util.getBytesFromBASE64(c),m.getValue("mediaType"));
		//TestImageBinary.toImage((Base64Util.getBytesFromBASE64(c)));
		
		String s=m.getValue("data");
		/*Map mm=JsonUtils.fromJson(s, Map.class);
		ResourceUtils.toImage(ResourceUtils.hex2byte((String)mm.get("content")));*/
		System.out.println(m);
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}