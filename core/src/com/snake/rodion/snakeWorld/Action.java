package com.snake.rodion.snakeWorld;


public enum Action {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,
    CRASH,
    EAT,
    INACTIVE;

    private final int value;

    Action() {
        this.value = 1 << this.ordinal();
    }

    public int getValue() {
        return value;
    }
}

