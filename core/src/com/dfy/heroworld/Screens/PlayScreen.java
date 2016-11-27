package com.dfy.heroworld.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Scenes.Hud;

/**
 * Created by _iDong on 11/27/2016.
 */
public class PlayScreen implements Screen {
    private HeroWorld game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    private Hud hud;

    public PlayScreen(HeroWorld game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(400, 200,gamecam);
        hud = new Hud(game.batch);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

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

    }
}
