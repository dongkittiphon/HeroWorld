package com.dfy.heroworld.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.String;
import javafx.stage.Stage;

/**
 * Created by _iDong on 11/27/2016.
 */
public class Hud {
    public com.badlogic.gdx.scenes.scene2d.Stage stage;
    private Viewport viewport;
    private Integer life;

    Label lifeLable;
    Label leftlifeLable;

    public Hud(SpriteBatch sb){
        life = 3;

        viewport = new FitViewport(400, 200, new OrthographicCamera());
        stage = new com.badlogic.gdx.scenes.scene2d.Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        lifeLable = new Label(java.lang.String.format("x %1d",life), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        leftlifeLable = new Label("LIFE" ,new Label.LabelStyle(new BitmapFont(),Color.WHITE));

        table.add(leftlifeLable).expandX().padTop(10);
        table.row();
        table.add(lifeLable).expandX();

        stage.addActor(table);


    }

}
