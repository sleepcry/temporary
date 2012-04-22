package com.ormgas.rokonpong.statemachine;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.ormgas.rokonpong.GamePlayer;
import com.stickycoding.rokon.GameObject;

public class StateMoveAway extends State {
	
	protected StateMoveAway(GamePlayer character,StateMachine sm) {
		super(character,sm);
	}
	
	@Override
	public void onUpdate() {
		GameObject target = character.getTarget();
		if (target != null) {
			Vector2 targetPos = new Vector2(target.x, target.y);
			if (targetPos.dst(character.getBody().getPosition()) >= character.getStatus().safeDst) {
				changeState(CharacterState.REST);
				character.setTarget(null);
			} else if (character.usePower(character.getStatus().accPower)) {
				character.getBody().applyLinearImpulse(
						character.calcPower(character.calcEscapeRoute(character.getBody().getPosition().x - targetPos.x,
								character.getBody().getPosition().y - targetPos.y)),
						targetPos);
			} else {
				changeState(CharacterState.ATTACK);
			}
		}else {
			changeState(CharacterState.REST);
		}
		Log.d("sm", "MOVEAWAY");
	}

	@Override
	public void changeState(CharacterState newState) {
		switch(newState) {
		case REST:
		case ATTACK:
			sm.changeTo(newState);
			break;
			
		}
	}

}
