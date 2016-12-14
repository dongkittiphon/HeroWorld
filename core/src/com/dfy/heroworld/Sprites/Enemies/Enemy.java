package com.dfy.heroworld.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dfy.heroworld.Scenes.Hud;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Sprites.Fire.FireBall;
import com.dfy.heroworld.Sprites.Hero;
import sun.security.provider.certpath.Vertex;

/**
 * Created by _iDong on 11/27/2016.
 */
public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public  Enemy(PlayScreen screen,float x,float y){
        this.world=screen.getWorld();
        this.screen=screen;
        setPosition(x,y);
        defineEnemy();
        velocity=new Vector2(0.6f,-1);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();
    public  abstract  void  update(float dt);
    public abstract void hitByFireball(FireBall fireBall);
    public abstract void hitByEnemy(Enemy enemy);

    public void  reverseVelocity(boolean x, boolean y){
        if(x){
            velocity.x = - velocity.x;
        }
        if (y){
            velocity.y = - velocity.y;
        }
    }

}
