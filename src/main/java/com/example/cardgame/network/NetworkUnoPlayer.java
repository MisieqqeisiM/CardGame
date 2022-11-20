package com.example.cardgame.network;

import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.network.messages.NetworkInfo;
import com.example.cardgame.network.messages.PlayerStatusUpdate;

public abstract class NetworkUnoPlayer extends UnoPlayer {
    public abstract void onNetworkInfo(NetworkInfo info);
    public abstract void onPlayerJoined(PlayerStatusUpdate player);

    public abstract void load(NetworkControls controls);
}
