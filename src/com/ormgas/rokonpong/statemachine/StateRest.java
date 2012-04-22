package com.ormgas.rokonpong.statemachine;

import java.util.List;

import android.util.Log;

import com.ormgas.rokonpong.GamePlayer;
import com.ormgas.rokonpong.PlayerManager;
import com.ormgas.rokonpong.statemachine.PlayerActionInterface.Action;
import com.ormgas.rokonpong.status.Judgement;

public class StateRest extends State {

	protected StateRest(GamePlayer character, StateMachine sm) {
		super(character, sm);
	}
	
	@Override
	public void changeState(CharacterState newState) {
		sm.changeTo(newState);
	}

	@Override
	protected void update() {
		if (attacked()) {
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
		}else if(Judgement.shouldFreeMove(player)) {
			changeState(CharacterState.FREEMOVE);
		}		
		Log.d("sm", "REST");	
	}

	

	@Override
	protected void action() {		
	}

//	@Override
//	protected void update() {
//		
//			
//		
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
////					if (anotherDst < dst) {
////						followingTarget = p;
////						dst = anotherDst;
////					}
////				}
////				if (followingTarget != null) {
////					player.setTarget(followingTarget);
////					changeState(CharacterState.MOVECLOSE);
////				}
////			}
////		}
////		//if should freely move, look forward to some fortune
////		else if(Judgement.shouldFreeMove(player)){
////			changeState(CharacterState.FREEMOVE);
////		}
//
//		Log.d("sm", "REST");	
//	}

}
