package com.ormgas.rokonpong;

import android.graphics.Typeface;

import com.stickycoding.rokon.Font;
import com.stickycoding.rokon.Rokon;
import com.stickycoding.rokon.TextTexture;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TextureAtlas;

public class Textures {

	public static TextureAtlas atlas;
	public static Texture smallcloud;
	public static Texture bigcloud;
	public static Texture splashSpirit;
	public static Texture hero;
	public static Texture grass;
	public Textures() {
		
	}
	
	public static void load()
	{
		atlas = new TextureAtlas();
		
		splashSpirit = new Texture("thespirit.png");
		atlas.insert(splashSpirit);
		
		bigcloud = new Texture("bigcloud.png");
		atlas.insert(bigcloud);

		smallcloud = new Texture("smallcloud.png");
		atlas.insert(smallcloud);
		
		atlas.complete();
		
		TextureAtlas atlas2 = new TextureAtlas();
		hero = new Texture("hero.png",12,8);
		atlas2.insert(hero);
		grass = new Texture("sand3.png");
		atlas2.insert(grass);
		atlas2.complete();
	}
}
