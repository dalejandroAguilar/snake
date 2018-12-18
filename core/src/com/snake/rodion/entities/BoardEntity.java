package com.snake.rodion.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import static com.snake.rodion.Constants.PAD_BOTTOM;
import static com.snake.rodion.Constants.PAD_LEFT;
import static com.snake.rodion.Constants.PLANK_CONSTANT;

public class BoardEntity extends Actor implements Disposable{
    Texture texture;
    public BoardEntity() {
        texture = new Texture(Gdx.files.internal("board.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX()+PAD_LEFT, getY()+PAD_BOTTOM);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
