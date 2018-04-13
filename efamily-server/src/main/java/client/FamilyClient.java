package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.winterframework.efamily.server.core.ChannelManager;
import com.winterframework.efamily.server.protocol.Delimiters;
import com.winterframework.efamily.server.protocol.FmlRequest;
import com.winterframework.modules.web.util.JsonMapper;

public class FamilyClient {
	private static JsonMapper jmapper = JsonMapper.nonEmptyMapper();
	public static void main(String[] args) throws Exception { 
		// comm();
		String host = "120.25.160.36";
		//String host = "112.74.213.113";
		// String host = "54.255.177.102";
		// String host = "localhost";
		//String host="2001:470:18:74b::2";
		boolean isPush = true;
		// String host = "192.168.1.10";
		int port = Integer.parseInt("6999");
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		 
		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addFirst(
							"frameDecoder",
							new DelimiterBasedFrameDecoder(Integer.MAX_VALUE,
									Delimiters.protocolDelimiter()));
					ch.pipeline().addLast(new CliRepDecoder());
					ch.pipeline().addLast(new CliReqEncode());
					ch.pipeline().addLast(new ClientHandler());
				}
			});

			// Start the client.
			int i = 1;
			while (i-- > 0) {
				ChannelFuture f = b.connect(host, port).sync(); // (5)
				 
				FmlRequest fr = new FmlRequest();
				fr.setEncode((byte) 0);
				fr.setEncrypt((byte) 0);
				fr.setVersion((byte) 1);
				fr.setClinetType((byte) 0);
				fr.setExtend((byte) 0);
				fr.setSessionId(System.currentTimeMillis());
				fr.setStatus(0);
				fr.setCommand(28999);
				fr.setToken("bb6f8c1b331f4b0985ca140424be71f3"); 
				/*fr.setValue("toId","612");
				fr.setValue("status",1+"");*/
				//fr.setValue("resourceId","528a0267a7f84198995b8d4f1c79fbc7");
				/*fr.setValue("type",ResourceType.IMAGE);
				fr.setValue("mediaType",ResourceType.ImageType.PNG);
				fr.setValue("mediaContent", new TestImageBinary().image2buff
				 ("C:\\Users\\ibm\\Downloads\\fml1.png"));
				fr.setValue("mediaContent", new TestImageBinary().image2buff
						 ("http://192.168.1.111/123.jpg"));*/
				/*fr.setValue("mediaContent", new TestImageBinary().image2buff(
				 "http://103.17.117.63:6070/audios/tip1.mp3"));
				fr.setValue("mediaContent", "新浪网新闻中心是新浪网最重要的频道之一");
				 */
				if (!isPush) {
					/*
					 * fr.setValue("userId", "A"+userNo); fr.setValue("roomId",
					 * "No.1");
					 */
					// fr.setValue("content", "Hello."+userNo);
					// fr.setValue("content", new
					// TestImageBinary().getImageBinary());
					/*
					 * String s=new TestImageBinary().getImageBinary(); //
					 * System.out.println(s.length()); //String
					 * s="Happy new year."; fr.setValue("imageFile", s);
					 */
					 Map<String,String> m=new HashMap<String,String>();
					 m.put("time", "1474356907000"); 
					 m.put("status","1");
					 m.put("code","108");
					 
					 fr.setData(m);
					//fr.setValue("time", System.currentTimeMillis() + "");
					/*
					 * fr.setValue("userId", "1001"); fr.setValue("deviceId",
					 * "1001"); fr.setValue("type", "image");
					 * //fr.setValue("url",
					 * "http://103.17.117.63:6070/images/123.jpg");
					 * fr.setValue("url", "http://192.168.1.111/123.jpg");
					 */
					//fr.setValue("userId", 675 + ""); 
 
					/*
					 * fr.setValue("userId", "1001"); fr.setValue("deviceId",
					 * "1002"); fr.setValue("code", "400001");
					 * fr.setValue("value", "100"); fr.setValue("time",
					 * System.currentTimeMillis()+"");
					 */
					// fr.setValue("content",
					// "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy");
				} else {
					fr.setCommand(299999);
					//fr.setValue("paramCode", "100131");
					
				 	/*fr.setValue("pushUserId", 1985+"");
					fr.setValue("pushDeviceId", 401+"");
					fr.setValue("command", 20209+"");
					fr.setValue("code", "410004");
					*/
					
				 	fr.setValue("pushUserId", 2024+"");
					fr.setValue("pushDeviceId", 398+"");
					fr.setValue("command", 20152+"");
					//fr.setValue("code", "410000"); 
					
					
					/*fr.setValue("pushUserId", 1716+"");
					fr.setValue("pushDeviceId", 393+"");
					fr.setValue("command", 20499+""); 
					fr.setValue("value", "08:00|14:00");*/
				}
 
				f.channel().writeAndFlush(fr);
				// Thread.sleep(1000);
				f.channel().closeFuture().sync();
			}

			// Wait until the connection is closed.

		} finally {
			workerGroup.shutdownGracefully();
		}
		
	}
	public static void simulateChannel(Long userId,Long deviceId){
		ChannelManager.remove(userId, deviceId);
	}

	public static void testPush() {

	}

	public static void comm() {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("localhost", 6090), 5000);
			// ���������
			OutputStream ou = socket.getOutputStream();

			int i = 10;
			FmlRequest fr = new FmlRequest();
			fr.setEncode((byte) 'A');
			fr.setEncrypt((byte) 'B');
			fr.setVersion((byte) 'C');
			fr.setExtend((byte) 'D');
			fr.setCommand(new Random().nextInt());
			fr.setSessionId(i);

			/*
			 * SocketChannel socketChannel = SocketChannel.open(new
			 * InetSocketAddress("localhost", 6090));
			 * socketChannel.configureBlocking(false); ByteBuffer
			 * buf=ByteBuffer.wrap(ObjectToByte(fr)); // buf.flip();
			 * socketChannel.write(buf); socketChannel.close();
			 */
			ou.write(getReq());

			// ou.write("ssssssshvfjvjycjhchcjujcjuchvhyujuummvvbhbjmn".getBytes());
			/*
			 * ByteBufOutputStream bout = new ByteBufOutputStream(new
			 * ReplayingDecoderBuffer()); bout.write(LENGTH_PLACEHOLDER);
			 * ObjectOutputStream oout = new CompactObjectOutputStream(bout);
			 * oout.writeObject(msg); oout.flush(); oout.close(); int endIdx =
			 * out.writerIndex();
			 */

			// ou.write("hello3333333333333333333333333333".getBytes());
			// ou.write("android �ͻ���".getBytes("UTF-8"));
			ou.flush();
			ou.close();
			socket.close();

		} catch (SocketTimeoutException aa) {
		}

		catch (IOException e) {
		}
	}

	public static Object ByteToObject(byte[] bytes) {
		Object obj = null;
		try {
			// bytearray to object
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);

			obj = oi.readObject();
			bi.close();
			oi.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	public static byte[] getReq() {
		ByteBuffer bf = ByteBuffer.allocate(16);
		bf.put((byte) 'A');
		bf.put((byte) 'B');
		bf.put((byte) 'C');
		bf.put((byte) 'D');
		return bf.array();
	}

	public static byte[] ObjectToByte(java.lang.Object obj) {
		byte[] bytes = null;
		try {
			// object to bytearray
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			// oo.writeObject(obj);
			/*
			 * oo.write((byte)'A'); oo.write((byte)'B'); oo.write((byte)'C');
			 * oo.
			 * write((byte)'D');http://blog.csdn.net/maosijunzi/article/details
			 * /14127481 oo.writeInt(1); oo.writeInt(2);
			 * oo.writeInt("33".length(
			 * )+"33".getBytes().length+"3".length()+"3".getBytes().length);
			 * oo.writeInt("33".length()); oo.write("33".getBytes());
			 * oo.writeInt("3".length()); oo.write("3".getBytes());
			 * oo.write('\n');
			 */
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
		} catch (Exception e) {
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return bytes;
	}
	
	public static void pressure(){
		
	}

}