package com.ormgas.rokonpong;

import com.ormgas.rokonpong.status.CharacterAttributes;
import com.stickycoding.rokon.GameObject;

public class Bunny extends GamePlayer {
	public static final int BASE = 51;
	
	
	private Bunny(float x, float y, float width, float height,CharacterAttributes charAttr) {
		super(x, y, width, height,charAttr,BASE);
	}
	public static final class Builder{
		float x,y,width,height;
		CharacterAttributes charAttr = new CharacterAttributes();
		public Builder() {
			width = MainActivity.calcWidth(Textures.hero.getWidth() / 12);
			height = MainActivity.calcHeight(Textures.hero.getHeight() / 8);
			x = MainActivity.screenWidth / 2;
			y = MainActivity.screenHeight-height-1;
		}
		public Bunny getBunny() {
			return new Bunny(x,y,width,height,charAttr);
		}
		public Builder setX(float x) {
			this.x = x;
			return this;
		}
		public Builder setY(float y) {
			this.y  = y;
			return this;
		}
		public Builder setWidth(float width) {
			this.width = width;
			return this;
		}
		public Builder setHeight(float height) {
			this.height = height;
			return this;
		}
	}
	
	public float getAttackFactor(GamePlayer attacker) {
		float factor = 1.0f;
		if(attacker instanceof Hunter) {
			factor = 5.0f; 
		}else if(attacker instanceof Wolf) {
			factor = 0.5f;
		}
		return factor;
	}
}
