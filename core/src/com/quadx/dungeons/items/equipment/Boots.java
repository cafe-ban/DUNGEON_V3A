package com.quadx.dungeons.items.equipment;

/**
 * Created by Tom on 12/29/2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Boots extends Equipment {
    public Boots(){
        type=Type.Boots;
        setGrade();
        setBoost();
        setMods();
        name=grade.toString()+" "+boost.toString()+" Boots";
    }
}
