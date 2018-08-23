package com.zw.zwpp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketUtil {
	public static void main(String[] args) {
		try (Socket socket = new Socket("time.nist.gov", 13)){
			socket.setSoTimeout(15000);
			InputStream in = socket.getInputStream();
			StringBuilder sb =new StringBuilder();
			InputStreamReader reader = new InputStreamReader(in,"ASCII");
			for (int c = reader.read(); c !=-1; c = reader.read()) {
				sb.append((char)c);
			}
			String[] timeArr = sb.toString().split(" ");
			String dateTime = timeArr[1]+" "+timeArr[2]+" UTC";
			try {
				Date date = new SimpleDateFormat("yy-MM-dd HH:mm:ss").parse(dateTime);
				long time = date.getTime();
				System.out.println(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(sb);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("连接失败");
		}
	}
}
