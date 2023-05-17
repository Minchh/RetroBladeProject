package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import inputs.Input;
import level.Level;
import level.Tile;
import main.Game;
import utils.LoadSave;
import utils.PlayerState;
import utils.SpriteConsts;
import utils.Vector2;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex;
	private PlayerState playerAction;
	private boolean isMoving, isAttacking, isOnGround, isOutOfBoundX, isOutOfBoundY;
	Vector2 direction;
	int playerSpeed;
	private float xDrawOffset = 54 * Game.SCALE;
	private float yDrawOffset = 52 * Game.SCALE;
	private float offsetLeftAnimation = 6 * Game.SCALE;
	private int flipX, flipW;

	private float gravity;
	private float jumpSpeed;

	private Level currentLevel;
	private Input input;

	// Status Bar UI
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int)(192 * Game.SCALE);
	private int statusBarHeight = (int)(58 * Game.SCALE);
	private int statusBarX = (int)(10 * Game.SCALE);
	private int statusBarY = (int)(10 * Game.SCALE);

	private int healthBarWidth = (int)(150 * Game.SCALE);
	private int healthBarHeight = (int)(4 * Game.SCALE);
	private int healthBarXStart = (int)(34 * Game.SCALE);
	private int healthBarYStart = (int)(14 * Game.SCALE);

	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;

	// AttackBox
	private Rectangle2D.Float attackBox;

	public Player(float x, float y, int width, int height, Input input) {
		super(x, y, width, height);

		this.input = input;

		playerAction = PlayerState.IDLE;
		isMoving = false;
		isAttacking = false;
		isOnGround = false;
		isOutOfBoundX = false;
		isOutOfBoundY = false;

		// Movement
		direction = new Vector2(1, 1);
		playerSpeed = (int)(1 * Game.SCALE);
		gravity = 0.04f * Game.SCALE;
		jumpSpeed = -2.30f * Game.SCALE;

		// Fliping the player
		flipX = 0;
		flipW = 1;

		loadAnimations();
		initRect(x, y, 14 * Game.SCALE, 44 * Game.SCALE);
		initAttackBox();
	}

	private void initAttackBox()
	{
		attackBox = new Rectangle2D.Float(getX(), getY(), (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
	}



	public void update()
	{
		updateHealthBar();
		updateAttackBox();

		updateDirections();
		updateAttacking();

		horizontalMovementCollision();
		verticalMovementCollision();

		updateAnimationTick();
		setAnimations();
	}

	private void updateAttackBox()
	{
		if (direction.getX() == 1)
		{
			attackBox.x = getRect().x + getRect().width + (int)(Game.SCALE * 10);
		}
		else if (direction.getX() == -1)
		{
			attackBox.x = getRect().x - getRect().width - (int)(Game.SCALE * 10);
		}
		attackBox.y = getRect().y + (Game.SCALE * 10);
	}

	private void updateHealthBar()
	{
		healthWidth = (int)((currentHealth / (float)maxHealth) * healthBarWidth);
	}

	public void render(Graphics g, int lvlOffset)
	{
		if (flipW > 0)
		{
			g.drawImage(animations[playerAction.ordinal()][aniIndex],
					(int)(getRect().x - xDrawOffset) + flipX - lvlOffset, (int)(getRect().y - yDrawOffset),
					getWidth() * flipW, getHeight(), null);
		}
		else
		{
			g.drawImage(animations[playerAction.ordinal()][aniIndex],
					(int)(getRect().x - xDrawOffset - offsetLeftAnimation) + flipX - lvlOffset, (int)(getRect().y - yDrawOffset),
					getWidth() * flipW, getHeight(), null);
		}

		renderUI(g);

		// For debugging only
//		renderRect(g, lvlOffset);
//		renderAttackBox(g, lvlOffset);
	}

	private void renderUI(Graphics g)
	{
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.RED);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}

	private void renderAttackBox(Graphics g, int xLvlOffset)
	{
		g.setColor(Color.RED);
		g.drawRect((int)attackBox.x - xLvlOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}

	public void setCurrentLevel(Level level)
	{
		this.currentLevel = level;
	}

	private void updateAnimationTick()
	{
		aniTick++;
		if (aniTick >= playerAction.getSpriteSpeed())
		{
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= playerAction.getSpriteAmount())
			{
				aniIndex = 0;
				isAttacking = false;
			}
		}
	}

	private void setAnimations()
	{
		PlayerState startAni = playerAction;

		if (isMoving)
			playerAction = PlayerState.RUNNING;
		else
			playerAction = PlayerState.IDLE;

		if (!isOnGround)
		{
			if (direction.getY() < 0)
				playerAction = PlayerState.JUMP;
			else
				playerAction = PlayerState.FALLING;
		}


		if (isAttacking)
			playerAction = PlayerState.ATTACK;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick()
	{
		aniTick = 0;
		aniIndex = 0;
	}

	private void updateAttacking()
	{
		if (input.isButtonDown(MouseEvent.BUTTON1))
		{
			isAttacking = true;
		}

	}

	private void updateDirections()
	{
		isMoving = false;
		boolean left = input.isKey(KeyEvent.VK_A);
		boolean right = input.isKey(KeyEvent.VK_D);

		direction.setX(0);

		if (left && right || !left && !right)
			return;

		if (isOnGround && isAttacking)
			return;

		if (left)
		{
			direction.setX(-1);
			flipX = getWidth();
			flipW = -1;
			isMoving = true;
		}
		if (right)
		{
			direction.setX(1);
			flipX = 0;
			flipW = 1;
			isMoving = true;
		}
	}

	private void applyGravity()
	{
		direction.setY(direction.getY() + gravity);
	}

	private void jump()
	{
		isOnGround = true;
		direction.setY(jumpSpeed);
	}

	private void updatePositionHorizontal()
	{
		isOutOfBoundX = false;
		float changeX;
		int maxWidth = currentLevel.getFirstLayer()[0].length * Game.TILE_WIDTH;

		if (isMoving)
		{
			changeX = direction.getX() * playerSpeed;
			if (getRect().x + changeX < 0 || getRect().x + changeX >= maxWidth - getRect().width)
				isOutOfBoundX = true;
			else
				getRect().x += changeX;
		}
	}

	private void updatePositionVertical()
	{
		isOutOfBoundY = false;
		if (input.isKeyDown(KeyEvent.VK_SPACE) && isOnGround)
			jump();

		applyGravity();
		float changeY;
		changeY = direction.getY();
		if (getRect().y + changeY < 0 || getRect().y >= Game.GAME_HEIGHT)
			isOutOfBoundY = true;
		else
			getRect().y += direction.getY();

	}

	private void verticalMovementCollision()
	{
		updatePositionVertical();

		if (isOutOfBoundY)
		{
			resetPlayerPos();
			return;
		}

		for (Tile t : currentLevel.getTiles())
		{
			if (t.getRect().intersects(getRect()))
			{
				if (direction.getY() > 0)
				{
					getRect().y = t.getRect().y - getRect().height;
					direction.setY(0);
					isOnGround = true;
				}
				else if (direction.getY() < 0)
				{
					getRect().y = t.getRect().y + t.getRect().height;
					direction.setY(0);
				}
			}
		}


		if (isOnGround && direction.getY() < 0 || direction.getY() > 0)
			isOnGround = false;
	}

	private void horizontalMovementCollision()
	{
		updatePositionHorizontal();

		if (isOutOfBoundX)
			return;

		for (Tile t : currentLevel.getTiles())
			if (t.getRect().intersects(getRect()))
			{
				if (direction.getX() > 0)
					getRect().x = t.getRect().x - getRect().width;
				else if (direction.getX() < 0)
					getRect().x = t.getRect().x + t.getRect().width;
			}
	}

	public void changeHealth(int value)
	{
		currentHealth += value;

		if (currentHealth <= 0)
		{
			currentHealth = 0;
			// Game Over
		}
		else if (currentHealth >= maxHealth)
		{
			currentHealth = maxHealth;
		}
	}

	private void loadAnimations()
	{
		BufferedImage img = LoadSave.GetSpriteSheet(LoadSave.PLAYER_SPRITE_SHEET);

		animations = new BufferedImage[6][8];
		for (int y = 0; y < animations.length; y++)
			for (int x = 0; x < animations[y].length; x++)
				animations[y][x] = img.getSubimage(x * SpriteConsts.PLAYER.getSpriteWidth(), y * SpriteConsts.PLAYER.getSpriteHeight(),
						SpriteConsts.PLAYER.getSpriteWidth(), SpriteConsts.PLAYER.getSpriteHeight());

		statusBarImg = LoadSave.GetSpriteSheet(LoadSave.STATUS_BAR);

	}

	public Vector2 getDirection()
	{
		return direction;
	}

	public void windowLostFocus()
	{
		direction.setX(0);
		direction.setY(0);
	}

	public void resetPlayerPos()
	{
		getRect().x = 100 * Game.SCALE;
		getRect().y = 100 * Game.SCALE;
		direction.setX(0);
		direction.setY(0);
	}
}
