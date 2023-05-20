/*
Name: Group 11
Member names & IU code:
	- Đỗ Nguyễn Bình Minh - ITCSIU21201
	- Trần Thanh Hiếu - ITCSIU21179
	- Hồ Anh Dũng - ITCSIU21172
Purpose: This class helps us transmit all keyboard and mouse to the game, to help us control our player and
			interact with the menu.
 */

package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{

	private int NUM_KEYS = KeyEvent.VK_WINDOWS + 1;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keysLast = new boolean[NUM_KEYS];

	private int NUM_BUTTONS = 5;
	private boolean[] buttons = new boolean[NUM_BUTTONS];
	private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

	private int mouseX, mouseY, scroll;
	private int mouseXLast, mouseYLast;

	public Input()
	{
		mouseXLast = 0;
		mouseYLast = 0;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
	}

	public void reset() {
		for (int i = 0; i < NUM_KEYS; i++)
			keys[i] = false;

		for (int i = 0; i < NUM_BUTTONS; i++)
			buttons[i] = false;
	}

	public void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			keysLast[i] = keys[i];
		}

		for (int i = 0; i < NUM_BUTTONS; i++) {
			buttonsLast[i]= buttons[i];
		}

		mouseXLast = mouseX;
		mouseYLast = mouseY;
	}

	public boolean isKey(int keyCode) {
		return keys[keyCode];
	}

	public boolean isKeyDown(int keyCode) {
		return keys[keyCode] && !keysLast[keyCode];
	}

	public boolean isKeyUp(int keyCode) {
		return !keys[keyCode] && keysLast[keyCode];
	}

	public boolean isButton(int button) {
		return buttons[button];
	}

	public boolean isButtonDown(int button) {
		return buttons[button] && !buttonsLast[button];
	}

	public boolean isButtonUp(int button) {
		return !buttons[button] && buttonsLast[button];
	}

	public boolean isMouseMoved()
	{
		return (mouseX != mouseXLast) || (mouseY != mouseYLast);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		buttons[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		scroll = e.getWheelRotation();
	}

	public int getMouseX()
	{
		return mouseX;
	}

	public int getMouseY()
	{
		return mouseY;
	}

	public int getScroll()
	{
		return scroll;
	}

}