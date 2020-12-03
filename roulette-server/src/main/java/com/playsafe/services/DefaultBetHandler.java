package com.playsafe.services;

import com.playsafe.converter.Converter;
import com.playsafe.exceptions.BetParseException;
import com.playsafe.models.Bet;
import lombok.Data;

import java.io.*;
import java.net.Socket;

/**
 * @author Izu Omenife
 */
@Data
public class DefaultBetHandler implements BetHandler<String> {
    private GameService gameService;
    private Socket socket;
    boolean playing = true;
    private BettingService bettingService;
    private DataOutputStream outputStream;

    public DefaultBetHandler(GameService gameService, Socket socket, BettingService bettingService) throws IOException {
        this.gameService = gameService;
        this.socket = socket;
        this.bettingService = bettingService;
        outputStream = new DataOutputStream(socket.getOutputStream());
        gameService.addBetHandler(this);
    }

    public void processBet(String msg) throws BetParseException {
        if (msg.equalsIgnoreCase("bye")){
            // Close the socket and stop accepting bets
            playing = false;
            gameService.removeBetHandler(this);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            convertAndSaveBet(msg);
        }
    }

    private void convertAndSaveBet(String betString) throws BetParseException {
        Bet bet = bettingService.placeBet(betString);
        System.out.println(bet);
        System.out.println(String.format("Number of bets placed: %d",bettingService.findAll().size()));
    }

    @Override
    public void update(int winingNumber) {
        // do nothing for now. Result will be displayed by one screen.

    }


    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            while (playing){
                String betString = reader.readLine().trim();
                processBet(betString);
            }
        }catch (Exception e){
            System.out.println("Error ");
            playing = false;
            gameService.removeBetHandler(this);
        }
    }
}
