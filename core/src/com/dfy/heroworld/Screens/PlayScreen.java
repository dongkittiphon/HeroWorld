package com.dfy.heroworld.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Scenes.Hud;
import com.dfy.heroworld.Sprites.Enemies.Enemy;
import com.dfy.heroworld.Sprites.Fire.FireBall;
import com.dfy.heroworld.Sprites.Hero;
import com.dfy.heroworld.Tool.B2WorldCreator;
import com.dfy.heroworld.Tool.WorldContactListener;

/**
 * Created by _iDong on 11/27/2016.
 */
public class PlayScreen implements Screen{
    private HeroWorld game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Hero player;


    public PlayScreen(HeroWorld game) {
        atlas = new TextureAtlas("sprites.pack");
        this.game = game;

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(HeroWorld.V_WIDTH / HeroWorld.PPM, HeroWorld.V_HEIGHT/ HeroWorld.PPM, gamecam);
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ HeroWorld.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/ 2 , gamePort.getWorldHeight() / 2 ,0);
//เพิ่มใหม่checkด้วย
     /*   maploader = new TmxMapLoader();
        map = maploader.load("level2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ HeroWorld.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/ 2 , gamePort.getWorldHeight() / 2 ,0);
*/
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        world.setContactListener(new WorldContactListener());

        player = new Hero(this);




    }
    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }
    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.jump();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.fire();
        }

    }
    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6, 2);
        player.update(dt);
        //gamecam.position.x = player.b2body.getPosition().x;
        //gamecam.position.y = player.b2body.getPosition().y;
        for (Enemy enemy:creator.getshell()){
            enemy.update(dt);
        }
        for (Enemy enemy:creator.getmouse()){
            enemy.update(dt);
        }
        for( Enemy enemy : creator.getTao()) {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / HeroWorld.PPM) {
                enemy.b2body.setActive(true);
            }
        }

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);


        for(Enemy enemy : creator.getTao())
            enemy.draw(game.batch);
        for(Enemy enemy : creator.getmouse())
            enemy.draw(game.batch);
        for(Enemy enemy : creator.getshell())
            enemy.draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }
}
