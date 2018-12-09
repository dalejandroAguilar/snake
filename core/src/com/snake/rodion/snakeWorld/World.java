package com.snake.rodion.snakeWorld;

import java.util.ArrayList;

import static com.snake.rodion.snakeWorld.Direction.RIGHT;

public class World {
    private Frame frame;
    private Snake snake;
    private Food food;

    public World(ArrayList<Node> initSnakeBody, Frame frame, Node initFoodPosition) {
        this.frame = frame;
        snake = new Snake(initSnakeBody, this, RIGHT);
        food = new Food(initFoodPosition, this);
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

    public void setDirection(Direction direction) {
        snake.setDirection(direction);
    }

    public void step(){
        snake.step();
    }
}
