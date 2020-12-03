package com.playsafe.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bet implements Serializable {
    public enum Outcome {
        WIN, LOSE
    }

    public enum Payout {
        EVEN(36.0), ODD(1.0), DEFAULT(2.0),NO(0.0);
        private double amount;

        private Payout(double amount) {
            this.amount = amount;
        }

        public double getAmount() {
            return amount;
        }
    }

    private static final long serialVersionUID = -6871674729523300437L;
    private Player player;
    private double amount;
    private String slot;
    private Double payout;
    private Outcome outcome;


}
