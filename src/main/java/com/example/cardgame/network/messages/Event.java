package com.example.cardgame.network.messages;

import com.example.cardgame.core.events.PlayerEvent;

public record Event(PlayerEvent event) implements NetworkMessage{
}
