package com.zw.zwpp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TocketClient {
	public static void main(String[] args) {
		InetAddress localHost;
		Socket socket = null;
		InputStreamReader isr=null;
		try {
			localHost = InetAddress.getLocalHost();
			socket = new Socket("10.136.182.7", 9999, localHost, 8888);
			InputStream inputStream = socket.getInputStream();
			StringBuilder sb = new StringBuilder();
			isr = new InputStreamReader(inputStream,"ASCII");
			for(int c = isr.read(); c!=-1;c=isr.read()) {
				sb.append((char)c);
			}
			System.out.println(sb.toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(isr!=null) {
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(socket!=null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
