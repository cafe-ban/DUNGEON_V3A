package com.quadx.dungeons;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.quadx.dungeons.monsters.Monster;
import com.quadx.dungeons.states.AbilitySelectState;
import com.quadx.dungeons.states.GameStateManager;
import com.quadx.dungeons.states.MainMenuState;
import com.quadx.dungeons.states.mapstate.Map2State;
import com.quadx.dungeons.states.mapstate.MapState;
import com.quadx.dungeons.tools.ColorConverter;
import com.quadx.dungeons.tools.FontHandler;
import sun.security.provider.SHA;

import static com.quadx.dungeons.tools.FontHandler.generator;

public class Game extends ApplicationAdapter {

	public static BitmapFont font;
	SpriteBatch spriteBatch;
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 724;
	public static Player player= new Player();
	public static Monster monster=new Monster();
	private GameStateManager gameStateManager;
	public static ShapeRenderer shapeR;

	@Override
	public void create () {
		gameStateManager=new GameStateManager();
		shapeR=new ShapeRenderer();
		player.addSpell();
		Gdx.graphics.setWindowedMode(WIDTH,HEIGHT);
		setFontSize(20);
		spriteBatch = new SpriteBatch();
		//gameStateManager.push(new Map2State(gameStateManager));
		gameStateManager.push(new MainMenuState(gameStateManager));

		//gameStateManager.push(new AbilitySelectState(gameStateManager));
	}
	public static BitmapFont getFont(){

		return font;
	}
	public static void console(String s){
		System.out.println(s);
	}
	public static void setFontSize(int x){

		try{
			font.dispose();
			generator.dispose();
		}
		catch (NullPointerException e)
		{
			//console("Null pointer disposing generator or font");
		}
		try {
			FreeTypeFontGenerator generator= new FreeTypeFontGenerator(Gdx.files.internal("fonts\\prstart.ttf"));;
			FreeTypeFontGenerator.FreeTypeFontParameter parameter= new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameter.size = x;
			font = generator.generateFont(parameter);
			//console("Font Generated");
		}
			catch (Exception e){
			e.printStackTrace();
		}
	}

	public static void getColor(ColorConverter c){
		font.setColor(c.getLIBGDXColor());
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(spriteBatch);
	}
}
