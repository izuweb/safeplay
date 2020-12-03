package com.playsafe.services;

public interface GameService {
    void start();
    void play();
    void addBetHandler(BetHandler handler);
    void removeBetHandler(BetHandler handler);
    void notifyHandlers();
    void setWiningNum(int num);

}
