package com.example.cardgame.network.players;

import com.example.cardgame.core.PlayerControls;
import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.core.events.PlayerEvent;
import com.example.cardgame.network.Communicator;
import com.example.cardgame.network.ConnectionState;
import com.example.cardgame.network.NetworkControls;
import com.example.cardgame.network.PlayerInfo;
import com.example.cardgame.network.messages.*;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.function.Consumer;

public class RemotePlayer implements NetworkPlayer {
    Communicator communicator;
    PlayerControls controls;
    NetworkControls networkControls;
    public Runnable onGreet;
    public Runnable onQuit;
    boolean connected = false;
    String name;

    final Object sync;
    public RemotePlayer(Socket socket, Object sync) throws IOException {
        this.sync = sync;
        this.communicator = new Communicator(socket, () -> {
            if(onQuit != null)
                onQuit.run();
        });
        this.communicator.onMessage = this::update;
    }

    @Override
    public void start() {
        this.communicator.start();
    }

    @Override
    public void onEvent(PlayerEvent event) {
        communicator.send(new Event(event));
    }


    @Override
    public void connect(PlayerControls controls, PlayerState state, NetworkControls networkControls) {
        this.controls = controls;
        this.networkControls = networkControls;
        communicator.send(new GameData(state));
    }

    @Override
    public void send(NetworkMessage message) {
        communicator.send(message);
    }

    @Override
    public void setOnGreet(Runnable f) {
        onGreet = f;
    }

    @Override
    public void setOnQuit(Runnable f) {
        onQuit = f;
    }

    @Override
    public void disconnect() {
        communicator.disconnect();
    }

    @Override
    public PlayerInfo getInfo() {
        if(name == null)
            return new PlayerInfo(ConnectionState.CONNECTING, "");
        else
            return new PlayerInfo(ConnectionState.CONNECTED, name);
    }

    public void update(NetworkMessage message) {
        synchronized (sync) {
            if (message instanceof Greet) {
                connected = true;
                name = ((Greet) message).name();
                if (onGreet != null)
                    onGreet.run();
            } else if (message instanceof AddBot) {
                if(networkControls != null)
                    networkControls.addBot();
            } else if (message instanceof RemoveBot) {
                if(networkControls != null)
                    networkControls.removeBot();
            } else if (message instanceof Reset) {
                if(networkControls != null)
                    networkControls.reset();
            }
            if (message instanceof ActionMessage) {
                controls.playAction(((ActionMessage) message).action());
            }
        }
    }
}
