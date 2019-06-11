package com.chunwang007.socket.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerTry {

    public static void main(String[] arg){
        new ServerTry().Server();
    }

    //获取来自客户端的Socket
    public void Server() {

        ServerSocket serverSocket=null;
        Socket socket=null;
        Map<Integer,Socket> map=new HashMap<Integer, Socket>();
        BufferedReader br=null;
        PrintWriter pw=null;
        try {
            serverSocket = new ServerSocket(8989);

            while(true) {

                //在等待其他人的同时转发已到达的消息
                socket = serverSocket.accept();

                //获取一下发送的id，并将其存入到map集合当中
                br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String brStr = br.readLine();
                String myIdStr = brStr.substring(5, 14);
                Integer myId = Integer.valueOf(myIdStr);
                map.put(myId, socket);
//              当key相同时，map会自动更新最新传入的value
                if (brStr.substring(18, 27).equals("000000000")) {
                    System.out.println("用户 " + (int) myId + "已上线！");
                    pw = new PrintWriter(map.get(myId).getOutputStream());
                    pw.write("Allowed to connect!" + "\n");
                    pw.flush();
                }



                if (socket!=null) {
                    ServerThread serverThread = new ServerThread(socket,map);
                    Thread thread = new Thread(serverThread);
                    thread.start();
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /*
     根据传入的Id，创建一个Map<Integer,Socket> map;其中Socket存储的是传入的Socket，
     Integer是其Id，根据 From+发送方Id+TO+接收方Id的原则，找到相对应的Id，然后将数据发送给接收方，
     使用线程进行并发式处理，
     存在的问题：无法存储客户端传入的Socket，需要解决
     */

}
