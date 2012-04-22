package com.ormgas.rokonpong.status;

import com.stickycoding.rokon.Updateable;

public class PlayerStatus implements Updateable {
	public float accPower;
	public float currentPower;
	public float powerRegenerate = 0.1f;
	public float maxPower = 10.0f;
	public float maxLife = 100;
	public float currentLife = 100;
	public float attack = 1.0f;
	
	public int status = 0;
	
	//static 
	//the velocity coefficient to attack
	public static final float VELOCITY_FACTOER = 1.0f;
	//attacked just now
	public static final int MASK_ATTACKED = 0x1;
	//power not enough
	public static final int MASK_POWERLOW = 0x2;
	//life not enough
	public static final int MASK_LIFELOW = 0x3;
	//state are promoted
	public static final int MASK_PROMOTED = 0x8;
	//state are weakened
	public static final int MASK_WEAKENED = 0x10;
	
	
	public CharacterAttributes characterAttr;
	public float attackRange;
	public float safeDst;
	
	public PlayerStatus(CharacterAttributes charAttr) {
		characterAttr = charAttr;
		maxPower = currentPower = charAttr.maxPower;
		maxLife = currentLife = charAttr.maxLife;
		attack = charAttr.attack;
		attackRange = charAttr.attackRange;
		safeDst = charAttr.safeDst;
		accPower = charAttr.accPower;
	}
	@Override
	public void onUpdate() {
		if(currentPower < characterAttr.maxPower) {
			currentPower += powerRegenerate;
		}
	}
}
