package com.playsafe.services;

import com.playsafe.models.Bet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameResultServiceImpl implements GameResultService {

    @Override
    public String getResult(int winingNumber, BettingService betService) {
        /*Number: 4
Player Bet Outcome Winnings
---
Tiki_Monkey 2 LOSE 0.0
Barbara EVEN WIN 6.0*/
        List<Bet> bets = getBets(betService.findAll(), winingNumber);
        StringBuilder builder = new StringBuilder();
        builder.append("Number: " + winingNumber)
                .append("\n").append("PLAYER").append("\t").append("BET").append("\t").append("Outcome").append("\t")
                .append("Winnings").append("\n");
        for (Bet bet : bets) {
            builder.append(bet.getPlayer().getName()).append("\t").append(bet.getSlot()).append("\t")
                    .append(bet.getOutcome().name()).append("\t").append(bet.getPayout()).append("\n");
        }
        return builder.toString();

    }

    private List<Bet> getBets(List<Bet> allBets, int winingNumber) {
        List<Bet> wonBets;
        if (winingNumber % 2 == 0) {
            wonBets = new EvenBetResultHandler().handle(allBets, winingNumber);
        } else if (winingNumber % 2 == 1) {
            wonBets = new OddBetResultHandler().handle(allBets, winingNumber);
        } else {
            wonBets = new DefaultBetResultHandler().handle(allBets, winingNumber);
        }


        return wonBets;
    }

    /*private List<Bet> getLoseBets(List<Bet> allBets, List<Bet> wonBets) {
        List<Bet> loseBets = new ArrayList<>();
        for (Bet bet : allBets) {
            if (!wonBets.contains(bet)) {
                loseBets.add(bet);
            }
        }
        return loseBets;
    }*/
}
