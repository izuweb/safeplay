package com.playsafe.services;

import com.playsafe.models.Bet;
import com.playsafe.models.Bet.Payout;

import java.util.List;
import java.util.stream.Collectors;

public class EvenBetResultHandler implements BetResultHandler {

    @Override
    public List<Bet> handle(List<Bet> allBets, int winingNumber) {
        return allBets.stream().filter(bet -> bet.getSlot().equalsIgnoreCase("EVEN"))
                .map(b -> Bet.builder()
                        .player(b.getPlayer())
                        .slot(b.getSlot())
                        .amount(b.getAmount())
                        .payout(b.getAmount() * Payout.EVEN.getAmount())
                        .outcome(Bet.Outcome.WIN).build())
                .collect(Collectors.toList());

    }

}
