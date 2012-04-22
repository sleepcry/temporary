package com.ormgas.rokonpong.statemachine;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.status.Judgement;
import com.stickycoding.rokon.GameObject;

public class StateMoveClose extends State {

	protected StateMoveClose(GamePlayer character, StateMachine sm) {
		super(character, sm);
	}

	@Override
	public void onUpdate() {
		GameObject target = character.getTarget();
		if (target != null) {
			Vector2 targetPos = new Vector2(target.x, target.y);
			//if close enough
			if (targetPos.dst(character.getBody().getPosition()) <= character
					.getStatus().attackRange) {
				if (Judgement.shouldFight(character)) {
					changeState(CharacterState.ATTACK);
				}else {
					changeState(CharacterState.MOVEAWAY);
				}
			}
			//not close enough
			else if (character.usePower(character.getStatus().accPower)) {
				character.getBody().applyLinearImpulse(
						character.calcPower(targetPos.x
								- character.getBody().getPosition().x,
								targetPos.y
										- character.getBody().getPosition().y),
						targetPos);
			}
			//not enough power
			else {
				changeState(CharacterState.REST);
				character.setTarget(null);
			}
		}
		Log.d("sm", "MOVECLOSE");

	}

	@Override
	public void changeState(CharacterState newState) {
		switch (newState) {
		case REST:
		case ATTACK:
			sm.changeTo(newState);
			break;
		}

	}

}
