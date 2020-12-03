package com.playsafe.services;

import java.io.IOException;
import java.net.Socket;

public interface CallBack {

    void fetchAndDisplayResult(Socket connection) throws IOException;
}
