package com.snake.rodion.snakeWorld;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

import static com.snake.rodion.snakeWorld.Action.*;
import static com.snake.rodion.snakeWorld.Direction.*;

public class Snake {
    private ArrayList<Node> body;
    private World world;
    private boolean vitals;
    private Set<Action> actions;

    public Snake(ArrayList<Node> body, World world, Direction direction) {
        this.body = body;
        this.world = world;
        vitals = true;
        actions = EnumSet.of(INACTIVE);
        setDirection(direction);
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
        if (actions.contains(MOVE_UP)) {
            return UP;
        }
        if (actions.contains(MOVE_DOWN)) {
            return DOWN;
        }
        if (actions.contains(MOVE_LEFT)) {
            return LEFT;
        }
        if (actions.contains(MOVE_RIGHT)) {
            return RIGHT;
        }
        return ANYWHERE;
    }

    public Set<Action> setDirection(Direction direction) {
        Node dummyHead = new Node();
        Node displacement = new Node();
        Action dummyAction;
        dummyAction = INACTIVE;
        //Set action = EnumSet.of(Action.CRASH);
        switch (direction) {
            case UP:
                displacement.setPosition(0, 1);
                dummyAction = MOVE_UP;
                break;
            case DOWN:
                displacement.setPosition(0, -1);
                dummyAction = MOVE_DOWN;
                break;
            case LEFT:
                displacement.setPosition(-1, 0);
                dummyAction = MOVE_LEFT;
                break;
            case RIGHT:
                displacement.setPosition(1, 0);
                dummyAction = MOVE_RIGHT;
                break;
        }
        dummyHead.setPosition(getHead());
        dummyHead.increase(displacement);
        if (!dummyHead.isEqual(getNeck())) {
            actions = EnumSet.of(INACTIVE);
            actions.add(dummyAction);
            if (thereIsNoCollisions(dummyHead)) { // no hay colision
                if (dummyHead.isEqual(world.getFood().getPosition()))
                    actions.add(EAT);
            } else {
                actions.add(CRASH);
            }
        }
        return actions; //TODO: Sera?
    }

    public Node getHead() {
        return body.get(body.size() - 1);
    }

    public Node getNeck() {
        return body.get(body.size() - 2);
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
        return new Node(body.get(i - 1).getX() - body.get(i).getX(), body.get(i - 1).getY() - body.get(i).getY());
    }

    private Node getFordwardGap(int i) {
        return new Node(body.get(i + 1).getX() - body.get(i).getX(), body.get(i + 1).getY() - body.get(i).getY());
    }

    public Direction getBackwardRelativePosition(int i) {
        int gapX = getBackwardGap(i).getX();
        int gapY = getBackwardGap(i).getY();
        if (gapX == -1)
            return LEFT;
        if (gapX == 1)
            return RIGHT;
        if (gapY == -1)
            return DOWN;
        if (gapY == 1)
            return UP;
        return ANYWHERE;
    }

    public Direction getForwardRelativePosition(int i) {
        int gapX = getFordwardGap(i).getX();
        int gapY = getFordwardGap(i).getY();
        if (gapX == -1)
            return LEFT;
        if (gapX == 1)
            return RIGHT;
        if (gapY == -1)
            return DOWN;
        if (gapY == 1)
            return UP;
        return ANYWHERE;
    }

    public void step() {
        if (!actions.contains(CRASH)) {
            Node dummyHead = new Node();
            Node displacement = new Node();
            if (actions.contains(MOVE_UP)) {
                displacement.setPosition(0, 1);
            }
            if (actions.contains(MOVE_DOWN)) {
                displacement.setPosition(0, -1);
            }
            if (actions.contains(MOVE_LEFT)) {
                displacement.setPosition(-1, 0);
            }
            if (actions.contains(MOVE_RIGHT)) {
                displacement.setPosition(1, 0);
            }
            dummyHead.setPosition(getHead());
            dummyHead.increase(displacement);
            if (actions.contains(EAT)) {
                body.add(new Node());
                getHead().setPosition(dummyHead);
                world.getFood().setRandomPosition();
            } else {
                for (int i = 0; i < body.size() - 1; i++) {
                    body.get(i).setPosition(body.get(i + 1));
                }
                getHead().setPosition(dummyHead);
            }
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

    private boolean thereIsNoCollisions(Node node) {
        if (!checkBoundaries(node))
            return false;
        if (!thereIsNoSnake(node))
            return false;
        return true;
    }

    public boolean isVitals() {
        return vitals;
    }

    public void setVitals(boolean vitals) {
        this.vitals = vitals;
    }
}