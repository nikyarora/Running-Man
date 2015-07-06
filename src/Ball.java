import java.awt.Graphics;
import java.awt.Image;

/**
 * Ball Class
 * 
 *This class controls the balls that fall out of the sky
 * on to the person. It has a move method, getters, setters,
 * and a draw method.
 */
public class Ball 
{
	public int x, y;
	public static int ballSize;
	private int dy;
	public int startY;
	
	/**
	 * Ball Constructor
	 * @param xIn
	 * @param yIn
	 * @param ballSizeIn
	 * @param dyIn
	 */
	public Ball(int xIn, int yIn, int ballSizeIn, int dyIn)
	{
		x = xIn;
		y = yIn;
		ballSize = ballSizeIn;
		dy = dyIn;
		startY = yIn;			
	}
	
	//Getters
	public int getXVal()
	{
		return x; 
	}
	
	public int getYVal()
	{
		return y;
	}
	
	public int getSize()
	{
		return ballSize;
	}
	
	/**
	 * Moves the balls
	 */
	public void move()
	{
		y = y + dy;
	}
	
	/**
	 * Draws the balls
	 * @param g
	 * @param ball
	 */
	public void draw(Graphics g, Image ball)
	{
		g.drawImage(ball, x, y, ballSize, ballSize, null);
	}

}
