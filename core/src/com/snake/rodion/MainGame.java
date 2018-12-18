package com.snake.rodion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.snake.rodion.screens.GameScreen;

public class MainGame extends Game {
    private Preferences preferences;
	public MainGame(){
		//TODO: aquí no va nada
	}
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}
}
