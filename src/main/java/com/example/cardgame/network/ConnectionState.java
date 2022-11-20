package com.example.cardgame.network;

import java.io.Serializable;

public enum ConnectionState implements Serializable {
    LOCAL, CONNECTING, CONNECTED, NONE
}
