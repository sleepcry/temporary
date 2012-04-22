package com.ormgas.rokonpong;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ormgas.rokonpong.statemachine.CharacterState;
import com.ormgas.rokonpong.statemachine.StateMachine;
import com.ormgas.rokonpong.status.CharacterAttributes;
import com.ormgas.rokonpong.status.PlayerStatus;
import com.stickycoding.rokon.GameObject;
import com.stickycoding.rokon.PhysicalSprite;

public class GamePlayer extends PhysicalSprite {
	public static final int LEFT_BEGIN = 36;
	public static final int UP_BEGIN = 0;
	public static final int DOWN_BEGIN = 24;
	public static final int RIGHT_BEGIN = 12;
	public static final double PI_RATIO = 180.0/3.14159265;

	// frame count
	int updateCnt = 0;
	// texture base
	int base = 0;
	// AI or player
	boolean isAuto = true;
	CharacterState cs = CharacterState.REST;
	// accelerate flag
	boolean isAccelerating = false;
	Vector2 accPos;
	// charactor status
	PlayerStatus status;
	GameObject target;
	List<StatusMoniter> moniter;
	StateMachine sm;

	public GamePlayer(float x, float y, float width, float height,
			CharacterAttributes charAttr, int base) {
		super(x, y, width, height);
		this.base = base;
		FixtureDef fixture = new FixtureDef();
		fixture.restitution = 0.0f;
		fixture.density = 0.0f;
		fixture.friction = 0.1f;
		createDynamicBox(fixture);
		body.setLinearDamping(0.0f);
		body.setUserData(this);
		setTextureTile(Textures.hero, base + DOWN_BEGIN);
		status = new PlayerStatus(charAttr);
		moniter = new ArrayList<StatusMoniter>();
	}

	public void onUpdate() {
		// position update
		super.onUpdate();
		// texture update
		Vector2 velocity = getBody().getLinearVelocity();
		if (Math.abs(velocity.x) > Math.abs(velocity.y)) {
			if (velocity.x > 0) {
				// right
				setTextureTile(base + RIGHT_BEGIN + (int) updateCnt % 36 / 12);
			} else if (velocity.x < 0) {
				// left
				setTextureTile(base + LEFT_BEGIN + (int) updateCnt % 36 / 12);
			}
		} else {
			if (velocity.y < 0) {
				// up
				setTextureTile(base + UP_BEGIN + (int) updateCnt % 36 / 12);
			} else if (velocity.y > 0) {
				// down
				setTextureTile(base + DOWN_BEGIN + (int) updateCnt % 36 / 12);
			}
		}

		updateCnt++;
		if (updateCnt >= 36) {
			updateCnt = 0;
		}

		// movement update
		if (isAccelerating && (updateCnt % 5) == 0) {
			if (usePower(status.accPower)) {
				getBody().applyLinearImpulse(
						calcPower(accPos.x - getBody().getPosition().x,
								accPos.y - getBody().getPosition().y), accPos);
			}
		}

		// auto update
		if ((updateCnt % 3) == 0) {
			if (isAuto) {
				sm.onUpdate();
			}else {
				manualDo();
			}
		}

		// status update
		status.onUpdate();
		updateStatus();
	}

	private void manualDo() {
		switch (cs) {
		case ATTACK:
			if (!target.isAlive()) {
				cs = CharacterState.REST;
			} else if (target != null && target instanceof GamePlayer) {
				Vector2 targetPos = new Vector2(target.x, target.y);
				if (targetPos.dst(body.getPosition()) <= status.attackRange) {
					((GamePlayer) target).attacked(this);
				} else {
					cs = CharacterState.REST;
					target = null;
				}
			}
			Log.d("status", "ATTACK");
			break;
		}
		
	}

	public Vector2 calcPower(Vector2 direction) {
		Vector2 newVector = new Vector2();
		float len = direction.len();
		newVector.x = status.accPower * direction.x / len;
		newVector.y = status.accPower * direction.y / len;
		return newVector;
	}

	public Vector2 calcPower(float x, float y) {
		return calcPower(new Vector2(x, y));
	}
	public Vector2 calcEscapeRoute(float x, float y) {
		return calcEscapeRoute(new Vector2(x, y));
	}
	public Vector2 calcEscapeRoute(Vector2 direction) {
		Vector2 newVector = new Vector2();
		float len = direction.len();
		double angle = calcAngle(direction.x,direction.y) + (Math.random()-0.5)*45;
		newVector.x = (float) (status.accPower * Math.cos(angle));
		newVector.y = (float) (status.accPower * Math.sin(angle));
		return newVector;
	}
	public double calcAngle(float x, float y) {
		Vector2 newVector = new Vector2(x,y);
		float len = newVector.len();
		if(Math.abs(len) <= 0.00001f) {
			return 0;
		}
		if(x >=0 && y >= 0) {
			return PI_RATIO*Math.asin(newVector.x/len);
		}else if(x < 0 && y >= 0) {
			return PI_RATIO*Math.asin(newVector.x/len)+180.0;
		}else if(x < 0 && y < 0) {
			return PI_RATIO*Math.atan(newVector.y/newVector.x) + 180;
		}else {
			return PI_RATIO*Math.asin(newVector.x/len) + 360;
		}
	}
	public void accelerate(Vector2 pos) {
		if (status.currentPower >= status.accPower) {
			this.isAccelerating = true;
			accPos = pos;
		}
	}

	public void stopAccelerate() {
		this.isAccelerating = false;
	}

	public void setAuto() {
		this.isAuto = true;
		sm = StateMachine.getStateMachine(this);
	}

	public GamePlayer manual() {
		this.isAuto = false;
		sm = null;
		return this;
	}
	
	public void attacked(GamePlayer attacker) {
		status.currentLife -= attacker.status.attack
				* attacker.body.getLinearVelocity()
						.sub(body.getLinearVelocity()).len()
				* PlayerStatus.VELOCITY_FACTOER * getAttackFactor(attacker);
		if (status.currentLife <= 0) {
			status.currentLife = 0;
			remove();
		}else if(attacker != target) {
			status.status |= PlayerStatus.MASK_ATTACKED;
		}
	}

	public float getAttackFactor(GamePlayer target) {
		return 1.0f;
	}

	public void encounter(GameObject object2) {
		if (target == null) {
			setTarget(object2);
			if (!isAuto) {
				cs = CharacterState.ATTACK;
			}
		}
	}

	public void addMoniter(StatusMoniter moniter) {
		this.moniter.add(moniter);
	}

	private void updateStatus() {
		for (int i = 0; i < moniter.size(); i++) {
			moniter.get(i).statusChanged(status);
		}
	}

	public boolean usePower(float used) {
		if (status.currentPower > used) {
			status.currentPower -= used;
			return true;
		}
		return false;
	}
	public GameObject getTarget() {
		return target;
	}

	public PlayerStatus getStatus() {
		
		return status;
	}

	public void setTarget(GameObject target) {
		this.target = target;		
	}
}
