package com.yiwei.paxAdmin.unitTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class HTTPSClient {
	public static void main(String[] args) {
		int port = 443;
		String host = "pax-us.atlassian.net";
		
		SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		try{
			socket = (SSLSocket)factory.createSocket(host,port);
			String[] supported = socket.getSupportedCipherSuites();
			socket.setEnabledCipherSuites(supported);
			Writer out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			out.write("GET / HTTP1.1\r\n");
			out.write("Host:" + host + "\r\n\r\n");
			out.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String s;
			while(!(s = in.readLine()).equals("")){
				System.out.println(s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
