package com.quadx.dungeons.items;

import com.quadx.dungeons.tools.ImageLoader;

import static com.quadx.dungeons.Game.player;

/**
 * Created by Tom on 11/23/2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class ManaPlus extends Item {
    public ManaPlus(){
        name="Mana+";
        int size= (int) (3*((double)player.getManaMax()/(double)500));
        if(size==0){
            icon= ImageLoader.mana[0];
            manamod=60;
            cost=100;
        }
        else if(size==1){
            icon=ImageLoader.mana[1];
            manamod=200;
            cost=100;
        }
        else if(size>1){
            icon=ImageLoader.mana[2];
            manamod=1000;
            cost=100;
        }
    }
}
