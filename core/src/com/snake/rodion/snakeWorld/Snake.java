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
        this.direction = direction;
    }

    public Node getHead() {
        return body.get(body.size() - 1);
    }
    public Node getNeck() {
        return body.get(body.size() - 2);
    }

    public void step() {
        Node displacement = new Node();
        Node dummyHead = new Node();
        switch (direction) {
            case UP:
                displacement.setPosition(0,1);
                break;
            case DOWN:
                displacement.setPosition(0,-1);
                break;
            case LEFT:
                displacement.setPosition(-1,0);
                break;
            case RIGHT:
                displacement.setPosition(1,0);
                break;
        }
        dummyHead.setPosition(getHead());
        dummyHead.increase(displacement);
        if (checkBoundaries(dummyHead)) { // el movimiento se da dentro de las paredes del frame
            if (dummyHead.isEqual(world.getFood().getPosition())) { // mmm he comido una manzana: la cola se queda ahí
                body.add(new Node()); // pero snake crece
            } else {  // snake no crece pero se desplaza, los lugares anteriores ocupan los siguientes
                for (int i = 0; i < body.size() - 1; i++) {
                    body.get(i).setPosition(body.get(i+1));
                }
            }
            getHead().setPosition(dummyHead);
            world.getFood().setRandomPosition(); // FIXME: snake crea su propio alimento!!!!
        } else
            vitals = false;
    }

    private boolean checkBoundaries(Node node) {
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
}
