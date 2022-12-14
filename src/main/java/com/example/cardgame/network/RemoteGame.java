package com.example.cardgame.network;

import com.example.cardgame.core.UnoGame;
import com.example.cardgame.network.messages.GameData;
import com.example.cardgame.network.messages.NetworkInfo;
import com.example.cardgame.network.messages.PlayerStatusUpdate;
import com.example.cardgame.network.players.Bot;
import com.example.cardgame.network.players.NetworkPlayer;
import com.example.cardgame.network.players.NetworkPlayerController;
import com.example.cardgame.network.players.RemotePlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class RemoteGame extends Thread {
    private final ServerSocket socket;
    List<NetworkPlayerController> playerControllers = new ArrayList<>();
    NetworkControls controls;

    UnoGame game;
    private class ServerThread extends Thread {

        @Override
        public void run() {
            while(true) {
                try {
                    var player = new RemotePlayer(socket.accept(), RemoteGame.this);
                    addPlayer(player);

                } catch (IOException e) {
                    throw new RuntimeException("server not working >:(");
                }
            }
        }
    }
    private void addPlayer(NetworkPlayer player) {
        player.setOnGreet(() -> {
            var controller = playerControllers.stream().filter(
                    playerController -> playerController.getInfo().connectionState() == ConnectionState.NONE
            ).findAny();
            if(controller.isEmpty()) return;
            controller.get().attach(player);
            player.send(new NetworkInfo(playerControllers.stream().map(NetworkPlayerController::getInfo).toList()));
            playerControllers.forEach(client -> client.send(new PlayerStatusUpdate(controller.get().getId(), player.getInfo())));
        });
        player.start();
    }

    public RemoteGame(int port) throws IOException {
        controls = new NetworkControls() {
            @Override
            public void addBot() {
                addPlayer(new Bot());
            }

            @Override
            public void removeBot() {
                var controller = playerControllers.stream().filter(
                        playerController -> playerController.getInfo().connectionState() == ConnectionState.LOCAL
                ).reduce((first, second) -> second);
                controller.ifPresent(NetworkPlayerController::disconnect);
            }

            @Override
            public void reset() {
                int size = 4;
                game = new UnoGame(4);
                playerControllers.forEach((controller) -> game.join(controller.getPlayer()));
                var networkInfo = playerControllers.stream().map(NetworkPlayerController::getInfo).toList();
                playerControllers.forEach((controller) -> controller.send(new NetworkInfo(networkInfo)));

            }
        };
        int size = 4;
        game = new UnoGame(size);
        for(int i = 0; i < size; i++) {
            var playerController = new NetworkPlayerController(controls) {
                @Override
                protected void onDisconnect() {
                    playerControllers.forEach(client -> client.send(new PlayerStatusUpdate(getId(), getInfo())));
                }
            };
            game.join(playerController.getPlayer());
            playerControllers.add(playerController);
        }
        socket = new ServerSocket(port);
        new ServerThread().start();
    }
}
