/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: This class has all the player's states in the game.
 */

package utils;

public enum PlayerState {
	ATTACK(8, 15),
	FALLING(2, 20),
	IDLE(4, 45),
	RUNNING(8, 15),
	JUMP(3, 20),
	HURT(1, 20);

	private int spriteAmount;
	private int spriteSpeed;

	PlayerState(int spriteAmount, int spriteSpeed) {
		this.spriteAmount = spriteAmount;
		this.spriteSpeed = spriteSpeed;
	}

	public int getSpriteAmount() {
		return spriteAmount;
	}

	public int getSpriteSpeed() {
		return spriteSpeed;
	}
}
