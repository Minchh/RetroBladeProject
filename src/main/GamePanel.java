/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: This class help to display art to the screen.
 */

package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

public class GamePanel extends JPanel
{
	private Game game;

	public GamePanel(Game game)
	{
		setPanelSize();
		this.game = game;

		addKeyListener(game.getInput());
		addMouseListener(game.getInput());
		addMouseMotionListener(game.getInput());
		addMouseWheelListener(game.getInput());

	}

	private void setPanelSize()
	{
		Dimension d = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		game.render(g);
	}

	public Game getGame()
	{
		return game;
	}
}
