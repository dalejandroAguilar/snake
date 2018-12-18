package com.snake.rodion.snakeWorld;

import java.util.ArrayList;
import java.util.EnumSet;

public class World {
    private Frame frame;
    private Snake snake;
    private Food food;
    private Direction direction;

    public World(ArrayList<Node> initSnakeBody, Frame frame, Node initFoodPosition, Direction direction) {
        this.frame = frame;
        //this.direction = direction;
        food = new Food(initFoodPosition, this);
        this.direction = direction;
        snake = new Snake(initSnakeBody, this, direction);
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Direction move(Direction direction) {
        //snake.setDirection(direction);
        return snake.move(direction);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        snake.setDirection(direction);
    }

    public Direction getDirection() {
        return direction;
    }

    public EnumSet<Action> step() {
        direction = snake.getDirection();
        return snake.step();
    }
}
