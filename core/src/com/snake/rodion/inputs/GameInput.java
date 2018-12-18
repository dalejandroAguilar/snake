package com.snake.rodion.inputs;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.snake.rodion.screens.GameScreen;

import static com.snake.rodion.snakeWorld.Direction.DOWN;
import static com.snake.rodion.snakeWorld.Direction.LEFT;
import static com.snake.rodion.snakeWorld.Direction.RIGHT;
import static com.snake.rodion.snakeWorld.Direction.UP;

public class GameInput implements GestureDetector.GestureListener {
    private GameScreen gameScreen;
    public GameInput(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (Math.abs(velocityX) > Math.abs(velocityY))
            if (velocityX > 0)
                gameScreen.onAction(RIGHT);
            else
                gameScreen.onAction(LEFT);
        else if (Math.abs(velocityX) < Math.abs(velocityY))
            if (velocityY > 0)
                gameScreen.onAction(UP);
            else
                gameScreen.onAction(DOWN);
        else
            return false;
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {
    }
}
