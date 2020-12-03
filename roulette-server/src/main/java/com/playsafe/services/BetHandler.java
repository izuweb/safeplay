package com.playsafe.services;

import com.playsafe.exceptions.BetParseException;

import java.io.IOException;

public interface BetHandler<T>  extends Runnable{

  void processBet(T t) throws BetParseException;
  void update(int winingNumber) throws IOException;
}
