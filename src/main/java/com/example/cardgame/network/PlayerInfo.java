package com.example.cardgame.network;

import java.io.Serializable;

public record PlayerInfo(ConnectionState connectionState, String name) implements Serializable { }
