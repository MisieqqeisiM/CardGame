package com.example.cardgame.network.messages;

import com.example.cardgame.network.PlayerInfo;
import java.util.List;

public record NetworkInfo(List<PlayerInfo> info) implements NetworkMessage { }
