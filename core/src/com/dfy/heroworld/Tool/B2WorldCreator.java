package com.dfy.heroworld.Tool;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Sprites.Hero;

/**
 * Created by _iDong on 12/1/2016.
 */
public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map) {
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
    }
}
