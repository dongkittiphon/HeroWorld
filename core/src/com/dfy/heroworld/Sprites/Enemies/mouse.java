package com.dfy.heroworld.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Sprites.Fire.FireBall;
import com.dfy.heroworld.Sprites.Hero;
import javafx.animation.Animation;

/**
 * Created by Asus on 13/12/2559.
 */
    public class mouse extends Enemy{

    private float stateTime;
    private com.badlogic.gdx.graphics.g2d.Animation walk;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public mouse(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("mouse"),i*200,0,200,100));
        }
        walk = new com.badlogic.gdx.graphics.g2d.Animation(0.4f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),20/ HeroWorld.PPM,20/HeroWorld.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8/HeroWorld.PPM);

        //ชน
        fdef.filter.categoryBits = HeroWorld.ENEMY_BIT;
        fdef.filter.maskBits = HeroWorld.GROUND_BIT |
                HeroWorld.FIREBALL_BIT|
                HeroWorld.BRICK_BIT |
                HeroWorld.ENEMY_BIT |
                HeroWorld.OBJECT_BIT |
                HeroWorld.HERO_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("mouse"), 1200, 0, 200, 96));
            stateTime = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walk.getKeyFrame(stateTime, true));
        }
    }

    @Override
    public void hitByFireball(FireBall fireBall) {
        setToDestroy = true;
        //เสียงตาย
    }
    @Override
    public void hitByEnemy(Enemy enemy) {
        setToDestroy = true;
        //เสียงตาย
    }

    @Override
    public void reverseVelocity(boolean x, boolean y) {
        super.reverseVelocity(x, y);
    }
}




