package com.example.cardgame.network.messages;

import com.example.cardgame.network.PlayerInfo;

public record PlayerStatusUpdate(int id, PlayerInfo info) implements NetworkMessage{
}
