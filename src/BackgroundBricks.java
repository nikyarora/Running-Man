import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JFrame;

/**
 * Background Bricks Class
 * 
 * This class controls the background bricks that
 * move in the background. It has a move method, getters,
 * and setters, and a draw method.
 *
 */
public class BackgroundBricks extends JFrame
{
	// DATA:
	private int x, y;						
	private int dx;
	private int rectSizeX;
	private static int rectSizeY;	

	/**
	 * Constructor
	 * @param xIn
	 * @param yIn
	 * @param rectSizeXIn
	 * @param rectSizeYIn
	 * @param dxIn
	 */
	public BackgroundBricks(int xIn, int yIn, int rectSizeXIn, int rectSizeYIn, int dxIn)
	{
		// Nothing to do but save the data in the object's data fields.
		x = xIn;
		y = yIn;
		rectSizeX = rectSizeXIn;
		rectSizeY = rectSizeYIn;
		dx = dxIn;

	}

	/**
	 * Moves the bricks
	 */
	public void move()
	{
		x = x + dx;
	}
	
	//Getters
	public int getdX()
	{
		return dx;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Rectangle getRect()
	{
		return new Rectangle (x, y, rectSizeX, rectSizeY);
	}

	/**
	 * Wraps the bricks around the screen
	 * @param xLow
	 * @param xHigh
	 * @param yLow
	 * @param yHigh
	 */
	public void wrap(int xLow, int xHigh, int yLow, int yHigh)
	{
		if(x >= xHigh)
		{
			x = x - xHigh;
		}
		else if(x <= xLow)
		{
			x = x + xHigh;
		}
		else if(y >= yHigh)
		{
			y = y - yHigh;
		}
		else if(y <= yLow)
		{
			y = y + yHigh;
		}
	}

	/**
	 * Draws the bricks
	 * @param g
	 */
	public void draw(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(x, y, rectSizeX, rectSizeY);
	}
}
