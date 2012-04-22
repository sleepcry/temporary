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


	private boolean shouldStop() {
		return !Judgement.shouldFight(player);
	}

	private boolean shouldChangeTarget() {
		return true;
	}

	@Override
	public void changeState(CharacterState newState) {
		sm.changeTo(newState);
	}

	@Override
	protected void action() {
		GameObject targetObj = player.getTarget();
		if (targetObj != null && targetObj.isAlive() && targetObj instanceof GamePlayer) {
			GamePlayer targetplayer = (GamePlayer)targetObj;
			if (targetplayer.body.getPosition().dst(player.getBody().getPosition()) <= player.getStatus().attackRange) {
				targetplayer.attacked(player);
			}
		}		
	}

	@Override
	protected void update() {
		if (attacked()) {
			if(Judgement.shouldEscape(player)) {
				changeState(CharacterState.ESCAPE);
			}else if (shouldChangeTarget()) {
				player.changeTarget();
			}
		}else if(shouldStop()) {
			changeState(CharacterState.ESCAPE);
		}else {
			GameObject targetObj = player.getTarget();
			if (targetObj instanceof GamePlayer) {
				GamePlayer targetplayer = (GamePlayer)targetObj;
				if (targetplayer.body.getPosition().dst(player.getBody().getPosition()) > player.getStatus().attackRange) {
					changeState(CharacterState.FOLLOW);
				}
			}	
		}
		
		Log.d("sm", "ATTACK");		
	}

}
