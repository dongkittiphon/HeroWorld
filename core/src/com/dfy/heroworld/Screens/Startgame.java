package com.dfy.heroworld.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dfy.heroworld.HeroWorld;


/**
 * Created by nanear_ss on 14/12/2559.
 */
public class Startgame implements Screen{
    private Viewport viewport;
    private Stage stage;

    private Game game;
    private SpriteBatch batch;
    Texture bg;

    public Startgame(Game game){
        this.game = game;
        viewport = new FitViewport(HeroWorld.V_WIDTH,HeroWorld.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,((HeroWorld)game).batch);

        batch = new SpriteBatch();
        bg = new Texture("Background_Intro11.jpg");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new PlayScreen((HeroWorld) game));
            dispose();
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(bg,0,0,650,500);

        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
