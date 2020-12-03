package com.playsafe;

import com.playsafe.services.BettingService;
import com.playsafe.services.BettingServiceImpl;
import com.playsafe.services.GameService;
import com.playsafe.services.GameServiceImpl;

public class RouletteServer {

    public static void main(String[] args) {
        if (!(args.length == 1)){
            throw new IllegalArgumentException("port number is missing");
        }else{
            int port = Integer.parseInt(args[0].trim());
            BettingService bettingService = new BettingServiceImpl();
            GameService gameService = new GameServiceImpl(bettingService,port);
            gameService.start();
        }


    }
}
