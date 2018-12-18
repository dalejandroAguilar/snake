package com.snake.rodion.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.snake.rodion.snakeWorld.Direction;
import com.snake.rodion.snakeWorld.Node;
import com.snake.rodion.snakeWorld.Snake;

import static com.snake.rodion.Constants.PAD_BOTTOM;
import static com.snake.rodion.Constants.PAD_LEFT;
import static com.snake.rodion.Constants.PLANK_CONSTANT;

public class SnakeEntity extends Actor implements Disposable {
    private Snake snake;
    private Texture headTexture;
    private Texture bodyTexture;
    private Texture cornerTexture;
    private Texture tailTexture;

    public SnakeEntity(Snake snake) {
        this.snake = snake;
        headTexture = new Texture(Gdx.files.internal("head.png"));
        bodyTexture = new Texture(Gdx.files.internal("body.png"));
        cornerTexture = new Texture(Gdx.files.internal("corner.png"));
        tailTexture = new Texture(Gdx.files.internal("tail.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float rotation = 0f;
        // head
        rotation = 0f;
        switch (snake.getWorld().getDirection()){
            case RIGHT:
                break;
            case UP:
                rotation = 90;
                break;
            case LEFT:
                rotation = 180;
                break;
            case DOWN:
                rotation = 270;
                break;
        }
        batch.draw(new TextureRegion(headTexture), snake.getHead().getX() * PLANK_CONSTANT +PAD_LEFT, snake.getHead().getY() * PLANK_CONSTANT +PAD_BOTTOM, bodyTexture.getWidth() / 2f, bodyTexture.getHeight() / 2f, bodyTexture.getWidth(), bodyTexture.getHeight() , 1, 1, rotation);
        // tail
        rotation = 0f;
        switch (snake.getForwardRelativePosition(0)){
            case RIGHT:
                break;
            case UP:
                rotation = 90;
                break;
            case LEFT:
                rotation = 180;
                break;
            case DOWN:
                rotation = 270;
                break;
        }
        batch.draw(new TextureRegion(tailTexture), snake.getTail().getX() * PLANK_CONSTANT +PAD_LEFT, snake.getTail().getY() * PLANK_CONSTANT +PAD_BOTTOM, bodyTexture.getWidth() / 2f, bodyTexture.getHeight() / 2f, bodyTexture.getWidth(), bodyTexture.getHeight(), 1, 1, rotation);
        // body
        for (int i = 1; i < snake.getSize() - 1; i++) {
            TextureRegion bodyDummyTexture = new TextureRegion(bodyTexture);
            rotation = 0f;
            Direction backwardPosition = snake.getBackwardRelativePosition(i);
            Direction forwardPosition = snake.getForwardRelativePosition(i);
            // ---
            switch (backwardPosition){
                case LEFT:
                    switch (forwardPosition){
                        case RIGHT:
                            break;
                        case UP:
                            bodyDummyTexture = new TextureRegion(cornerTexture);
                            rotation = 180;
                            break;
                        case DOWN:
                            bodyDummyTexture = new TextureRegion(cornerTexture);
                            rotation = 270;
                            break;
                    }
                    break;
                case RIGHT:
                    switch (forwardPosition){
                        case LEFT:
                            break;
                        case UP:
                            bodyDummyTexture = new TextureRegion(cornerTexture);
                            rotation = 90;
                            break;
                        case DOWN:
                            bodyDummyTexture = new TextureRegion(cornerTexture);
                            break;
                    }
                    break;
                case UP:
                    switch (forwardPosition){
                        case LEFT:
                            bodyDummyTexture = new TextureRegion(cornerTexture);
                            rotation = 180;
                            break;
                        case RIGHT:
                            bodyDummyTexture = new TextureRegion(cornerTexture);
                            rotation = 90;
                            break;
                        case DOWN:
                            rotation = 90;
                            break;
                    }
                    break;
                case DOWN:
                    switch (forwardPosition){
                        case LEFT:
                            bodyDummyTexture = new TextureRegion(cornerTexture);
                            rotation = 270;
                            break;
                        case RIGHT:
                            bodyDummyTexture = new TextureRegion(cornerTexture);
                            break;
                        case UP:
                            rotation = 90;
                            break;
                    }
                    break;
            }


            batch.draw(bodyDummyTexture, snake.get(i).getX() * PLANK_CONSTANT + PAD_LEFT, snake.get(i).getY() * PLANK_CONSTANT +PAD_BOTTOM, bodyTexture.getWidth() / 2f, bodyTexture.getHeight() / 2f, bodyTexture.getWidth(), bodyTexture.getHeight(), 1, 1, rotation);
        }
    }

    @Override
    public void dispose() {
        headTexture.dispose();
        bodyTexture.dispose();
        cornerTexture.dispose();
        tailTexture.dispose();
    }
}
