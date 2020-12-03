package com.playsafe.services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameServiceImpl implements GameService {
    private static final int THREAD_POOL_SIZE = 3;
    private static final long DELAY = 30;
    private Set<BetHandler> betHandlers;
    private int winingNum;
    private final BettingService bettingService;
    private int port;

    public GameServiceImpl(BettingService bettingService, int port) {
        this.bettingService = bettingService;
        this.port = port;
        betHandlers = new HashSet<>();
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(port, Integer.MAX_VALUE, InetAddress.getLocalHost())) {
            Executor executor = Executors.newWorkStealingPool();
            printServer(server);
            play();
            while (true) {
                Socket connection = server.accept();
                System.out.println(String.format("Client connected with local port : %s and port: %s", connection.getLocalPort(), connection.getPort()));
                executor.execute(new DefaultBetHandler(this, connection, bettingService));

            }
        } catch (IOException ignored) {
        }

    }

    private void printServer(ServerSocket server) throws SocketException {
        System.out.println("The server started successfully  and ready to accept requests on " + server.getLocalPort() + " " + server.getReceiveBufferSize());
        String hostAddress = server.getInetAddress().getHostAddress();
        String hostName = server.getInetAddress().getHostName();
        System.out.println(String.format("HostAddress %s HostName %s", hostAddress, hostName));
    }

    @Override
    public void play() {
        scheduleGame();
    }

    private synchronized void scheduleGame() {
        ScheduledExecutorService sexec = Executors.newSingleThreadScheduledExecutor();
        sexec.scheduleWithFixedDelay(() -> {
            final int winingNumber = spin();
            this.setWiningNum(winingNumber);
            bettingService.deleteAll();
        }, DELAY,DELAY,  TimeUnit.SECONDS);
    }

    private int spin() {
        return (int) (Math.random() * (36)) + 1;
    }

    public void addBetHandler(BetHandler handler) {
        betHandlers.add(handler);
    }

    public void removeBetHandler(BetHandler handler) {
        betHandlers.remove(handler);
    }

    public synchronized void notifyHandlers() {
        if (betHandlers.size() > 0) {
            for (BetHandler betHandler : betHandlers) {
                try {
                    betHandler.update(winingNum);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No handler available");
        }
    }

    @Override
    public void setWiningNum(int num) {
        this.winingNum = num;
        GameResultService resultService = new GameResultServiceImpl();
        String result = resultService.getResult(winingNum, bettingService);
        System.out.println(result);
        notifyHandlers();
    }
}
