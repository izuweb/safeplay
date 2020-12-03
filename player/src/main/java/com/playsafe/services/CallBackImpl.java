package com.playsafe.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CallBackImpl implements CallBack {


    @Override
    public void fetchAndDisplayResult(Socket connection) throws IOException {
        DataOutputStream os = new DataOutputStream(connection.getOutputStream());
        new Thread(() -> {
            String result = null;
            try (BufferedReader clientReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                while ((result = clientReader.readLine()) != null) {
                    System.out.println(result);
                    /*os.writeUTF(result);
                    os.flush();*/
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
