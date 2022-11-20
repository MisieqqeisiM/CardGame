package com.example.cardgame.network.players;

import com.example.cardgame.core.PlayerControls;
import com.example.cardgame.core.PlayerState;
import com.example.cardgame.core.events.PlayerEvent;
import com.example.cardgame.network.NetworkControls;
import com.example.cardgame.network.PlayerInfo;
import com.example.cardgame.network.messages.NetworkMessage;

public interface NetworkPlayer {
    void onEvent(PlayerEvent event);

    void connect(PlayerControls controls, PlayerState state, NetworkControls networkControls);

    void send(NetworkMessage message);
    void setOnGreet(Runnable f);
    void setOnQuit(Runnable f);
    void disconnect();
    void start();
    PlayerInfo getInfo();
}
