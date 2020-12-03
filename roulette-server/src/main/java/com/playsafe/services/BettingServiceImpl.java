package com.playsafe.services;

import com.playsafe.converter.BetConverter;
import com.playsafe.converter.Converter;
import com.playsafe.dao.BetDao;
import com.playsafe.exceptions.BetParseException;
import com.playsafe.models.Bet;

import java.util.List;

public class BettingServiceImpl implements BettingService {

    private final BetDao betDao = BetDao.INSTANCE;
    private final Converter<String,Bet> converter = new BetConverter();

    @Override
    public Bet placeBet(String betString) throws BetParseException {
        Bet bet = converter.convert(betString);
        return betDao.addBet(bet);
    }

    @Override
    public List<Bet> findAll() {
        return betDao.getAllBets();
    }

    @Override
    public void deleteAll() {
        betDao.clearBets();
    }
}
