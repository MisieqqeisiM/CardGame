package com.example.cardgame.network;

import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.network.messages.*;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private final Communicator communicator;

    final UnoPlayer player;

    public Client(String ip, int port, String name, UnoPlayer player) throws IOException {
        var socket = new Socket(ip, port);
        this.player = player;
        communicator = new Communicator(socket);
        communicator.start();
        communicator.send(new Greet(name));
    }


    public void update() {
        NetworkMessage message;
        while((message = communicator.getMessage()) != null) {
            if(message instanceof GameData) {
                this.player.load(((GameData) message).state(), action -> communicator.send(new ActionMessage(action)));
            } else if(message instanceof Event) {
                this.player.enqueue(((Event) message).event());
                this.player.eventNotify();
            }
        }
    }


}
