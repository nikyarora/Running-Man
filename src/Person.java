import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Person Class
 * 
 * This class controls the person running
 * on the screen. It has getters, setters, and
 * move methods.
 */
public class Person extends JFrame
{
	// DATA:
	public int x, y;						
	private int dx, dY;
	private static int personSize;	
	private int value = 0;
	private int lives = 3;

	/**
	 * Person Constructor
	 * @param xIn	X location of person
	 * @param yIn	Y location of person
	 * @param personSizeIn	Size of person
	 * @param dxIn	X velocity of person
	 * @param dyIn	Y velocity of person
	 */
	public Person(int xIn, int yIn, int personSizeIn, int dxIn, int dyIn)
	{
		x = xIn;
		y = yIn;
		personSize = personSizeIn;
		dx = dxIn;
		dY = dyIn;

	}
	
	/**
	 * collectCoin
	 * @param valueIn	Value of coin
	 */
	public void collectCoin(int valueIn)
	{
		value = value + valueIn;
		if (value % 100 == 0)
		{
			getLife();
		}
	}
	
	/**
	 * Moves the person horizontally
	 */
	public void move()
	{
		x = x + dx;
	}
	
	//Getters
	public void getLife()
	{
		lives ++;
	}
	
	public int getValue()
	{
		return value;
	}

	public int getdY()
	{
		return dY;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getdX()
	{
		return dx;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public int getPersonSize()
	{
		return personSize;
	}

	//Setters
	public void setY(int newY)
	{
		y = newY;
	}

	public void setdY(int newdY)
	{
		dY = newdY;
	}
	
	public void setdX(int newdX)
	{
		dx = newdX;
	}
	
	public void setLives(int newNumLives)
	{
		lives = newNumLives;
	}
	
	public void setX(int xVal)
	{
		x = xVal;
	}
	
	/**
	 * jump class - changes the person's y velocity for the jump
	 * @param dyIn
	 */
	public void jump(int dyIn)
	{
		y = y - dY;
	}

	/**
	 * stay class
	 */
	public void stay()
	{
		y = y + (-100);
	}
	
	/**
	 * takes one life from person
	 */
	public void takeLife()
	{
		lives --;
	}

	/**
	 * Moves the person down
	 * @param yInitial	Starting y location
	 */
	public void gravity(int yInitial)
	{
		dY = dY - 4;
	}

	/**
	 * bounce
	 */
	public void bounce()
	{
		dY = -dY;
	}
	
	/**
	 * Wraps the person around the screen
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
		
		else if(y >= yHigh)
		{
			y = y - yHigh;
		}
		
	}

	/**
	 * Draw the person
	 * @param g
	 * @param man
	 */
	public void draw(Graphics g, Image man)
	{
		g.drawImage(man, x, y, personSize, personSize, null);
	}
	
	/**
	 * Draws the person when dead
	 * @param g
	 * @param man
	 * @param red_x
	 */
	public void drawDead(Graphics g, Image man, Image red_x)
	{
		g.drawImage(man, 450, 500, personSize, personSize, null);
		g.drawImage(red_x, 450, 500, personSize, personSize, null);
	}
}
