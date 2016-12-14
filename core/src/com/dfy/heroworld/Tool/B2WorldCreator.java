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
import com.dfy.heroworld.Sprites.Hero;

/**
 * Created by _iDong on 12/1/2016.
 */
public class B2WorldCreator {
    private Array<Tao> tao;

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

    }

    public Array<Tao> getTao() {
        return tao;
    }
}
