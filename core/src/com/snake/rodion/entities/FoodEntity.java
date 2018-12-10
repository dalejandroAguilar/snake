package com.snake.rodion.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.snake.rodion.snakeWorld.Food;

import static com.snake.rodion.Constants.PLANK_CONSTANT;

public class FoodEntity extends Actor implements Disposable {
    private Food food;
    private Texture foodTexture;

    public FoodEntity(Food food) {
        this.food = food;
        foodTexture = new Texture(Gdx.files.internal("apple.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(foodTexture, food.getPosition().getX()* PLANK_CONSTANT, food.getPosition().getY()* PLANK_CONSTANT);
    }

    @Override
    public void dispose() {
        foodTexture.dispose();
    }
}
