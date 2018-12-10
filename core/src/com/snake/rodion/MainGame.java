package com.snake.rodion;

import com.badlogic.gdx.Game;
import com.snake.rodion.screens.GameScreen;

public class MainGame extends Game {
	public MainGame(){
		//TODO: aquí no va nada
	}
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}
}
