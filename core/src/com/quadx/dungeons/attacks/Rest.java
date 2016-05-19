package com.quadx.dungeons.attacks;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by range on 5/17/2016.
 */
public class Rest extends Attack {
    public Rest(){
        costGold = 0;
        type = 2;
        powerA = new int[]{0, 0, 0, 0, 0};
        costA = new int[]{10, 20, 30, 40, 50};
        name = "Rest";
        power = 0;
        cost = 10;
        mod = 8;
        range = 0;
        spread = 0;
        description = "Converts M to Energy";
    }
}