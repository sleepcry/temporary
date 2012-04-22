package com.ormgas.rokonpong.statemachine;

public enum CharacterState {
	FREEMOVE(0),REST(1),ATTACK(2),MOVEAWAY(3),MOVECLOSE(4);
	int id;
	private CharacterState(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
