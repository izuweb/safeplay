package com.playsafe;

import com.playsafe.services.ClientService;
import com.playsafe.services.ClientServiceImpl;

public class Client {

    public static void main(String[] args) {
        if (!(args.length ==1)){
            throw new IllegalArgumentException("Missing  a parameter(port) for the client");
        }else{
            int port  = Integer.parseInt(args[0].trim());
            ClientService clientService = new ClientServiceImpl(port);
            new Thread(clientService).start();

        }
    }
}
