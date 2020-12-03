package com.playsafe.dao;

import com.playsafe.models.Bet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum BetDao {

    INSTANCE;
    private final List<Bet> bets = new CopyOnWriteArrayList<>();
    private final List<Bet> wonBets = new CopyOnWriteArrayList<>();

    public Bet addBet(Bet bet) {
        bets.add(bet);
        return bet;
    }

    public synchronized List<Bet> getAllBets() {
        return this.bets;
    }

    public synchronized void clearBets() {
        for (Bet bet : bets) {
            bets.remove(bet);
        }
    }

    public synchronized List<Bet> getWonBets() {
        return wonBets;
    }

}
