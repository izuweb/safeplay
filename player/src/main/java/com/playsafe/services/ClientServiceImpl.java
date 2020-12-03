package com.playsafe.services;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientServiceImpl implements ClientService {

    private final int port;

    public ClientServiceImpl(int port) {
        this.port = port;
    }

    @Override
    public void play() {
        BufferedReader reader = null;
        try( Socket socket = new Socket(InetAddress.getLocalHost(),port)) {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Host address"+socket.getInetAddress().getHostAddress());
            while (true) {
                OutputStream outputStream = socket.getOutputStream();
                System.out.println("Please enter your name, Bet type and amount");
                String msg = reader.readLine();
                PrintWriter writer = new PrintWriter(outputStream, true);
                writer.println(msg);
            }
        }catch (IOException e){
             e.printStackTrace();
        }
        finally {
            try {
                if (reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void run() {
        play();
    }
}
