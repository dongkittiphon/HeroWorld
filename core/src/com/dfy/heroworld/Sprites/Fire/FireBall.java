package com.dfy.heroworld.Sprites.Fire;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Sprites.Hero;

/**
 * Created by _iDong on 11/27/2016.
 */
public class FireBall extends Sprite {
    PlayScreen screen;
    World world;

    Array<TextureRegion> frames;
    Animation fireAnimation;

    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;

    Body b2body;

    public FireBall(PlayScreen screen, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("fireball"),0, 0, 8, 8));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("fireball"),8, 0, 8, 8));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("fireball"),16, 0, 8, 8));

        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));

        setBounds(x, y, 6 / HeroWorld.PPM, 6 / HeroWorld.PPM);
        defineFireBall();
    }

    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 /HeroWorld.PPM : getX() - 12  /HeroWorld.PPM, getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / HeroWorld.PPM);

        fdef.filter.categoryBits = HeroWorld.FIREBALL_BIT;
        fdef.filter.maskBits = HeroWorld.ENEMY_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2 (fireRight ? 2f :-2f, 0));
    }

    public void update(float dt){
        stateTime += dt;
        setRegion(fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 0.7f || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }




}

