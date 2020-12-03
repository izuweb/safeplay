package com.playsafe.services;

import com.playsafe.models.Bet;
import com.playsafe.models.Bet.Payout;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultBetResultHandler implements BetResultHandler {

    @Override
    public List<Bet> handle(List<Bet> allBets, int winingNumber) {
        return allBets.stream()
                .filter(bet -> !bet.getSlot().equalsIgnoreCase("EVEN") && !bet.getSlot().equalsIgnoreCase("ODD"))
                .filter(b -> Integer.parseInt(b.getSlot()) == winingNumber)
                .map(b -> Bet.builder()
                        .player(b.getPlayer())
                        .slot(b.getSlot())
                        .amount(b.getAmount())
                        .payout(b.getAmount() * Payout.DEFAULT.getAmount())
                        .outcome(Bet.Outcome.WIN).build()).collect(Collectors.toList());
    }

}
