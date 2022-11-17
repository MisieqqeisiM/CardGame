package com.example.cardgame.core.utils;

public enum Direction {
    LEFT, RIGHT;

    public Direction other() {
        return switch(this) {
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
