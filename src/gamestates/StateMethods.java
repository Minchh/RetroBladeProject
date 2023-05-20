/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: All methods that each state of the game should have.
 */

package gamestates;

import java.awt.Graphics;

public interface StateMethods
{
	public void update();
	public void render(Graphics g);
}
