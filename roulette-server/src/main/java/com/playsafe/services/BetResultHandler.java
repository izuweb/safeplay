package com.playsafe.services;

import com.playsafe.models.Bet;

import java.util.List;

public interface BetResultHandler {
	
	List<Bet> handle(List<Bet> allBets, int winingNumber);

}
