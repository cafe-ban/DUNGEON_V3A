package com.quadx.dungeons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.quadx.dungeons.tools.EMath;

import static com.quadx.dungeons.Game.player;

/**
 * Created by range_000 on 1/10/2017.
 */
public class Anim {
    Texture texture= null;
    Vector2 pos= null;
    float vel= 0;
    Vector2 dest=null;
    boolean end= false;
    int flag = -1;
    //flags
    //0-drop
    //2-moveplayer


    public Anim(){

    }
    public Anim(Texture t, Vector2 p, float v, Vector2 d, int f){
        texture=t;
        pos=p;
        vel=v;
        dest=d;
        flag=f;
    }
    public void update(){
        float dx= EMath.dx(pos,dest);
        float dy= EMath.dy(pos,dest);
        Vector2 velcomp=Physics.getVxyComp(vel,pos,dest);
        if(Math.abs(dx)<vel)
            pos.x=dest.x;
        else
            pos.x+=velcomp.x;
        if(Math.abs(dy)<vel)
            pos.y=dest.y;
        else
            pos.y+=velcomp.y;
        if((int) dest.x ==(int) pos.x &&(int) dest.y==(int) pos.y )end=true;
        switch (flag){
            case 2:{
                player.setAbsPos(pos);
            }
        }
        if(end)
            player.overrideControls=false;
    }

    public Vector2 getPos() {
        return pos;
    }
    public int getFlag(){return flag;}
    public boolean isEnd(){
        return end;
    }

    public Texture getTexture() {
        return texture;
    }
}
