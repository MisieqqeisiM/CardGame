package com.example.cardgame.network;

import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.network.messages.*;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final Communicator communicator;

    final NetworkUnoPlayer player;

    public Client(String ip, int port, String name, NetworkUnoPlayer player) throws IOException {
        var socket = new Socket(ip, port);
        this.player = player;
        communicator = new Communicator(socket, () -> {
            System.out.println("lost connection");
        });
        communicator.start();
        communicator.send(new Greet(name));
    }

    public void update() {
        NetworkMessage message;
        while((message = communicator.getMessage()) != null) {
            if(message instanceof NetworkInfo) {
                this.player.onNetworkInfo((NetworkInfo) message);
            } else if(message instanceof GameData) {
                this.player.load(((GameData) message).state(), action -> communicator.send(new ActionMessage(action)));
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
