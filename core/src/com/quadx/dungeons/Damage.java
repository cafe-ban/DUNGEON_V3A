package com.quadx.dungeons;

import com.quadx.dungeons.monsters.Monster;

import java.util.Random;

/**
 * Created by Tom on 11/24/2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Damage {
    private static int crit;
    private static int damage;
    private static final int defaultDamage = 1;
    private static final Random rn =new Random();

    public static int playerPhysicalDamage(Player p, Monster m, int power){
        double a=((2*(double)p.level+10)/250);
        double b= ((double)(p.attack+p.attackMod)/m.getDefense());
        double c=(power*2);
        damage =(int) (a *b * c );
        if (damage < 0) //checks for negative damage
            damage = defaultDamage;
        if(rn.nextFloat()<.15)
            damage*=1.15;
        return damage;
    }
    public static int monsterPhysicalDamage(Player p, Monster m, int power){
        int baseDamage=(int)((m.getAttack()*3)+(power))-((p.getDefense()));
        crit=(baseDamage/100)*15;
        if(crit<1)crit=1;
        damage= baseDamage+rn.nextInt(crit);
        if (damage < 0) //checks for negative damage
            damage = defaultDamage;
        if(Game.player.safe)
            damage=0;
        return damage;
    }
    public static int playerMagicDamage(Player p, Monster m, int power){
        double a=((2*(double)p.level+10)/250);
        double b= ((double)(p.intel+p.intelMod)/m.getIntel());
        double c=(power*2);
        damage =(int) (a *b * c );
        if(rn.nextFloat()<.15)
            damage*=1.15;
        if (damage < 0) //checks for negative damage
            damage = defaultDamage;
        return damage;
    }
    public static int monsterMagicDamage(Player p, Monster m, int power){
        int baseDamage=(int)((m.getIntel()*3)+(power))-((p.getIntel()));
        crit=(baseDamage/100)*15;
        if(crit<1)crit=1;
        damage= baseDamage+rn.nextInt(crit);
        if (damage < 0) //checks for negative damage
            damage = defaultDamage;
        if(Game.player.safe)
            damage=0;
        return damage;
    }
}
