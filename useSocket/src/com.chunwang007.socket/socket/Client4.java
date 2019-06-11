package com.chunwang007.socket.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client4 {

    private static Integer count=0;
    private static Socket socket = null;
    private static BufferedReader br = null;
    private static PrintWriter pw = null;
    private static Scanner in = new Scanner(System.in);
    private static int id=4;
    public static void main(String[] arg){
         new Client4().Client();
    }

    public void Client() {


        try {

            socket = new Socket("127.0.0.1", 8989);
            System.out.println(socket);
            pw=new PrintWriter(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true){
                if (count==0){
                    System.out.println("开始连接.....");
                    pw.write("From 000000004 To 000000000 :Connect request"+"\n");
                    pw.flush();
                    String brStr=br.readLine();
                    System.out.println("服务器:"+brStr);
                }
                if(count==1){
                    pw.write("From 000000004 To 000000001 :Chat request"+"\n");
                    pw.flush();
                    String brStr=br.readLine();
                    System.out.println("服务器:"+brStr);
                }

                if(count>1){

                    Client4Thread clientThread=new Client4Thread(socket);
                    Thread thread=new Thread(clientThread);
                    thread.start();

                    String pwStr = in.next();
                    //添加"\n"，告诉服务器数据已经传输结束了
                    pw.write("From 000000004 To 000000003 :" + pwStr + "\n");
                    pw.flush();

                }
                count++;
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("执行了吗？");
            pw.close();
            try {
                br.close();
                System.out.println("被关闭了");
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
