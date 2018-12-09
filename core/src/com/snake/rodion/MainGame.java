package com.snake.rodion;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snake.rodion.snakeWorld.Direction;
import com.snake.rodion.snakeWorld.Food;
import com.snake.rodion.snakeWorld.Frame;
import com.snake.rodion.snakeWorld.Node;
import com.snake.rodion.snakeWorld.Snake;
import com.snake.rodion.snakeWorld.World;

import java.util.ArrayList;

import static com.snake.rodion.snakeWorld.Constants.PLANK_CONSTANT;
import static com.snake.rodion.snakeWorld.Direction.*;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture imgFood, imgBoard,imgHead ;
	World world;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		imgFood = new Texture("apple.png");
        imgBoard = new Texture("board.png");
		imgHead = new Texture("head.png");
		Frame frame = new Frame(new Node(0,0), new Node(20,15));
		ArrayList<Node> body = new ArrayList<Node>();
		body.add(new Node(2,2));
		body.add(new Node(2,3));
		world = new World(body, frame, new Node(3,3) );
		world.setDirection(RIGHT);
		world.step();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.draw(imgBoard, 0, 0);
		batch.draw(imgFood, world.getFood().getPosition().getX()*PLANK_CONSTANT, world.getFood().getPosition().getY()*PLANK_CONSTANT);
		for (Node node:world.getSnake().getBody() )
			batch.draw(imgHead, node.getX()*PLANK_CONSTANT, node.getY()*PLANK_CONSTANT);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgFood.dispose();
		imgBoard.dispose();
		imgHead.dispose();
	}
}
