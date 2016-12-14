package com.dfy.heroworld;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dfy.heroworld.Screens.GameOverScreen;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Screens.Startgame;

public class HeroWorld extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 288;
	public static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short HERO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ITEM_BIT = 256;
	public static final short FIREBALL_BIT = 1024;

	public SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();

		setScreen(new PlayScreen(this));
		setScreen(new Startgame(this));
		setScreen(new GameOverScreen(this));

	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		super.dispose();

	}
}
