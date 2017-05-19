package com.yiwei.paxAdmin.unitTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleHttpGet {
    public static final String SEQUENCE = "\r\n";

    public static void main(String[] args) throws UnknownHostException,
            IOException {
        String host = "www.baidu.com";
        Socket socket = new Socket(host, 80);
        OutputStream os = socket.getOutputStream();
        StringBuffer head = new StringBuffer();
        // 这些是必须的
        head.append("GET / HTTP/1.1" + SEQUENCE);
        head.append("Host:" + host + SEQUENCE + SEQUENCE);
        // 这些是可选的
//		head.append("Accept:text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		head.append("Accept-Language:zh-CN,zh;q=0.8");
//		head.append("User-Agent:Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        os.write(head.toString().getBytes());
        os.flush();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), "UTF-8"));
        String s = null;
        int contentLength = 0;
        StringBuffer headRes = new StringBuffer();
        do{
            s = reader.readLine();
            headRes.append(s);
            headRes.append("\n");
            if (s.startsWith("Content-Length")) {
                contentLength = Integer.parseInt(s.split(":")[1].trim());
                System.out.println("The website size is: " + contentLength);
            }
        }while(!s.equals(""));

        int totalCount = 0;
        byte[] buff = new byte[40960];
        StringBuffer contentRes = new StringBuffer();
        while (totalCount < contentLength) {
            int len = socket.getInputStream().read(buff);
            if(len != -1){
                totalCount += len;
                contentRes.append(new String(buff, 0, len, "UTF-8"));
            }else{
                //System.out.println("Connection error.");
                break;
            }

        }

        System.out.println("The website head:\r\n" + headRes);
        System.out.println("The website content:\r\n" + contentRes.toString());
        socket.close();
        FileWriter writer = new FileWriter("g:\\test.html");
        writer.write(contentRes.toString());
        writer.flush();
        writer.close();
    }
}