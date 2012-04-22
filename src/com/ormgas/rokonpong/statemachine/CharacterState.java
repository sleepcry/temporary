package com.ormgas.rokonpong.statemachine;

public enum CharacterState {
	FREEMOVE(0),REST(1),ATTACK(2),ESCAPE(3),FOLLOW(4);
	int id;
	private CharacterState(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
