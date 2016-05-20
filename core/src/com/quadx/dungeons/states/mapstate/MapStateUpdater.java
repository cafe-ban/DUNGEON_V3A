package com.quadx.dungeons.states.mapstate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.quadx.dungeons.Cell;
import com.quadx.dungeons.Game;
import com.quadx.dungeons.Player;
import com.quadx.dungeons.attacks.Attack;
import com.quadx.dungeons.monsters.Monster;
import com.quadx.dungeons.states.GameStateManager;
import com.quadx.dungeons.states.ShopState;


/**
 * Created by Tom on 1/29/2016.
 */
public class MapStateUpdater extends MapState {
    private static float dtimeMin=0;
    public static float dtWater=0;
    static float dtRun=0;
    static float dtDig=0;
    static float dtMove = 0;
    static float dtAttack = 0;
    static float dtRegen = 0;
    static float dtEnergyRe=0;
    static float dtInfo =0;
    static float dtItem=0;
    static float dtToolTip=0;
    static float dtInvSwitch=0;
    static float dtMap=0;
    static float lerp = 0.2f;
    static int x = 0;


    public MapStateUpdater(GameStateManager gsm) {
        super(gsm);
    }
    public static void moveMonsters(){
        dtimeMin+= Gdx.graphics.getDeltaTime();
        if(dtimeMin>.1) {
            gm.clearMonsterPositions();
            for(Monster m :gm.monsterList){
                m.move();
            }
            dtimeMin=0;
        }
    }
    public static void fuckingStupidUpdateFunction(float dt){
        Game.player.calculateArmorBuff();
        mouseX=Gdx.input.getX();
        mouseY=Gdx.input.getY();
        mouseRealitiveX=(int)(mouseX+viewX);
        mouseRealitiveY=(int)(Game.HEIGHT-mouseY+viewY);
        dtEnergyRe+=dt;

        if(dtEnergyRe>.2 && Game.player.getEnergy()<Game.player.getEnergyMax()){
            Game.player.setEnergy(Game.player.getEnergy()+Game.player.getEnergyRegen());
            if(Game.player.getEnergy()>Game.player.getEnergyMax())Game.player.setEnergy(Game.player.getEnergyMax());
            dtEnergyRe=0;
        }

        ////////////////////////////////////////////////////////
        //DEBUG OUTPUT SECTION
        //out("sSS");
        //out(Game.player.getX()+" "+Game.player.getY());
        // out("@"+mouseRealitiveX+" "+mouseRealitiveY);
        try {
            //out("-"+qButtonList.get(0).getPx() + " " + qButtonList.get(0).getPy());
        }
        catch (IndexOutOfBoundsException e){
        }
        ////////////////////////////////////////////////////////


        MapStateRender.loadInventoryIcons();
        MapStateRender.loadEquipIcons();
        Vector3 position = cam.position;

        position.x += (Game.player.getCordsPX().x - position.x) * lerp;
        position.y += (Game.player.getCordsPX().y - position.y) * lerp;
        cam.position.set(position);
        cam.update();
        dtDig +=dt;
        dtWater+=Gdx.graphics.getDeltaTime();

        dtRegen += dt;
        dtRun+=dt;
        dtItem+=dt;
        dtInfo+=dt;
        dtLootPopup +=dt;
        dtStatPopup+=dt;
        dtDamageTextFloat += dt;
        dtMap +=dt;
        dtMessage += Gdx.graphics.getDeltaTime();
        if(hovering){
            dtToolTip+=dt;
            if(dtToolTip>.6){
                popupItem =null;
                hovering=false;
                dtToolTip=0;
            }
        }
        if (dtDamageTextFloat > .6) {
            displayPlayerDamage = false;
        } else {
            //textY += .2;
        }
        if (dtRegen > .4) {
            if (Game.player.getMana() < Game.player.getManaMax())
                Game.player.setMana(Game.player.getMana() + Game.player.getManaRegenRate());
            if (Game.player.getHp() < Game.player.getHpMax())
                Game.player.setHp(Game.player.getHp() + Game.player.getHpRegenRate());
            dtRegen = 0;
        }
        if(effectLoaded) effect.update(Gdx.graphics.getDeltaTime());
        viewX = cam.position.x - cam.viewportWidth / 2;
        viewY = cam.position.y - cam.viewportHeight / 2;
        MapStateExt.mouseOverHandler();
        x++;
    }
    public static void buttonHandler() {
        dtMove += Gdx.graphics.getDeltaTime();
        dtAttack += Gdx.graphics.getDeltaTime();
        dtInvSwitch+= Gdx.graphics.getDeltaTime();
        if (dtMove > Game.player.getMoveSpeed()) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) movementHandler(0, 1, 'w');
            if (Gdx.input.isKeyPressed(Input.Keys.S)) movementHandler(0, -1, 's');
            if (Gdx.input.isKeyPressed(Input.Keys.A)) movementHandler(-1, 0, 'a');
            if (Gdx.input.isKeyPressed(Input.Keys.D)) movementHandler(1, 0, 'd');
            dtMove = 0;
        }
        if(dtInvSwitch>.15) {
            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                if (MapStateRender.inventoryPos >= 0)
                    MapStateRender.inventoryPos--;
                else {
                    MapStateRender.inventoryPos = Game.player.invList.size() - 1;
                }
                dtInvSwitch=0;

            }
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                if (MapStateRender.inventoryPos < Game.player.invList.size())
                    MapStateRender.inventoryPos++;
                else {
                    MapStateRender.inventoryPos = 0;
                }
                dtInvSwitch=0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            numberButtonHandler(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            numberButtonHandler(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            numberButtonHandler(2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            numberButtonHandler(3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            numberButtonHandler(4);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            numberButtonHandler(5);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
            numberButtonHandler(6);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
            numberButtonHandler(7);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F1)){
            functionButtonHandler(0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F2)){
            functionButtonHandler(1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F3)){
            functionButtonHandler(2);

        }
        /*
        if(Gdx.input.isKeyPressed(Input.Keys.F4)){
            functionButtonHandler(3);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F5)){
            functionButtonHandler(4);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F6)){
            functionButtonHandler(5);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F7)){
            functionButtonHandler(6);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F8)){
            functionButtonHandler(7);
        }*/
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            if (dtAttack > attackMintime) {
                MapStateExt.battleFunctions(lastNumPressed );
                //Game.player.attackList.get(Game.player.attackList.indexOf(attack)).setUses();
                //Game.player.attackList.get(Game.player.attackList.indexOf(attack)).checkLvlUp();
                dtAttack=0;
            }
        }
        /*
        if (Gdx.input.isKeyPressed(Input.Keys.PLUS)) {
            cellW++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            if(cellW>3)cellW--;
        }*/
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT) ||
                    Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                if(dtDig>Game.player.getMoveSpeed()) {
                    if (Game.player.getEnergy() > 5) {
                        int x= Game.player.getX()-1;
                        int y= Game.player.getY()-1;
                        gm.clearArea(x,y,true);
                        Game.player.setEnergy(Game.player.getEnergy() - 2);
                    }
                    dtDig=0;
                }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT) ||Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) ){
            if(dtRun>.05 && Game.player.getEnergy()>3) {
                Game.player.setMoveSpeed(.04f);
                Game.player.setEnergy(Game.player.getEnergy() - 3);
                dtRun=0;
            }
            else  Game.player.setMoveSpeed(.08f);

        }
        else{
            Game.player.setMoveSpeed(.08f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            clearFront();
            lastPressed = 'a';
            setFront(Game.player.getX(), Game.player.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            clearFront();
            lastPressed = 'd';
            setFront(Game.player.getX(), Game.player.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            clearFront();
            lastPressed = 'w';
            setFront(Game.player.getX(), Game.player.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            clearFront();
            lastPressed = 's';
            setFront(Game.player.getX(), Game.player.getY());
        }
        if(Gdx.input.isKeyPressed(Input.Keys.COMMA)){
            cam.position.set(0,0,0);
            gsm.push(new ShopState(gsm));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            if(dtMap>.6) {
                gm.initializeGrid();
                dtMap=0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.TAB)) {
            if (attackMenuOpen) attackMenuOpen = false;
            if (inventoryOpen) inventoryOpen = false;
        }
        //collisionHandler();
    }
    public static void functionButtonHandler(int i){
        if(dtItem>itemMinTime && Game.player.invList.size()>i){
            MapStateExt.useItem(MapStateRender.inventoryPos+ i);
            dtItem=0;
            if(Game.player.invList.get(i).size()==0) {
                Game.player.invList.remove(i);}
        }
    }
    public static void numberButtonHandler(int i){
        lastNumPressed=i;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            if(dtInfo>.4) {
                Attack a = Game.player.attackList.get(i);
                out(DIVIDER);
                out(a.getName() + ":");
                out("P:" + a.getPowerArr());
                out("M:" + a.getCostArr());
                out(a.getDescription());
                dtInfo=0;
            }
        }
        else {
            if (inventoryOpen && dtItem > itemMinTime && Game.player.invList.size() > i) {
                MapStateExt.useItem(i);
                dtItem = 0;
                if (Game.player.invList.get(i).size() == 0) {
                    Game.player.invList.remove(i);
                }
            } else if (inventoryOpen && dtItem < itemMinTime) {
            } else if (dtAttack > attackMintime) {
                attack = Game.player.attackList.get(attackListCount + i);
            }
        }
    }
    public static void movementHandler(int xmod, int ymod, char c) {
        int x = Game.player.getPX() + xmod * cellW;
        int y = Game.player.getPY() + ymod * cellW;
        for(Cell cell: gm.liveCellList){
            if(cell.getY()*cellW==y && cell.getX()*cellW==x && !cell.getWater()){
                Game.player.setCordsPX(x, y);
                Game.player.setLiveListIndex(gm.liveCellList.indexOf(cell));
            }
        }
        lastPressed=c;
    }
    public static void collisionHandler(){
        int index= Game.player.getLiveListIndex();
        Cell c=gm.liveCellList.get(index);
        if (c.hasLoot()) {
            dtLootPopup =0;
            lootPopup=new Texture(Gdx.files.internal("images/imCoin.png"));
            makeGold(Game.player.level);
            gm.liveCellList.get(index).setHasLoot(false);
        }
        if (c.hasCrate()) {
            openCrate();
            gm.liveCellList.get(index).setCrate(false);
        }
        if (c.hasWarp()) {
            Game.player.floor++;
            gm.initializeGrid();
        }
        if (c.getShop()) {
            gm.liveCellList.get(index).setShop(false);
            gsm.push(new ShopState(gsm));
        }
        /*
        if (gm.dispArray[Game.player.getX()][Game.player.getY()].hasMon()) {
            //enterBattlePhase();
        }
        else{

        }
        if (gm.dispArray[Game.player.getX()][Game.player.getY()].getAgro()) {
            int index= gm.dispArray[Game.player.getX()][Game.player.getY()].getIndex();
            float monx;
            float mony;
            try{
                monx= gm.monsterList.get(index).getX();
                mony= gm.monsterList.get(index).getY();
                int xmod=0,ymod=0;
                gm.dispArray[(int)monx][(int)mony].setMon(false);
                dtMonsterMove+=Gdx.graphics.getDeltaTime();
                if(dtMonsterMove>=.3) {
                    if (Game.player.getX() != monx) {

                        if (Game.player.getX() < monx) {
                            xmod -= 1;
                        }
                        if (Game.player.getX() > monx) {
                            xmod += 1;
                        }
                    }
                    if (Game.player.getY() != mony) {
                        if (Game.player.getY() < mony) {
                            ymod -= 1;
                        }
                        if (Game.player.getY() > mony) {

                        }
                    }
                    dtMonsterMove=0;
                }
                gm.monsterList.get(index).setCords((int)monx+xmod, (int)mony+ymod);
                gm.dispArray[(int)(monx+xmod)][(int)mony+ymod].setMon(true);
                if(gm.dispArray[(int)monx+xmod][(int)mony+ymod].hasPlayer()){
                    //enterBattlePhase();
                }
            }
            catch (IndexOutOfBoundsException e){

            }

        }*/
    }

}
