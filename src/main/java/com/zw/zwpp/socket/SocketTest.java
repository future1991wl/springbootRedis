package com.zw.zwpp.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketTest {
	public static void main(String[] args) {
		Socket socket = new Socket();
		SocketAddress socketAddress = new InetSocketAddress("time.nist.gov", 13);
		try {
			socket.connect(socketAddress);
			// 获取远程连接地址
			SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
			// 获取本机地址
			SocketAddress localSocketAddress = socket.getLocalSocketAddress();
			System.out.println("");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("cuowu");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
