package utils;

public enum SpriteConsts {
	PLAYER(128, 96),
	SLIME(32, 25),
	SKY(112, 304),
	CLOUD(554, 236),
	SEA(112, 96),
	FAR_GROUND(616, 110),
	BACKWALL(416, 0),
	SOUND_BUTTON(42, 42),
	URM_BUTTON(56, 56),
	VOLUME_BUTTON(28, 44),
	VOLUME_SLIDER(215, 0),
	MENU_BUTTON(140, 56);

	private int spriteWidth;
	private int spriteHeight;

	SpriteConsts(int spriteWidth, int spriteHeight) {
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}
}
