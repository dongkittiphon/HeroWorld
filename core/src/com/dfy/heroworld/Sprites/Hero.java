package com.dfy.heroworld.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Scenes.Hud;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Sprites.Enemies.Enemy;
import com.dfy.heroworld.Sprites.Enemies.Tao;
import com.dfy.heroworld.Sprites.Fire.FireBall;

/**
 * Created by _iDong on 11/27/2016.
 */
public class Hero extends Sprite {
    public enum State{FALLING, JUMPING, STANDING, RUNNING, DIE};

    public State currentState;
    public State previousState;
    public World world;
    public  static Body b2body;


    private TextureRegion heroStand;
    private TextureRegion heroDead;
    private Animation heroRun;
    private Animation heroJump;

    private float stateTimer;
    private boolean runningRight;
    private static  boolean heroIsDead;
    private PlayScreen screen;

    private Array<FireBall> fireballs;

    public Hero(PlayScreen screen) {
        super(screen.getAtlas().findRegion("boy"));
        this.world = screen.getWorld();
        this.screen = screen;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 6; i < 8; i++)
            frames.add(new TextureRegion(getTexture(),i * 100, 200,100 ,150 ));
        heroRun = new Animation(0.1f, frames);
        frames.clear();

      //  for (int i = 4; i < 6; i++)
       //     frames.add(new TextureRegion(getTexture(),i * 16, 10,16 ,16 ));
        //heroJump = new Animation(0.1f, frames);

        heroStand = new TextureRegion(getTexture(), 600, 200,100,150);
        heroDead = new TextureRegion(getTexture(), 800, 200,100,150);

        defineHero();
        setBounds(0, 0, 20 /HeroWorld.PPM, 24/ HeroWorld.PPM);
        setRegion(heroStand);
        fireballs = new Array<FireBall>();
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));

        for(FireBall  ball : fireballs) {
            ball.update(dt);
            if(ball.isDestroyed())
                fireballs.removeValue(ball, true);
        }
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;

        switch (currentState){
            case DIE:
                region = heroDead;
                break;
            case RUNNING:
                region = heroRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
                default:
                    region = heroStand;
                    break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }else if ((b2body.getLinearVelocity().x > 0 || runningRight)&& region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState(){

        if(heroIsDead) {
            return State.DIE;
        }else if (b2body.getLinearVelocity().x !=0) {
            return State.RUNNING;
        }else return State.STANDING;
    }

    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ HeroWorld.PPM, 32/ HeroWorld.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10/ HeroWorld.PPM);

        fdef.filter.categoryBits = HeroWorld.HERO_BIT;
        fdef.filter.maskBits = HeroWorld.GROUND_BIT |
                HeroWorld.BRICK_BIT |
                HeroWorld.OBJECT_BIT |
                HeroWorld.HERO_BIT |
                HeroWorld.ITEM_BIT |
                HeroWorld.ENEMY_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }

    public void fire(){
        fireballs.add(new FireBall(this.screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
    }

    public void jump(){
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public static boolean isDead() {
        return heroIsDead;
    }

    public void hit(Enemy enemy){

    }

    public static void die() {

        if (!isDead()) {

            heroIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = HeroWorld.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);

        }
    }

}
