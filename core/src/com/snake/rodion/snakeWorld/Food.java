package com.snake.rodion.snakeWorld;

import java.util.Random;

public class Food {
    private Node position;
    private World world;

    public Food(Node position, World world) {
        this.position = position;
        this.world = world;
    }



    public Node getPosition() {
        return position;
    }

    public void setPosition(Node position) {
        this.position = position;
    }

    public void setRandomPosition() {
        do {
            Random randX = new Random();
            Random randY = new Random();
            position.setPosition(
                    randX.nextInt(world.getFrame().getDimension().getX() - 1) + world.getFrame().getPosition().getX(),
                    randY.nextInt(world.getFrame().getDimension().getY() - 1) + world.getFrame().getPosition().getY()
            );
        }
        while (!isThisPositionValid(position));
    }

    private boolean isThisPositionValid(Node position) {
        for (Node node : world.getSnake().getBody())
            if (position.isEqual(node))
                return false;
        return true;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
