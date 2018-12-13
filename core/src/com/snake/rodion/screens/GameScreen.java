package com.snake.rodion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.snake.rodion.MainGame;
import com.snake.rodion.entities.FoodEntity;
import com.snake.rodion.entities.SnakeEntity;
import com.snake.rodion.snakeWorld.Direction;
import com.snake.rodion.snakeWorld.Frame;
import com.snake.rodion.snakeWorld.Node;
import com.snake.rodion.snakeWorld.World;
import java.util.ArrayList;

import javax.print.DocFlavor;

import static com.snake.rodion.Constants.*;
import static com.snake.rodion.snakeWorld.Direction.*;

public class GameScreen extends BaseScreen implements GestureDetector.GestureListener {
    private Stage stage;
    private World world;
    private SnakeEntity snakeEntity;
    private FoodEntity foodEntity;
    private float elapsedTime;

    public GameScreen(final MainGame mainGame) {
        super(mainGame);
        stage = new Stage(new FitViewport(1280, 720));
        ArrayList<Node> initSnakeBody = new ArrayList<Node>();
        initSnakeBody.add(new Node(1,1));
        initSnakeBody.add(new Node(1,2));
        world = new World(initSnakeBody, new Frame(new Node(0,0), new Node(WIDTH, HEIGHT)), new Node(3,3));
        snakeEntity = new SnakeEntity(world.getSnake());
        foodEntity = new FoodEntity(world.getFood());
        stage.addActor(snakeEntity);
        stage.addActor(foodEntity);
        elapsedTime = 0.f;
        GestureDetector gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
    }

    public void onAction(Direction direction){
        switch (direction){
            case UP:
                world.setDirection(DOWN);
                break;
            case DOWN:
                world.setDirection(UP);
                break;
            case RIGHT:
                world.setDirection(RIGHT);
                break;
            case LEFT:
                world.setDirection(LEFT);
                break;
        }
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        if (elapsedTime > 0.25f ){
            elapsedTime = 0;
        }
        if (elapsedTime == 0)
            world.step();
        Gdx.gl.glClearColor(0.f, 1.f, 1.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        snakeEntity.dispose();
        foodEntity.dispose();
        stage.dispose();
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
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (Math.abs(velocityX) > Math.abs(velocityY))
            if (velocityX > 0)
                onAction(RIGHT);
            else
                onAction(LEFT);
        else if (Math.abs(velocityX) < Math.abs(velocityY))
            if (velocityY > 0)
                onAction(UP);
            else
                onAction(DOWN);
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