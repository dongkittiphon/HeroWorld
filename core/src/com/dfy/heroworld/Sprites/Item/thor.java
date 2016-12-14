package com.dfy.heroworld.Sprites.Item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.dfy.heroworld.HeroWorld;
import com.dfy.heroworld.Scenes.Hud;
import com.dfy.heroworld.Screens.PlayScreen;
import com.dfy.heroworld.Sprites.Hero;

/**
 * Created by Asus on 14/12/2559.
 */
public class thor extends  Item {
    private float stateTime;
    private Array<TextureRegion> frames;
    private TextureRegion item;
    private Animation walk;
    private Animation itembrink;
    private thor thor;
    private boolean setToDestroy;
    private boolean destroyed;


    public thor(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("thor"), i * 200, 0, 200, 200));
            itembrink = new Animation(0.1f, frames);
            walk=new Animation(0.4f,frames);
            // frames.clear();
        }
        stateTime=0;
        setBounds(getX(),getY(),30/ HeroWorld.PPM,30/HeroWorld.PPM);
    }

      /*  item = new TextureRegion(getTexture(),0, 0,200,200);
        setRegion(item);
    }*/

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / HeroWorld.PPM);
        fdef.filter.categoryBits = HeroWorld.ITEM_BIT;
        fdef.filter.maskBits = HeroWorld.HERO_BIT |
                HeroWorld.OBJECT_BIT |
                HeroWorld.GROUND_BIT |
                HeroWorld.ENEMY_BIT |
                HeroWorld.BRICK_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void use(Hero hero) {
        Hud.addScore(100);
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(walk.getKeyFrame(stateTime));
        //  body.setLinearVelocity(velocity);
    }

}
