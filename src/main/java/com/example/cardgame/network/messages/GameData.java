package com.example.cardgame.network.messages;

import com.example.cardgame.core.PlayerState;

public record GameData(PlayerState state) implements NetworkMessage {
}
