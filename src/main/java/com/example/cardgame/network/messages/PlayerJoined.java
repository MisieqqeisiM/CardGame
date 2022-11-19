package com.example.cardgame.network.messages;

public record PlayerJoined(int id, String name) implements NetworkMessage{
}
