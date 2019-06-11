package com.chunwang007.socket.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ServerThread extends Thread {

    private  Socket socket=null;
    private static BufferedReader br=null;
    private static PrintWriter pw=null;
    private static Map<Integer,Socket> map=new HashMap<Integer, Socket>();
    private static Scanner in = new Scanner(System.in);
    private static String brStr=null;

    public ServerThread(Socket socket,Map<Integer,Socket> map){
        this.socket=socket;
        this.map=map;
    }

    @Override
    public void run() {

        try {


            while (true) {

                br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //有代码冗余的部位
                brStr = br.readLine();
//              发送方id
                String myIdStr = brStr.substring(5, 14);
                Integer myId = Integer.valueOf(myIdStr);
//              接收方id
                String frIdStr = brStr.substring(18, 27);
                Integer frId = Integer.valueOf(frIdStr);

                //接收到聊天请求，并回复ok
                if (frIdStr.equals("000000001")) {
                    System.out.println("允许用户 " + (int) myId + "开始聊天！");
                    pw = new PrintWriter(map.get(myId).getOutputStream());
                    pw.write("Allowed to chat!" + "\n");
                    pw.flush();
                }
                //找到相应客户端的id，根据id找到socket，随后进行发送
                if ((!(frIdStr.equals("000000000")))&&(!(frIdStr.equals("000000001")))) {
                    System.out.println("发送给：" + frId + "; 内容为：" + brStr.substring(29));
                    //写,因为服务器不会和服务器打招呼，所以map中的key不可能有服务器的id
                    Set<Integer> set = map.keySet();
                    for (Integer integer : set) {
                        if (frId == integer) {
//                      找到指定socket，进行转发
                            pw = new PrintWriter(map.get(frId).getOutputStream());
                            String pwStr = brStr.substring(29);
                            pw.write(myId + ":" + pwStr + "\n");
                            System.out.println("已发送...");
                            pw.flush();
                        }
                    }
                }
                //如果套接字已经关闭了，就关闭循环
                if (socket.isClosed()) {
                    System.out.println("看这里");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
                if (socket.isClosed()) {
                    System.out.println("socket被关闭了");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("socket被关闭了");
            }
        } finally {
            if (pw != null) {
                pw.close();
                System.out.println("pw被关闭了");
            }

            if (br != null) {
                try {
                    br.close();
                    System.out.println("br被关闭了");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
