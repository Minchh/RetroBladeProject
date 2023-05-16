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
