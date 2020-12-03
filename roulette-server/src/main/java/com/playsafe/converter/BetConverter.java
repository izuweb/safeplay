package com.playsafe.converter;

import com.playsafe.exceptions.BetParseException;
import com.playsafe.models.Bet.Payout;
import com.playsafe.models.Player;
import com.playsafe.models.Bet;

import static com.playsafe.models.Bet.Outcome.LOSE;

public class BetConverter implements Converter<String, Bet> {


    public Bet convert(String source) throws BetParseException {
        return parse(source);
    }

    private Bet parse(String line) throws BetParseException {
        String[] tokens = line.split("\\s+");
        int numTokens = tokens.length;
        if (!(numTokens== 3)){
            throw  new BetParseException(String.format("Expected %d tokens but found %d", 3,numTokens));
        }
        String playerName = tokens[0].trim();
        String betType = tokens[1].trim();
        double amount = Double.parseDouble(tokens[2].trim());
        validateNumericalBet(betType);
        return Bet.builder()
                .player(Player.builder().name(playerName).build())
                .amount(amount)
                .slot(betType)
                .outcome(LOSE)
                .payout(Payout.NO.getAmount())
                .build();


    }

    private void validateNumericalBet(String betType) throws BetParseException {
        if(!(betType.equalsIgnoreCase("EVEN")) && !(betType.equalsIgnoreCase("ODD")) ) {
            int num = Integer.parseInt(betType);
            if(num < 1 || num > 36) {
                throw new BetParseException("Your bet must be in the range of 1-36");
            }
        }
    }
}
