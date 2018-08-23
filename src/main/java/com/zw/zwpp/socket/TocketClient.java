package com.zw.zwpp.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TocketClient {
	public static void main(String[] args) {
		try (Socket socket = new Socket("10.136.182.7",8989)) {
			socket.setSoTimeout(1500000);
			 StringBuffer sb=new StringBuffer("GET http://v.qq.com/ HTTP/1.1\r\n");
		        sb.append("Host:v.qq.com\r\n");
		        sb.append("Connection: keep-alive\r\n");
		        sb.append("Upgrade-Insecure-Requests: 1\r\n\r\n");
			OutputStream socketOut = socket.getOutputStream();
			socketOut.write(sb.toString().getBytes());
			socket.shutdownOutput();// 关闭输出流
			 Scanner scannerSocket=new Scanner(socket.getInputStream());
	            String data;
	            while (scannerSocket.hasNextLine()){
	                data=scannerSocket.nextLine();
	                System.out.println(data);
	            }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
