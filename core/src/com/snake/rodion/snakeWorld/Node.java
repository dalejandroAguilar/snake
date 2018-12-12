package com.snake.rodion.snakeWorld;

public class Node {
    private int x;
    private int y;

    public Node(int x, int y) {
        setPosition(x, y);
    }

    public Node() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public void setPosition(Node node) {
        setX(node.getX());
        setY(node.getY());
    }

    public void increase(Node node) {
        setX(x + node.getX());
        setY(y + node.getY());
    }

    public void decrease(Node node) {
        setX(x - node.getX());
        setY(y - node.getY());
    }

    public boolean isEqual(Node node) {
        if (this.x == node.getX() && this.y == node.getY())
            return true;
        return false;
    }
}
