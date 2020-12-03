package com.playsafe.services;

import com.playsafe.exceptions.BetParseException;
import com.playsafe.models.Bet;

import java.util.List;

public interface BettingService {

    Bet placeBet(String bet) throws BetParseException;

    List<Bet> findAll();

    void deleteAll();
}
