package com.ormgas.rokonpong;

import com.ormgas.rokonpong.Bunny.Builder;
import com.ormgas.rokonpong.status.CharacterAttributes;
import com.stickycoding.rokon.GameObject;

public class Hunter extends GamePlayer {
	public static final int BASE = 48;

	float updateCnt = 0;

	private Hunter(float x, float y, float width, float height,
			CharacterAttributes charAttr) {
		super(x, y, width, height, charAttr, BASE);
	}

	public static final class Builder {
		float x, y, width, height;
		CharacterAttributes charAttr = new CharacterAttributes();

		public Builder() {
			x = MainActivity.screenWidth - 1;
			y = 1;
			width = MainActivity.calcWidth(Textures.hero.getWidth() / 12);
			height = MainActivity.calcHeight(Textures.hero.getHeight() / 8);
		}

		public Hunter getHunter() {
			return new Hunter(x, y, width, height, charAttr);
		}

		public Builder setX(float x) {
			this.x = x;
			return this;
		}

		public Builder setY(float y) {
			this.y = y;
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
		if(attacker instanceof Bunny) {
			factor = 0.5f; 
		}else if(attacker instanceof Wolf) {
			factor = 5.0f;
		}
		return factor;
	}
}
