package com.dfy.heroworld.Tool;

import com.badlogic.gdx.physics.box2d.*;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Scenes.Hud;
import com.dfy.heroworld.Sprites.Enemies.Enemy;
import com.dfy.heroworld.Sprites.Fire.FireBall;
import com.dfy.heroworld.Sprites.Hero;
import com.dfy.heroworld.Sprites.Item.Item;

import java.awt.event.ContainerListener;

/**
 * Created by _iDong on 12/14/2016.
 */
public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case HeroWorld.ENEMY_BIT | HeroWorld.OBJECT_BIT :
                if(fixA.getFilterData().categoryBits == HeroWorld.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case HeroWorld.HERO_BIT | HeroWorld.ENEMY_BIT:
                Hud.setLife(-1);
                Hero.die();
                break;
            case HeroWorld.ENEMY_BIT | HeroWorld.ENEMY_BIT :
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case HeroWorld.HERO_BIT | HeroWorld.ITEM_BIT:
                //Gdx.app.log("item","score");
                if(fixA.getFilterData().categoryBits == HeroWorld.ITEM_BIT)
                    ((Item) fixA.getUserData()).use((Hero) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Hero) fixA.getUserData());
                break;
            case HeroWorld.FIREBALL_BIT | HeroWorld.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == HeroWorld.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).hitByFireball((FireBall) fixB.getUserData());
                    ((FireBall)fixB.getUserData()).setToDestroy();
                }else{
                    ((Enemy)fixB.getUserData()).hitByFireball((FireBall) fixA.getUserData());
                    ((FireBall)fixA.getUserData()).setToDestroy();
                }
                break;
            case HeroWorld.ENEMY_BIT | HeroWorld.GROUND_BIT :
                if(fixA.getFilterData().categoryBits == HeroWorld.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
