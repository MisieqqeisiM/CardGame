package com.example.cardgame.core;

import com.example.cardgame.network.RemoteGame;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        new RemoteGame(3333).start();
    }
}
