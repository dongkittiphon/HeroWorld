package com.dfy.heroworld.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.dfy.heroworld.HeroWorld;

/**
 * Created by _iDong on 11/27/2016.
 */
public class Hero extends Sprite {
    public World world;
    public Body b2bobdy;

    public Hero(World world) {
        this.world = world;
        defineHero();
    }

    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32, 32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2bobdy = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);
        fdef.shape = shape;
        b2bobdy.createFixture(fdef);

    }
}
