package com.example.cardgame.network.players;

import com.example.cardgame.core.PlayerControls;
import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.events.PlayerEvent;
import com.example.cardgame.core.utils.RandomPlayer;
import com.example.cardgame.network.ConnectionState;
import com.example.cardgame.network.PlayerInfo;
import com.example.cardgame.network.messages.NetworkMessage;

public class Bot implements NetworkPlayer{
    private final RandomPlayer player;
    private Runnable onGreet;
    private Runnable onQuit;
    public Bot() {
        player = new RandomPlayer();
    }
    @Override
    public void onEvent(PlayerEvent event) {
        player.enqueue(event);
        player.eventNotify();
    }

    @Override
    public void connect(PlayerControls controls, PlayerState state) {
        player.load(state, controls);
    }

    @Override
    public void send(NetworkMessage message) { }

    @Override
    public void setOnGreet(Runnable f) { onGreet = f; }

    @Override
    public void setOnQuit(Runnable f) {
        onQuit = f;
    }

    @Override
    public void disconnect() {
        if(onQuit != null)
            onQuit.run();
    }

    @Override
    public void start() {
        if(onGreet != null)
            onGreet.run();
    }

    @Override
    public PlayerInfo getInfo() {
        return new PlayerInfo(ConnectionState.LOCAL, "Bot");
    }
}
