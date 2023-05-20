/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: All the game states.
 */

package gamestates;

public enum GameState
{
	PLAYING,
	MENU,
	OPTIONS,
	QUIT;

	public static GameState state = MENU;
}
