package com.dfy.heroworld.Sprites.Item;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Sprites.Hero;

/**
 * Created by _iDong on 11/27/2016.
 */
public abstract class Item extends Sprite{
    protected PlayScreen screen;
    protected World world;
    protected Body body;

    boolean todestroy;
    boolean destroy;

    public Item(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setBounds(getX(), getY(), 20 / HeroWorld.PPM, 20/ HeroWorld.PPM);

        todestroy = false;
        destroy = false;
        defineItem();
    }

    public abstract void defineItem();
    public abstract void use(Hero hero);

    public void update(float dt){
        if(todestroy && !destroy){
            world.destroyBody(body);
            destroy = true;
            //ใส่เสียง
        }
    }
    public void draw(Batch batch){
        if(!destroy)
            super.draw(batch);
    }
    public void destroy(){
        todestroy = true;
    }


}
