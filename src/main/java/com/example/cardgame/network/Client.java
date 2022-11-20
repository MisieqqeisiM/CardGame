package com.example.cardgame.network;

import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.network.messages.*;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class Client {
    private final Communicator communicator;

    NetworkUnoPlayer player;

    public Client(String ip, int port, String name) throws IOException {
        var socket = new Socket(ip, port);
        communicator = new Communicator(socket, () -> {
            System.out.println("lost connection");
        });
        communicator.start();
        communicator.send(new Greet(name));
    }

    public void setPlayer(NetworkUnoPlayer player, PlayerState state) {
        this.player = player;
        this.player.load(state, action -> communicator.send(new ActionMessage(action)));
        this.player.load(new NetworkControls() {
            @Override
            public void addBot() {
                communicator.send(new AddBot());
            }

            @Override
            public void removeBot() {
                communicator.send(new RemoveBot());
            }

            @Override
            public void reset() {
                communicator.send(new Reset());
            }
        });
    }
    public abstract void onReset(GameData gameData);

    public void update() {
        NetworkMessage message;
        while((message = communicator.getMessage()) != null) {
            if(message instanceof NetworkInfo) {
                this.player.onNetworkInfo((NetworkInfo) message);
            } else if(message instanceof GameData) {
                onReset((GameData) message);
            } else if(message instanceof PlayerStatusUpdate) {
                this.player.onPlayerJoined((PlayerStatusUpdate) message);
            } else if(message instanceof Event) {
                System.out.println(message);
                this.player.enqueue(((Event) message).event());
                this.player.eventNotify();
            }
        }
    }


    public void disconnect() {
        communicator.disconnect();
    }
}
