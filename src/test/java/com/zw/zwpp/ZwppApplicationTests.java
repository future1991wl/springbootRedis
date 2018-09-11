package com.zw.zwpp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zw.zwpp.utils.RedisUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZwppApplicationTests {
	@Autowired
	private RedisUtil redisUtil;

	@Test
	public void contextLoads() {
		redisUtil.set("heihei", "hehe");
		Object object = redisUtil.get("heihei");
		System.out.println(object);
	}

	@Test
	public void name() {
		Socket socket = null;
		SocketAddress remoteSocketAddress = null;
		try {
			socket = new Socket("www.yahoo.com", 80);
			remoteSocketAddress = socket.getRemoteSocketAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Socket socket2 = new Socket();
		try {
			socket2.connect(remoteSocketAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 未通
	 */
	@Test
	public void proxytest() {
		SocketAddress socketAddress =new InetSocketAddress("myproxy.example.com", 1080);
		Proxy proxy = new Proxy(Proxy.Type.SOCKS, socketAddress);
		Socket s =new Socket(proxy);
		SocketAddress remote = new InetSocketAddress("login.ibiblio.org", 25);
		try {
			s.connect(remote);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void addressTest() {
		try {
			Socket socket = new Socket("www.baidu.com",80);
			InetAddress inetAddress = socket.getInetAddress();
			int port = socket.getPort();
			InetAddress localAddress = socket.getLocalAddress();
			int localPort = socket.getLocalPort();
			boolean tcpNoDelay = socket.getTcpNoDelay();
			if(socket.getSoLinger() ==-1) {
				socket.setSoLinger(true, 240);
			}
			System.out.println(tcpNoDelay);
			System.out.println(socket.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void receiveBuffer() {
		Socket socket = new Socket();
		SocketAddress socketAddress = new InetSocketAddress("www.baidu.com", 80);
		try {
			socket.connect(socketAddress);
			int receiveBufferSize = socket.getReceiveBufferSize();
			int sendBufferSize = socket.getSendBufferSize();
			boolean keepAlive = socket.getKeepAlive();
			
			System.out.println("网络输入建议接收缓存区大小"+receiveBufferSize+"网络输入建议发送缓存区大小"+sendBufferSize);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Test
	public void bindPortSocket() {
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket socekt = null;
		try {
			socekt = new Socket("www.baidu.com",80,localHost,8080);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(socekt!=null) {
				try {
					socekt.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
