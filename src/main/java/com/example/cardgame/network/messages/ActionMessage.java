package com.example.cardgame.network.messages;

import com.example.cardgame.core.actions.Action;

public record ActionMessage(Action action) implements NetworkMessage{ }
