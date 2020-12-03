package com.playsafe.models;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class Player implements Serializable {
    private static final long serialVersionUID = 4278374291466592063L;
    private String name;
    private BigDecimal principal;
}
