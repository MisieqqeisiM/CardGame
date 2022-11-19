package com.example.cardgame.network;

import com.example.cardgame.core.UnoGame;
import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.core.utils.RandomPlayer;
import com.example.cardgame.network.messages.PlayerJoined;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class RemoteGame extends Thread {
    private final ServerSocket socket;
    List<RemotePlayer> clients = new ArrayList<>();

    UnoGame game;
    private class ServerThread extends Thread {

        @Override
        public void run() {
            while(true) {
                try {
                    var player = new RemotePlayer(socket.accept(), RemoteGame.this);
                    synchronized (RemoteGame.this) {
                        player.onGreet = (greet) -> {
                            game.join(player);
                            clients.forEach(client -> client.send(new PlayerJoined(player.getId(), greet.name())));
                            clients.add(player);
                        };
                        player.start();
                    }

                } catch (IOException e) {
                    throw new RuntimeException("server not working >:(");
                }
            }
        }
    }

    public RemoteGame(int port) throws IOException {
        game = new UnoGame(4);
        var first_player = new RandomPlayer();
        game.join(first_player);
        game.join(new RandomPlayer());
        game.join(new RandomPlayer());
        first_player.eventNotify();
        socket = new ServerSocket(port);
        new ServerThread().start();
    }

}
