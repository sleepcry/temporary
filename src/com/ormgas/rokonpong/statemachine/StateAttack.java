package com.ormgas.rokonpong.statemachine;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.status.Judgement;
import com.stickycoding.rokon.GameObject;

public class StateAttack extends State {

	protected StateAttack(GamePlayer character,StateMachine sm) {
		super(character,sm);
	}

	@Override
	public void onUpdate() {
		GameObject target = character.getTarget();
		if (!target.isAlive()) {
			changeState(CharacterState.REST);
		} else if (target != null && target instanceof GamePlayer) {
			GamePlayer player = (GamePlayer)target;
			Vector2 targetPos = new Vector2(target.x, target.y);
			if (targetPos.dst(character.getBody().getPosition()) <= character.getStatus().attackRange) {
				player.attacked(character);
			} else {
				changeState(CharacterState.REST);
				character.setTarget(null);
			}
			if(Judgement.nonsense() && Judgement.shouldEscape(character)) {
				changeState(CharacterState.MOVEAWAY);
			}
		}
		Log.d("sm", "ATTACK");
	}

	@Override
	public void changeState(CharacterState newState) {
		sm.changeTo(newState);
	}

}
