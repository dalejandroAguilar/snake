package com.snake.rodion.snakeWorld;

public class Frame {
    private Node position;
    private Node dimension;

    public Frame(Node position, Node dimension) {
        this.position = position;
        this.dimension = dimension;
    }

    public Frame() {
        this.position = new Node();
        this.dimension = new Node();
    }

    public Node getPosition() {
        return position;
    }

    public void setPosition(Node position) {
        this.position = position;
    }

    public Node getDimension() {
        return dimension;
    }

    public void setDimension(Node dimension) {
        this.dimension = dimension;
    }

    public Node getFirstCorner() {
        return position;
    }

    public Node getLastCorner() {
        Node node = new Node(position.getX() + dimension.getX() - 1, position.getY() + dimension.getY() - 1);
        return node;
    }
}
