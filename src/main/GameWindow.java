package main;

import javax.swing.JFrame;
import java.awt.BorderLayout;

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
	}

	public JFrame getJFrame()
	{
		return jframe;
	}
}
