package com.dfy.heroworld.Tool;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Sprites.Enemies.Tao;
import com.dfy.heroworld.Sprites.Enemies.mouse;
import com.dfy.heroworld.Sprites.Enemies.shell;
import com.dfy.heroworld.Sprites.Hero;

/**
 * Created by _iDong on 12/1/2016.
 */
public class B2WorldCreator {
    private Array<Tao> tao;
    private Array<mouse> mouse;
    private Array<shell> shell;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        //wood
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2 )/ HeroWorld.PPM, (rect.getY()+rect.getHeight()/ 2) / HeroWorld.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/ 2 / HeroWorld.PPM, rect.getHeight() / 2 / HeroWorld.PPM);

            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //ground
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ HeroWorld.PPM, (rect.getY()+rect.getHeight()/ 2) / HeroWorld.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/ 2 / HeroWorld.PPM, rect.getHeight() / 2 / HeroWorld.PPM);

            fdef.shape = shape;
            body.createFixture(fdef);
        }

        tao = new Array<Tao>();
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            tao.add(new Tao(screen, rect.getX() / HeroWorld.PPM, rect.getY() / HeroWorld.PPM));

        }
        mouse = new Array<mouse>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
           // mouse.add(new mouse(screen, rect.getX() / HeroWorld.PPM, rect.getY() / HeroWorld.PPM));
        }
        shell = new Array<shell>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
           // shell.add(new shell(screen, rect.getX() / HeroWorld.PPM, rect.getY() / HeroWorld.PPM));
        }

    }

    public Array<Tao> getTao() {
        return tao;
    }
    public Array<mouse> getmouse() {
        return mouse;
    }
    public Array<shell> getshell() {
        return shell;
    }
}
