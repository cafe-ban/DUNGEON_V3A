package com.quadx.dungeons.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.quadx.dungeons.Game;
import com.quadx.dungeons.commands.Command;
import com.quadx.dungeons.items.*;
import com.quadx.dungeons.items.equipment.Equipment;
import com.quadx.dungeons.states.mapstate.MapState;

import java.util.ArrayList;

import static com.quadx.dungeons.Game.commandList;
import static com.quadx.dungeons.Game.player;
import static com.quadx.dungeons.states.mapstate.MapState.viewX;
import static com.quadx.dungeons.states.mapstate.MapState.viewY;

/**
 * Created by Tom on 12/24/2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class ShopState extends State {


  //  public static float viewX=0;
    //public static float viewY=0;
  private int soldItemCost=0;
    private static float dtBuy=0;
    private float dtSold=0;

    private boolean dispSold = false;
    private static final ShapeRenderer shapeR=new ShapeRenderer();
    private static final ArrayList<Item> shopInv = new ArrayList<>();

    public ShopState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Game.WIDTH, Game.HEIGHT);
        cam.position.set(viewX+Game.WIDTH/2,viewY+Game.HEIGHT/2,0);
        if(!MapState.pause)
        genShopInv();
        Gdx.gl.glClearColor(0,0,0,1);
    }

    public static void exit(){
        MapState.pause=false;
        gsm.pop();
    }
    @Override
    protected void handleInput() {
        for(Command c: commandList){
            c.execute();
        }
        //keyboard functions--------------------------------------------------------

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) numberButtonHandler(0);
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) numberButtonHandler(1);
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) numberButtonHandler(2);
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) numberButtonHandler(3);
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) numberButtonHandler(4);
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) numberButtonHandler(5);
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_7)) numberButtonHandler(6);
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_8)) numberButtonHandler(7);
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_9)) numberButtonHandler(8);
    }
    private void numberButtonHandler(int i){
        if(!MapState.pause && Gdx.input.isKeyPressed(Input.Keys.MINUS) && dtBuy>.3){
                if(i>=0) {
                        try {
                            try {
                                Item item = player.invList.get(i).get(0);
                                if(item.isEquip)
                                    item.loadIcon(item.getType());
                                else
                                    item.loadIcon(item.getName());
                                player.invList.get(i).remove(0);
                                soldItemCost= (int) (item.getCost()*.75);
                                Game.player.setGold((float) (Game.player.getGold()+soldItemCost));
                                dispSold=true;
                                dtBuy=0;
                                if(player.invList.get(i).isEmpty()){
                                    player.invList.remove(i);
                                }
                            } catch (IndexOutOfBoundsException e) {
                            }
                        } catch (NullPointerException e) {
                        }
                    }

        }
        if(i<shopInv.size() && dtBuy>.3 && Game.player.getGold()>=shopInv.get(i).getCost()){
            Game.player.setGold(Game.player.getGold()-shopInv.get(i).getCost());
            Game.player.addItemToInventory(shopInv.get(i));
            if(i>=6) shopInv.remove(i);
            dtBuy=0;
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        dtBuy+=dt;
        if(dispSold) dtSold+=dt;
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        shapeR.setProjectionMatrix(cam.combined);

        if(!MapState.pause)
        drawShopInv(sb);
        drawPlayerInv(sb);

        sb.begin();
        GlyphLayout gl = new GlyphLayout();
        CharSequence cs="(TAB) EXIT";
        gl.setText(Game.getFont(),cs);
        Game.getFont().draw(sb,"(TAB) EXIT",viewX+(Game.WIDTH/2)-gl.width/2,viewY+70);
        sb.end();

        shapeR.begin(ShapeRenderer.ShapeType.Line);
        shapeR.setColor(1,0,0,1);
        shapeR.rect(viewX+40,viewY+40,Game.WIDTH-80,Game.HEIGHT-80);
        shapeR.end();
    }
    private void drawPlayerInv(SpriteBatch sb){
        sb.begin();

        Game.getFont().draw(sb,"INV",viewX+ Game.WIDTH/2,viewY+ Game.HEIGHT-50);
        Game.getFont().draw(sb,"G "+ Game.player.getGold(),viewX+(Game.WIDTH/2)+100,viewY+ Game.HEIGHT-50);
        if(dtSold<.3 && dispSold){
            Game.getFont().draw(sb,"+"+soldItemCost,viewX+Game.WIDTH-150,viewY+Game.HEIGHT-50);
        }
        else{
            dtSold=0;
            dispSold=false;
        }

        for(int i = 0; i< Game.player.invList.size(); i++)
        {
            try {
                ArrayList<Item> al = Game.player.invList.get(i);
                Game.getFont().draw(sb, (i + 1) + ". " + al.get(0).getName(), viewX + Game.WIDTH / 2, viewY + Game.HEIGHT - 90 - (i * 60));
                Game.getFont().draw(sb, "x" + al.size(), viewX + Game.WIDTH - 100, viewY + Game.HEIGHT - 90 - (i * 60));
            }catch (IndexOutOfBoundsException e){

            }

        }

        sb.end();
    }
    private void drawShopInv(SpriteBatch sb){
        sb.begin();
        Game.getFont().draw(sb,"SHOP",viewX+ 50,viewY+ Game.HEIGHT-50);
        for(int i=0;i<shopInv.size();i++){
            Item item=shopInv.get(i);
            Game.getFont().draw(sb,(i+1)+". "+item.getName(),viewX+70,viewY+ Game.HEIGHT-90-(i*60));
            Game.getFont().draw(sb,item.getCost()+"G",viewX+(Game.WIDTH/2)-100,viewY+ Game.HEIGHT-90-(i*60));
        }
        sb.end();
    }
    private void genShopInv(){
        shopInv.clear();
        shopInv.add(new Potion());
        shopInv.add(new ManaPlus());
        shopInv.add(new AttackPlus());
        shopInv.add(new DefPlus());
        shopInv.add(new IntPlus());
        shopInv.add(new SpeedPlus());
        Equipment e=Equipment.generateEquipment();
        shopInv.add(e);
        e=Equipment.generateEquipment();
        shopInv.add(e);
        SpellBook s = new SpellBook();
        shopInv.add(s);

    }
    public void dispose() {

    }
}
