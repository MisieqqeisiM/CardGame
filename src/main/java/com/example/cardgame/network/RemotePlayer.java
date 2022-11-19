package com.example.cardgame.network;

import com.example.cardgame.core.UnoGame;
import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.network.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class RemotePlayer extends UnoPlayer {
    Communicator communicator;
    public Consumer<Greet> onGreet;
    final Object sync;
    public RemotePlayer(Socket socket, Object sync) throws IOException {
        this.sync = sync;
        this.communicator = new Communicator(socket);
        this.communicator.onMessage = this::update;
    }

    public void start() {
        this.communicator.start();
    }

    public int getId() {
        return state.myId;
    }

    @Override
    public void eventNotify() {
        var event = nextEvent();
        communicator.send(new Event(event));
    }

    public void send(NetworkMessage message) {
        communicator.send(message);
    }

    public void update(NetworkMessage message) {
        synchronized (sync) {
            if (message instanceof Greet) {
                if (onGreet != null)
                    onGreet.accept((Greet) message);
            }
            if (message instanceof ActionMessage) {
                playAction(((ActionMessage) message).action());
            }
        }
    }

    @Override
    protected void onLoad() {
        communicator.send(new GameData(state));
    }
}
