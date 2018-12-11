package com.snake.rodion.snakeWorld;

import java.util.ArrayList;

import static com.snake.rodion.snakeWorld.Direction.*;

public class Snake {
    private ArrayList<Node> body;
    private World world;
    private Direction direction;
    private boolean vitals;

    public Snake(ArrayList<Node> body, World world, Direction direction) {
        this.body = body;
        this.world = world;
        this.direction = direction;
        vitals = true;
    }


    public ArrayList<Node> getBody() {
        return body;
    }

    public void setBody(ArrayList<Node> body) {
        this.body = body;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        Node dummyHead = new Node();
        Node displacement = new Node();
        switch (direction) {
            case UP:
                displacement.setPosition(0, 1);
                break;
            case DOWN:
                displacement.setPosition(0, -1);
                break;
            case LEFT:
                displacement.setPosition(-1, 0);
                break;
            case RIGHT:
                displacement.setPosition(1, 0);
                break;
        }
        dummyHead.setPosition(getHead());
        dummyHead.increase(displacement);
        if (thereIsNoSnake(dummyHead))
            this.direction = direction;
    }

    public Node getHead() {
        return body.get(body.size() - 1);
    }

    public Node getTail() {
        return body.get(0);
    }

    public Node get(int i) {
        return body.get(i);
    }

    public int getSize() {
        return body.size();
    }

    private Node getBackwardGap(int i) {
        return new Node(body.get(i - 1).getX() - body.get(i).getX(), body.get(i-1).getY() - body.get(i ).getY());
    }

    private Node getFordwardGap(int i) {
        return new Node(body.get(i + 1).getX() - body.get(i).getX(), body.get(i + 1).getY() - body.get(i).getY());
    }

    public Direction getBackwardRelativePosition(int i){
        int gapX = getBackwardGap(i).getX();
        int gapY = getBackwardGap(i).getY();
        if(gapX == -1)
            return LEFT;
        if(gapX == 1)
            return RIGHT;
        if(gapY == -1)
            return DOWN;
        if(gapY == 1)
            return UP;
        return ANYWHERE;
    }

    public Direction getForwardRelativePosition(int i){
        int gapX = getFordwardGap(i).getX();
        int gapY = getFordwardGap(i).getY();
        if(gapX == -1)
            return LEFT;
        if(gapX == 1)
            return RIGHT;
        if(gapY == -1)
            return DOWN;
        if(gapY == 1)
            return UP;
        return ANYWHERE;
    }

    public void step() {
        Node displacement = new Node();
        Node dummyHead = new Node();
        switch (direction) {
            case UP:
                displacement.setPosition(0, 1);
                break;
            case DOWN:
                displacement.setPosition(0, -1);
                break;
            case LEFT:
                displacement.setPosition(-1, 0);
                break;
            case RIGHT:
                displacement.setPosition(1, 0);
                break;
        }
        dummyHead.setPosition(getHead());
        dummyHead.increase(displacement);
        if (thereIsNoCollisions(dummyHead)) { // no hay colision
            if (dummyHead.isEqual(world.getFood().getPosition())) { // mmm he comido una manzana: la cola se queda ahí
                body.add(new Node()); // pero snake crece
                world.getFood().setRandomPosition(); // FIXME: snake crea su propio alimento!!!!
            } else {  // snake no crece pero se desplaza, los lugares anteriores ocupan los siguientes
                for (int i = 0; i < body.size() - 1; i++) {
                    body.get(i).setPosition(body.get(i + 1));
                }
            }
            getHead().setPosition(dummyHead);
        } else
            vitals = false;
    }

    private boolean checkBoundaries(Node node) { // el movimiento se da dentro de las paredes del frame
        if (node.getX() < world.getFrame().getFirstCorner().getX())
            return false;
        if (node.getY() < world.getFrame().getFirstCorner().getY())
            return false;
        if (node.getX() > world.getFrame().getLastCorner().getX())
            return false;
        if (node.getY() > world.getFrame().getLastCorner().getY())
            return false;
        return true;
    }

    private boolean thereIsNoSnake(Node position) { // no hay colision entre snake
        for (Node node : body)
            if (position.isEqual(node))
                return false;
        return true;
    }

    private boolean thereIsNoCollisions(Node node){
        if (!checkBoundaries(node))
            return false;
        if (!thereIsNoSnake(node))
            return false;
        return true;
    }
}
