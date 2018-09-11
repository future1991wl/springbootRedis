package com.zw.zwpp.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SerSocket {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(9999);
			Socket socket = serverSocket.accept();
			System.out.println("服务器ip："+socket.getLocalAddress()+"服务器端口："+socket.getLocalPort()+"客户端ip："+socket.getRemoteSocketAddress()+"客户端端口："+socket.getPort());
			OutputStream outputStream = socket.getOutputStream();
			Writer writer = new OutputStreamWriter(outputStream, "ASCII");
			Date date = new Date();
			writer.write(date.toString()+"\r\n");
			writer.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
