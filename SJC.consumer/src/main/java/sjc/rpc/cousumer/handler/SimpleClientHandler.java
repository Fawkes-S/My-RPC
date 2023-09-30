package sjc.rpc.cousumer.handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import sjc.rpc.cousumer.core.ResultFuture;
import sjc.rpc.cousumer.param.Response;

public class SimpleClientHandler extends ChannelInboundHandlerAdapter implements ChannelHandler {
	private static final Executor exec = Executors.newFixedThreadPool(10);
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		final Object m = msg;
		if(msg.toString().equals("ping")){
			System.out.println("收到读写空闲ping,向服务端发送pong");
			ctx.channel().writeAndFlush("pong\r\n");
		}
		
		exec.execute(new Runnable() {
			
			public void run() {
				Response response = JSONObject.parseObject(m.toString(), Response.class);
				System.out.println("SimpleClientHandler中的Response:"+JSONObject.toJSONString(response));
				ResultFuture.receive(response);				
			}
		});

	}
	
}
