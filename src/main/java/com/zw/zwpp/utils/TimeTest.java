package com.zw.zwpp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeTest {
	private static final String HOSTNAME="time.nist.gov";
	public static void main(String[] args) {
		Date d =TimeTest.getDateFromNetWork();
		System.out.println("It Is " + d);
	}
	private static Date getDateFromNetWork() {
		long differenceBetweenEpochs =2208988800L;
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		Calendar epoch1900 = Calendar.getInstance(gmt);
		epoch1900.set(1900, 01, 01, 00, 00, 00);
		long epoch1900ms = epoch1900.getTime().getTime();
		Calendar epoch1970 = Calendar.getInstance(gmt);
		epoch1970.set(1970, 01, 01, 00, 00, 00);
		long epoch1970ms = epoch1970.getTime().getTime();
		long differenceInMS = epoch1970ms - epoch1900ms;
		long diff = differenceInMS/1000;
		
		Socket socket = null;
		try {
			socket = new Socket(HOSTNAME,37);
			socket.setSoTimeout(15000);
			InputStream in = socket.getInputStream();
			long secondsSince1900 = 0;
			for (int i = 0; i < 4; i++) {
				secondsSince1900 = (secondsSince1900 << 8)|in.read();
			}
			long secondsSince1970 = secondsSince1900 - differenceBetweenEpochs;
			long msSince1970 = secondsSince1970 * 1000;
			Date time = new Date(msSince1970);
			return time;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(socket!=null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}
