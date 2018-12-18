package com.snake.rodion.snakeWorld;

import java.util.ArrayList;
import java.util.EnumSet;

import static com.snake.rodion.snakeWorld.Action.CRASH;
import static com.snake.rodion.snakeWorld.Action.EAT;
import static com.snake.rodion.snakeWorld.Action.INACTIVE;
import static com.snake.rodion.snakeWorld.Action.MOVE_DOWN;
import static com.snake.rodion.snakeWorld.Action.MOVE_LEFT;
import static com.snake.rodion.snakeWorld.Action.MOVE_RIGHT;
import static com.snake.rodion.snakeWorld.Action.MOVE_UP;
import static com.snake.rodion.snakeWorld.Direction.ANYWHERE;
import static com.snake.rodion.snakeWorld.Direction.DOWN;
import static com.snake.rodion.snakeWorld.Direction.LEFT;
import static com.snake.rodion.snakeWorld.Direction.RIGHT;
import static com.snake.rodion.snakeWorld.Direction.UP;

public class Snake {
    private ArrayList<Node> body;
    private World world;
    private boolean vitals;
    private Direction direction;

    public Snake(ArrayList<Node> body, World world, Direction direction) {
        this.body = body;
        this.world = world;
        this.direction = direction;
        vitals = true;
        move(direction);
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

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public Direction move(Direction direction) {
        Node dummyHead = new Node();
        Node displacement = new Node();
        Direction dummyAction;
        switch (direction) {
            case UP:
                displacement.setPosition(0, 1);
                dummyAction = UP;
                break;
            case DOWN:
                displacement.setPosition(0, -1);
                dummyAction = DOWN;
                break;
            case LEFT:
                displacement.setPosition(-1, 0);
                dummyAction = LEFT;
                break;
            case RIGHT:
                displacement.setPosition(1, 0);
                dummyAction = RIGHT;
                break;
            default:
                dummyAction = ANYWHERE;
        }
        dummyHead.setPosition(getHead());
        dummyHead.increase(displacement);
        if (!dummyHead.isEqual(getNeck()) && (this.direction != direction)) {
            this.direction = direction;
            return dummyAction;
        }
        return ANYWHERE;
    }

    public EnumSet<Action> step() {
        Node dummyHead = new Node();
        Node displacement = new Node();
        EnumSet<Action> actions = EnumSet.noneOf(Action.class); //inicializacion
        Action dummyAction;
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
            default:
                dummyAction = INACTIVE;
        }
        dummyHead.setPosition(getHead());
        dummyHead.increase(displacement);
        if (thereIsNoCollisions(dummyHead)) {
            actions.add(dummyAction);
            if (dummyHead.isEqual(world.getFood().getPosition())) {
                actions.add(EAT);
                body.add(new Node());
                getHead().setPosition(dummyHead);
                world.getFood().setRandomPosition();
            } else {
                for (int i = 0; i < body.size() - 1; i++) {
                    body.get(i).setPosition(body.get(i + 1));
                }
                getHead().setPosition(dummyHead);
            }
        } else {
            actions.add(CRASH);
            vitals = false;
        }
        return actions;
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

    public boolean getVitals() {
        return vitals;
    }

    public void setVitals(boolean vitals) {
        this.vitals = vitals;
    }
}