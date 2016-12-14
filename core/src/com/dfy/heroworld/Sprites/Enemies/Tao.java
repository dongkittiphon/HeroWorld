package com.dfy.heroworld.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Sprites.Fire.FireBall;

/**
 * Created by _iDong on 12/14/2016.
 */
public class Tao extends Enemy {
    public enum State {WALKING}
    public State currentState;
    public State previousState;

    private Array<TextureRegion> frames;
    private TextureRegion taoDead;
    private Animation walk;

    private float stateTime;
    private boolean setToDestroy;
    private boolean destroyed;

    public Tao(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("tao"),i*200,0,200,150));
        }
        walk = new Animation(0.4f,frames);
        taoDead =  new TextureRegion(screen.getAtlas().findRegion("tao"), 400, 0,200,150);

        currentState = previousState = State.WALKING;
        stateTime = 0;

        setBounds(getX(),getY(),20/ HeroWorld.PPM,20/HeroWorld.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    public void drow(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);

    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){
            case WALKING:
            default:
                region = walk.getKeyFrame(stateTime, true);
                break;

        }

        if(velocity.x > 0 && region.isFlipX() == false){
            region.flip(true, false);
        }
        if(velocity.x < 0 && region.isFlipX() == true){
            region.flip(true, false);
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;

        return region;
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

        fdef.restitution = 0.5f;

    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("tao"), 400, 0, 200, 150));
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
    public void reverseVelocity(boolean x, boolean y) {
        super.reverseVelocity(x, y);
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
            reverseVelocity(true, false);
    }
}
