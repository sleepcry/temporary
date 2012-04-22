package com.ormgas.rokonpong.statemachine;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.status.Judgement;
import com.stickycoding.rokon.GameObject;

public class StateFollow extends State {

	protected StateFollow(GamePlayer character, StateMachine sm) {
		super(character, sm);
	}

	// @Override
	// public void onUpdate() {
	//
	// // GameObject target = player.getTarget();
	// // if (target != null) {
	// // Vector2 targetPos = new Vector2(target.x, target.y);
	// // //if close enough
	// // if (targetPos.dst(player.getBody().getPosition()) <= player
	// // .getStatus().attackRange) {
	// // if (Judgement.shouldFight(player)) {
	// // changeState(CharacterState.ATTACK);
	// // }else {
	// // changeState(CharacterState.ESCAPE);
	// // }
	// // }
	// // //not close enough
	// // else if (player.usePower(player.getStatus().accPower)) {
	// // player.getBody().applyLinearImpulse(
	// // player.calcPower(targetPos.x
	// // - player.getBody().getPosition().x,
	// // targetPos.y
	// // - player.getBody().getPosition().y),
	// // targetPos);
	// // }
	// // //not enough power
	// // else {
	// // changeState(CharacterState.REST);
	// // player.setTarget(null);
	// // }
	// // }
	// Log.d("sm", "MOVECLOSE");
	//
	// }

	private boolean shouldStop() {
		return Judgement.shouldSeek(player);
	}

	@Override
	public void changeState(CharacterState newState) {
		sm.changeTo(newState);
	}

	@Override
	protected void action() {
		GameObject targetObj = player.getTarget();
		if (targetObj != null && targetObj.isAlive()) {
			Vector2 targetPos = new Vector2(targetObj.x, targetObj.y);
			if (player.usePower(player.getStatus().accPower)) {
				player.getBody().applyLinearImpulse(
						player.calcPower(targetPos.x
								- player.getBody().getPosition().x, targetPos.y
								- player.getBody().getPosition().y), targetPos);
			}
		}
	}

	@Override
	protected void update() {
		if (attacked()) {
			// interrupted by a attack
			if (Judgement.shouldFight(player)) {
				changeState(CharacterState.ATTACK);
			} else {
				changeState(CharacterState.ESCAPE);
			}
		} else if (followOver()) {
			if (Judgement.shouldFight(player)) {
				changeState(CharacterState.ATTACK);
			} else {
				GameObject targetObj = player.getTarget();
				if (targetObj != null && targetObj.isAlive()) {
					changeState(CharacterState.REST);
				} else {
					changeState(CharacterState.ESCAPE);
				}
			}
		} else if (shouldStop()) {
			// stopped by self
			changeState(CharacterState.REST);
		}
		Log.d("sm", "FOLLOW");	
	}

	private boolean followOver() {	
		GameObject targetObj = player.getTarget();	
		if (targetObj != null && targetObj.isAlive()) {
			Vector2 targetPos = new Vector2(targetObj.x, targetObj.y);
			if(targetPos.dst(player.getBody().getPosition()) <= player.getStatus().attackRange) {
				return true;
			}else {
				return false;
			}
		}else {
			return true;
		}
	}

}
