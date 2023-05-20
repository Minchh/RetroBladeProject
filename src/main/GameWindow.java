/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: This class creates window for the game.
 */

package main;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow
{
	private final JFrame jframe;

	public GameWindow(GamePanel gamePanel)
	{
		jframe = new JFrame(Game.GAME_TITLE);

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setLayout(new BorderLayout());
		jframe.add(gamePanel, BorderLayout.CENTER);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setResizable(false);
		jframe.setVisible(true);
		jframe.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowLostFocus();
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				gamePanel.getGame().windowGainFocus();
			}
		});
	}

	public JFrame getJFrame()
	{
		return jframe;
	}
}
