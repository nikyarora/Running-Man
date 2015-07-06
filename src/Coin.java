import java.awt.Graphics;
import java.awt.Image;

/**
 * Coin Class
 * 
 * This class controls the coin seen on the bricks on 
 * the screen. It has getters, setters, a constructor,
 * a move method, and a draw method.
 */
public class Coin 
{
	public int x, y;
	public static int coinSize;
	private int dx, dy;
	public int startY;
	
	/**
	 * Coin Constructor
	 * @param xIn
	 * @param yIn
	 * @param coinSizeIn
	 * @param dxIn
	 * @param dyIn
	 */
	public Coin(int xIn, int yIn, int coinSizeIn, int dxIn, int dyIn)
	{
		x = xIn;
		y = yIn;
		coinSize = coinSizeIn;
		dx = dxIn;
		dy = dyIn;
		
		startY = yIn;
	}
	
	/**
	 * Moves the coins
	 */
	public void move()
	{
		x = x + dx;
		y = y + dy;
	}
	
	/**
	 * Bounces the coins
	 * @param xLow
	 * @param xHigh
	 * @param yLow
	 * @param yHigh
	 */
	public void bounce(int xLow, int xHigh, int yLow, int yHigh)
	{
		if ((y - coinSize/2 <= yLow && dy < 0) || (y + coinSize/2 >= yHigh && dy > 0))
		{
			dy = -dy;
		}
	}
	
	/**
	 * Wraps the coins
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
	 * Draws the coins
	 * @param g
	 * @param coin
	 */
	public void draw(Graphics g, Image coin)
	{
		g.drawImage(coin, x, y, coinSize, coinSize, null);
	}
}
