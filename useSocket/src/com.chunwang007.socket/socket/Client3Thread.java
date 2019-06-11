package com.chunwang007.socket.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client3Thread extends Thread{

    Socket socket=null;
    BufferedReader brThread3=null;
    public Client3Thread(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {

        try {

            brThread3 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(brThread3.readLine());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
