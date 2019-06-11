package com.chunwang007.socket.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client4Thread extends Thread{

    Socket socket=null;
    BufferedReader brThread4=null;
    public Client4Thread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {

        try {

            brThread4 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(brThread4.readLine());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
