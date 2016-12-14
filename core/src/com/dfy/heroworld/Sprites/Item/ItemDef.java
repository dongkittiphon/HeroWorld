package com.dfy.heroworld.Sprites.Item;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by nanear_ss on 14/12/2559.
 */
public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position, Class<?> type){
        this.position = position;
        this.type = type;
    }
}
