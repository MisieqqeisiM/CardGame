package com.example.cardgame.network.players;

import com.example.cardgame.core.UnoPlayer;
import com.example.cardgame.network.ConnectionState;
import com.example.cardgame.network.NetworkControls;
import com.example.cardgame.network.PlayerInfo;
import com.example.cardgame.network.messages.NetworkMessage;

public abstract class NetworkPlayerController {
    private final UnoPlayer player;
    private NetworkPlayer networkPlayer;
    private final NetworkControls controls;
    public NetworkPlayerController(NetworkControls controls) {
        this.controls = controls;
        this.player = new UnoPlayer() {
            @Override
            public void eventNotify() {
                var event = nextEvent();
                if(networkPlayer != null)
                    networkPlayer.onEvent(event);
            }
            @Override
            protected void onLoad() {
                if(networkPlayer != null)
                    networkPlayer.connect(player::playAction, player.state.clone(), controls);
            }
        };
    }

    public PlayerInfo getInfo() {
        if(networkPlayer == null)
            return new PlayerInfo(ConnectionState.NONE,"");
        return networkPlayer.getInfo();
    }

    public void send(NetworkMessage message) {
        if(networkPlayer != null)
            networkPlayer.send(message);
    }
    public UnoPlayer getPlayer() {
        return player;
    }
    public void attach(NetworkPlayer networkPlayer) {
        this.networkPlayer = networkPlayer;
        networkPlayer.setOnQuit(()-> {
            this.networkPlayer = null;
            onDisconnect();
        });
        networkPlayer.connect(player::playAction, player.state.clone(), controls);
    }
    public void disconnect() {
        this.networkPlayer.disconnect();
    }
    protected abstract void onDisconnect();

    public int getId() {
        return player.state.myId;
    }
}
