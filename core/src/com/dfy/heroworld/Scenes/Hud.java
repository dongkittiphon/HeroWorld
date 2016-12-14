package com.dfy.heroworld.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Sprites.Hero;
import com.sun.org.apache.xpath.internal.operations.String;
import javafx.stage.Stage;

/**
 * Created by _iDong on 11/27/2016.
 */
public class Hud implements Disposable {
    public com.badlogic.gdx.scenes.scene2d.Stage stage;
    private Viewport viewport;
    private static Integer life;
    public static Integer score;
    public Integer worldTimer;
    private float timeCount;
    static Label lifeLable;
    static Label  scoreLable;
    Label leftlifeLable;
    Label heartLabel;
    Label countDownLabel;
    Label GdxLable;
    Label timeLabel;

    public Hud(SpriteBatch sb){
        life = 3;
        worldTimer = 100;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(HeroWorld.V_WIDTH, HeroWorld.V_HEIGHT, new OrthographicCamera());
        stage = new com.badlogic.gdx.scenes.scene2d.Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        lifeLable = new Label(java.lang.String.format("x %1d",life), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        leftlifeLable = new Label("LIFE" ,new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        countDownLabel = new Label(java.lang.String.format("%02d",worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLable = new Label(java.lang.String.format("%04d",score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        GdxLable = new Label("SCORE",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(leftlifeLable).expandX().padTop(10);
        table.add(GdxLable).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(lifeLable).expandX();
        table.add(scoreLable).expandX();
        table.add(countDownLabel).expandX();

        stage.addActor(table);
    }
    public static void addScore(int value){
        score += value;
        scoreLable.setText(java.lang.String.format("%04d",score));

    }

    public static void addLife(int value){
        life += value;
        lifeLable.setText(java.lang.String.format("x %1d",life));
    }
    public void update(float dt){
        timeCount += dt;
        if(timeCount>=1){
            worldTimer--;
            countDownLabel.setText(java.lang.String.format("%02d",worldTimer));
            timeCount = 0;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public static Integer getLife() {
        return life;
    }

    public static void setLife(Integer life) {
        Hud.life = life;
    }
}
