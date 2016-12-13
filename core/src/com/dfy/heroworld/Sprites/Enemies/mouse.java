package com.dfy.heroworld.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Sprites.Hero;
import javafx.animation.Animation;

/**
 * Created by Asus on 13/12/2559.
 */
public class mouse extends Enemy{
    private float statetime;
    private Animation walk;
    private Array<TextureRegion>frame;

    public mouse(PlayScreen screen,float x,float y){
        super(screen,x,y);
        frame=new Array<TextureRegion>();

        for (int i=0;i<2;i++){
            frame.add(new TextureRegion(screen.getAtlas().findRegion("heroworld"),i*1,103,200,200));
            walk= new Animation(0.4f, frame);
    }
    statetime=0;
    setBounds(getX(),getY(),10/ HeroWorld.PPM,10/HeroWorld.PPM);
    }
            


    @Override
    protected void defineEnemy() {
        BodyDef bdef=new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25/HeroWorld.PPM);

        //การชน
        fdef.filter.categoryBits=HeroWorld.ENEMY_BIT;
        fdef.filter.maskBits= HeroWorld.GROUND_BIT | HeroWorld.BRICK_BIT | HeroWorld.ENEMY_BIT | HeroWorld.OBJECT_BIT | HeroWorld.MARIO_BIT | HeroWorld.ITEM_BIT;
        fdef.shape=shape;
        b2body.createFixture(fdef).setUserData(this);





    }

    @Override
    public void update(float dt) {
        statetime+=dt;
        setPosition(b2body.getPosition().x+getWidth()/2,b2body.getPosition().y-getWidth()/2);
        setRegion(walk.getkeyframe(statetime));
        b2body.setLinearVelocity(velocity);
    }
}


