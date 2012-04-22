package com.ormgas.rokonpong.statemachine;

import java.util.List;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.PlayerManager;
import com.ormgas.rokonpong.status.Judgement;
import com.ormgas.rokonpong.status.PlayerStatus;

public class StateFreeMove extends State {

	protected StateFreeMove(GamePlayer character,StateMachine sm) {
		super(character,sm);
	}
//	public void onUpdate() {
//		
//		//current action
////		if (Judgement.nonsense() && player.usePower(player.getStatus().accPower)) {
////			Vector2 dir = new Vector2();
////			dir.x = (float) Math.random() - 0.5f;
////			dir.y = (float) Math.random() - 0.5f;
////			player.getBody().applyLinearImpulse(player.calcPower(dir.x, dir.y),
////					player.getBody().getPosition());
////		} 
////		//next action
////		//if should seek some enemy, seek one
////		if (Judgement.shouldSeek(player)) {
////			List<GamePlayer> lst = PlayerManager.seekPlayer(player.getBody()
////					.getPosition(), 3);
////			if (lst != null && lst.size() > 0 ) {
////				GamePlayer followingTarget = null;
////				float dst = 1000.0f;
////				for (int i = 0; i < lst.size(); i++) {
////					GamePlayer p = lst.get(i);
////					if(p == player)continue;
////					float anotherDst = p.getBody().getPosition()
////							.dst(player.getBody().getPosition());
////					if (anotherDst < dst ) {
////						followingTarget = p;
////						dst = anotherDst;
////					}
////				}
////				if (followingTarget != null) {
////					player.setTarget(followingTarget);
////					changeState(CharacterState.FOLLOW);
////				}
////			}
////		}
////		//some chance to end free walk
////		else if(Judgement.nonsense()){
////			sm.changeTo(CharacterState.REST);
////		}
//		Log.d("sm", "FREEMOVE");
//	}
	@Override
	public void changeState(CharacterState newState) {
		sm.changeTo(newState);		
	}
	@Override
	protected void action() {
		if (Judgement.nonsense() && player.usePower(player.getStatus().accPower)) {
			Vector2 dir = new Vector2();
			dir.x = (float) Math.random() - 0.5f;
			dir.y = (float) Math.random() - 0.5f;
			player.getBody().applyLinearImpulse(player.calcPower(dir.x, dir.y),
					player.getBody().getPosition());
		} 
		
	}
	@Override
	protected void update() {
		if (attacked()) {
			player.clearAttack();
			//interrupted by a attack
			if (Judgement.shouldFight(player)) {
				changeState(CharacterState.ATTACK);
			} else {
				changeState(CharacterState.ESCAPE);
			}
		}else if(Judgement.shouldFight(player)) {
			changeState(CharacterState.ATTACK);
		}
		//if should seek a enemy, seek one
		else if (Judgement.shouldSeek(player)) {
			if(player.seek()) {
				changeState(CharacterState.FOLLOW);
			}
				
		}else if(Judgement.shouldEscape(player)) {
			changeState(CharacterState.ESCAPE);
		}else if(!Judgement.shouldFreeMove(player)) {
			changeState(CharacterState.REST);
		}	
		Log.d("sm", "WALK");		
	}

}
