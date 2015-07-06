import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

/*********************************************************************************
 * Names: Mina Mahmood, Nikhar Arora, Anika Padwekar
 * Class: AP Computer Science - A Block
 * Date: May 26, 2015
 * 
 * Final Project
 * This project is a game that consists of 5 classes. The start
 * screen is the first screen seen and it shows rules and has
 * a button to start the game. A man is shown who has three lives.
 * The goal of the game is to jump (using the spacebar) on the bricks 
 * and collect coins, while avoiding the balls falling out of the sky. Also,
 * if the user hits the top of the screen, right of the screen, or bottom of
 * the screen, they lose a life. Once the user loses three lives, the game is
 * over. The classes in the program include:
 * 
 * -RunningMan Class - this is the class with the main where the graphics
 * are made and the game is constructed. It implements MouseListener, KeyListener,
 * ActionListener, and the JFrame
 * 
 * -Person Class - this is the class where the person is constructed with a x
 * and y location, a x and y velocity, and a size
 * 
 * -Coin Class - this is the class where the coins are constructed with a 
 * x and y location, a x and y velocity, and a size
 * 
 * -BackgroundBrick - this is the class where the bricks are constructed with a
 * x and y location, a x velocity, and a x and y size
 * 
 * -Ball Class - this is the class where the balls are constructed with a x and y
 * location, a y velocity, a size, and a start y location
 *
 ******************************************************************************/
public class RunningMan extends JFrame 
implements KeyListener, MouseListener, ActionListener
{
	//Objects That Appear on the Screen
	private static Person person;
	private static ArrayList<Coin> coins = new ArrayList<Coin>();
	private static ArrayList<BackgroundBricks> bricks = new ArrayList<BackgroundBricks>();
	private static ArrayList<Ball> balls = new ArrayList<Ball>();
	private static BackgroundBricks startBrick;

	//Screen Size Variables
	private static final int MAX_WIDTH = 1000;	
	private static final int MIN_WIDTH = 0;
	private static final int MAX_HEIGHT = 600;		
	private static final int MIN_HEIGHT = 22;

	//Start Variables for the Person
	private static final int PERSON_START_X = 170;
	private static final int PERSON_START_Y = 460;
	private static final int PERSON_SIZE = 100;
	private static final int START_VELOCITY = 5;

	//Other Final Variables
	private static final int START_VELOCITY_BACK = -10;

	public static final int VERTICAL_X_1 = (MAX_WIDTH/2) - 100;
	public static final int VERTICAL_Y_1 = 400;

	private static int DELAY_IN_MILLISEC = 150;

	//Status Final Variables
	private static final int START_SCREEN = 0;
	private static final int RUNNING_SCREEN = 2;
	private static final int GAME_OVER_SCREEN = 3;
	private static final int LOSE_LIFE_SCREEN = 4; 
	private static int status = START_SCREEN;

	private static int time_check = 29;

	private static int goingThroughScreen = 0;

	//Boolean Variables
	private static boolean on_floor = true;
	private static boolean hitWall = false;

	private static int timeClicks = 0;
	
	private static boolean max_reached = false;

	//Image Variables
	private static Image background;
	private static Image man;
	private static Image coin;
	private static Image red_x;
	private static Image ball;

	/**
	 * Constructor: Makes the objects
	 */
	public RunningMan()
	{
		makePerson();
		makeFirstBrick();		
		makeFirstBall();

		setSize(MAX_WIDTH, MAX_HEIGHT);
		setVisible(true);		

		Timer clock= new Timer(DELAY_IN_MILLISEC, this);			
		clock.start();	
	}

	/**
	 * Main Class
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// Create the window.
		RunningMan gp = new RunningMan();
		gp.setSize(MAX_WIDTH, MAX_HEIGHT);
		gp.setVisible(true);
		background = new ImageIcon("background.jpg").getImage();
		man = new ImageIcon("man.gif").getImage();
		coin = new ImageIcon("coin.gif").getImage();
		red_x = new ImageIcon("red_x.png").getImage();
		ball = new ImageIcon("ball.png").getImage();
		gp.addMouseListener(gp);
		gp.addKeyListener(gp);
	}

	/**
	 * Create the person
	 */
	public static void makePerson()
	{
		int personSize = PERSON_SIZE;
		int x = PERSON_START_X;
		int y = PERSON_START_Y;
		int dx = START_VELOCITY;
		int dy = 0;

		person = new Person(x, y, personSize, dx, dy);
	}

	/**
	 * Resets all the variables
	 */
	public static void reset()
	{
		person.setX(PERSON_START_X);
		person.setY(PERSON_START_Y);
		person.setdX(START_VELOCITY);
		person.setdY(0);
		bricks = new ArrayList<BackgroundBricks>();
		coins = new ArrayList<Coin>();
		balls = new ArrayList<Ball>();
		makeFirstBrick();
		makeFirstBall();
	}

	/**
	 * Makes the first five bricks that are on the screen
	 */
	public static void makeFirstBrick()
	{
		for(int i = 0; i <= 4; i++)
		{
			int rectSizeX = 200;
			int x = (200 * i) + 150;
			int y = 550;
			int rectSizeY = 15;
			int dx = -20;

			startBrick = new BackgroundBricks(x,y, rectSizeX, rectSizeY, dx);
			bricks.add(startBrick);
		}
	}

	/**
	 * Makes the first ball obstacle
	 */
	public static void makeFirstBall()
	{
		int ballSize = 40;
		int x = (int)(Math.random() * (MAX_WIDTH - ballSize));
		int y = MIN_HEIGHT;
		int dy = (int)(Math.random() * 6) + 1;

		balls.add(new Ball(x, y, ballSize, dy));
	}
	
	/**
	 * Makes the rest of the bricks
	 * @return
	 */
	public static BackgroundBricks makeBrick()
	{	
		int rectSizeX = 200;
		BackgroundBricks previousBrick;

		previousBrick = bricks.get(bricks.size()-1);

		int previousBrickX = previousBrick.getX();
		int x = previousBrickX + 500;

		int y = (int)(250 + Math.random() * 220);
		int rectSizeY = 15;
		int dx = -25;

		BackgroundBricks newBrick = new BackgroundBricks(x, y, rectSizeX, rectSizeY, dx);
		bricks.add(newBrick);
		makeCoins(x + rectSizeX/2 -15, y - 28, dx);
		return newBrick;
	}

	/**
	 * Makes the coins and gives each brick one coin
	 * @param xIn
	 * @param yIn
	 * @param dxIn
	 */
	public static void makeCoins(int xIn, int yIn, int dxIn)
	{
		int coinSize = 30;
		int x = xIn;
		int y = yIn;
		int dx = dxIn;
		int dy = 0;
		for(int i = 0; i < 1; i++)
		{
			dy = (int)(Math.random() * 15);
			coins.add(new Coin(x, y, coinSize, dx, dy));
		}
	}

	/**
	 * Makes more ball obstacles
	 */
	public static void makeBall()
	{
		Ball previousBall;

		previousBall = balls.get(balls.size()-1);

		int previousBallX = previousBall.getXVal();
		int x = (previousBallX + 200) % MAX_WIDTH;
		int ballSize = 40;
		int y = MIN_HEIGHT;
		int dy = (int)(Math.random() * 6) + 1;

		balls.add(new Ball(x, y, ballSize, dy));
	}

	/**
	 * KeyPressed
	 */
	public void keyPressed(KeyEvent e)					
	{

		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE)
		{
			if (hitWall)
			{
				hitWall = false;
			}
			person.setdX(10);

			person.setdY(20);
			on_floor = false;

		}

		max_reached = false;

	}

	/**
	 * KeyTyped
	 */
	public void keyTyped(KeyEvent e)					
	{
		int keyCode = e.getKeyCode();
	}

	/**
	 * KeyReleased
	 */
	public void keyReleased(KeyEvent e)					
	{
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE)
		{

		}
	}

	/**
	 * ActionPerformed
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (status == LOSE_LIFE_SCREEN)
		{
			time_check --;
		}

		if(status == RUNNING_SCREEN)
		{
			person.move();

			if (person.getdY() < 0 && person.getY() >= PERSON_START_Y)
			{
				person.takeLife();
				if (person.getLives() == 0)
				{
					status = GAME_OVER_SCREEN;

				}
				else
				{
					status = LOSE_LIFE_SCREEN;
				}
				person.setY(PERSON_START_Y);
				person.setdY(0);
				on_floor = true;
			}
			if (person.getdY() > 0 && person.getY() <= MIN_HEIGHT)
			{
				person.takeLife();
				if (person.getLives() == 0)
				{
					status = GAME_OVER_SCREEN;

				}
				else
				{
					status = LOSE_LIFE_SCREEN;
				}
			}

			if (person.getdX() > 0 && person.x + PERSON_SIZE >= MAX_WIDTH)
			{
				person.takeLife();
				if (person.getLives() == 0)
				{
					status = GAME_OVER_SCREEN;
				}

				else
				{
					status = LOSE_LIFE_SCREEN;
				}
			}
			if (!on_floor)
			{
				person.jump(0);
				person.gravity(0);
			}

			for(Coin each: coins)
			{
				each.move();
				each.bounce(MIN_WIDTH + 200, MAX_WIDTH - 200, each.startY -200 , each.startY);
			}

			for (int i = 0; i < balls.size(); i ++)
			{
				Ball each = balls.get(i);
				if (each != null)
				{
					each.move();
					for (BackgroundBricks eachBrick: bricks)
					{

						Rectangle b_rect = eachBrick.getRect();
						Rectangle c_rect = new Rectangle (each.getXVal(), each.getYVal(), each.getSize(), each.getSize());
						if (c_rect.intersects(b_rect))
						{
							balls.remove(each);
						}
					}
				}

				if(each.getXVal() == MAX_HEIGHT - each.getSize())
				{
					balls.remove(each);
				}

			}
			
			startBrick.move();
			
			for(BackgroundBricks each: bricks)
			{
				each.move();
			}
			
			timeClicks++;
			goingThroughScreen ++;
			
			if (goingThroughScreen >= MAX_WIDTH)
			{
				goingThroughScreen = 0;
			}

			if (timeClicks % 15 == 0)
			{
				makeBrick();
			}

			if (timeClicks % 20 == 0)
			{
				makeBall();
			}

			for (int i = 0; i < coins.size(); i ++)
			{
				Coin each = coins.get(i);
				if (each !=null)
				{
					Rectangle c_rect = new Rectangle (each.x+3, each.y, each.coinSize, each.coinSize);
					Rectangle p_rect = new Rectangle (person.x, person.y, PERSON_SIZE, PERSON_SIZE);
					if (c_rect.intersects(p_rect))
					{
						coins.remove(each);
						person.collectCoin(20);
					}
				}	
			}

			for (int j = 0; j < balls.size(); j ++)
			{
				Ball each = balls.get(j);
				if (each != null)
				{
					Rectangle p_rect = new Rectangle (person.x + 3, person.y + 3, PERSON_SIZE - 3, PERSON_SIZE - 3);
					Rectangle c_rect = new Rectangle (each.getXVal() + 5, each.getYVal() + 5, each.getSize() - 5, each.getSize() - 5);
					if (c_rect.intersects(p_rect))
					{
						balls.remove(each);
						person.takeLife();
						if (person.getLives() == 0)
						{
							status = GAME_OVER_SCREEN;

						}
						else
						{
							status = LOSE_LIFE_SCREEN;
						} 
					}
				}
			}

			boolean intersects = checkIfIntersectsWithBrick();

			if (intersects && person.getX()<= 0)
			{
				person.setdX(0);
				hitWall = true;
			}
		}

		if (person.getLives() == 0)
		{
			status = GAME_OVER_SCREEN;
		}

		repaint();
	}

	/**
	 * Checks if the person intersects with a brick
	 * @return	Returns true or false depending of it intersects
	 */
	public static boolean checkIfIntersectsWithBrick()
	{
		if (bricks.size() != 0 && bricks.get(0) != null)
		{
			for(BackgroundBricks each: bricks)
			{
				Rectangle b_rect = each.getRect();
				Rectangle p_rect = new Rectangle (person.x, person.y + 9 , PERSON_SIZE, PERSON_SIZE);

				if(person.getdY() > 0 && b_rect.intersects(p_rect))
				{
					person.bounce();
				}

				else if (person.getdY() < 0 && b_rect.intersects(p_rect))
				{
					person.setdX(each.getdX());
					int brickY = each.getY();
					person.setY(brickY - person.getPersonSize() + 9);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * MouseClicked
	 */
	public void mouseClicked(MouseEvent e) 
	{

	}

	/**
	 * MouseEntered
	 */
	public void mouseEntered(MouseEvent e) 
	{

	}

	/**
	 * MouseExited
	 */
	public void mouseExited(MouseEvent e) 
	{

	}

	/**
	 * MousePressed
	 */
	public void mousePressed(MouseEvent e) 
	{
		double xClick = e.getX();
		double yClick = e.getY();
		if(status == START_SCREEN && 
				xClick >= VERTICAL_X_1 && 
				xClick <= VERTICAL_X_1 + 150 && 
				yClick >=  VERTICAL_Y_1 && 
				yClick <= VERTICAL_Y_1 + 50)
		{	
			status = RUNNING_SCREEN;
			repaint();
		}
		
		if(status == GAME_OVER_SCREEN && 
				xClick >= VERTICAL_X_1 && 
				xClick <= VERTICAL_X_1 + 150 && 
				yClick >=  VERTICAL_Y_1 && 
				yClick <= VERTICAL_Y_1 + 50)
		{
			status = START_SCREEN;
			reset();
			repaint();
			person.setLives(3);
		}

	}

	/**
	 * MouseReleased
	 */
	public void mouseReleased(MouseEvent e)
	{

	}

	/**
	 * Paint - Draws the images on the screen
	 */
	public void paint(Graphics g)
	{

		if(status == START_SCREEN)
		{
			g.setColor(Color.white);
			g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);

			g.setColor(Color.ORANGE);
			g.fillRoundRect(VERTICAL_X_1, VERTICAL_Y_1, 210, 50 , 25 , 25);	

			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("Start Game", VERTICAL_X_1 + 10, VERTICAL_Y_1 + 40);

			g.setColor(Color.white);
			g.drawRect(0,0,MAX_WIDTH,MIN_WIDTH);
			g.setColor(Color.black);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("How To Play", 
					VERTICAL_X_1, VERTICAL_Y_1 - 100);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("-you start with three lives", 
					VERTICAL_X_1 - 180, VERTICAL_Y_1 - 80);
			g.drawString("-when lives are 0, the game is over", 
					VERTICAL_X_1 - 180, VERTICAL_Y_1 - 60);
			g.drawString("-use the spacebar to jump; "
					+ "lose a life if you hit the top, right, or bottom"
					,VERTICAL_X_1 - 180, VERTICAL_Y_1 - 40);
			g.drawString("-when you collect 100 cash money, you receive a life", 
					VERTICAL_X_1 - 180, VERTICAL_Y_1 - 20);

			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("Welcome to Running Man", 
					VERTICAL_X_1 - 50, VERTICAL_Y_1 - 200);
			g.drawString("Press the button below to begin the game", 
					VERTICAL_X_1 - 160, VERTICAL_Y_1 - 165);
		}


		if (status == GAME_OVER_SCREEN)
		{
			g.drawImage(background, MIN_WIDTH, MIN_WIDTH, MAX_WIDTH, MAX_HEIGHT, this);
			person.drawDead(g, man, red_x);
			g.setColor(Color.BLUE);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("GAME OVER", 375, 300);
			
			g.setColor(Color.ORANGE);
			g.fillRoundRect(VERTICAL_X_1, VERTICAL_Y_1, 210, 50 , 25 , 25);	

			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("Play Again", VERTICAL_X_1 + 10, VERTICAL_Y_1 + 40);
		}

		if (status == LOSE_LIFE_SCREEN)
		{
			int time = timeClicks;
			g.drawImage(background, MIN_WIDTH, MIN_WIDTH, MAX_WIDTH, MAX_HEIGHT, this);

			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("Lost a life! Starting in: " + time_check/5, 300, 300);

			if (time_check == 0)
			{
				time_check = 29;
				reset();
				timeClicks = 0;
				status = RUNNING_SCREEN;
			}

		}
		if (status == RUNNING_SCREEN)
		{
			g.drawImage(background, MIN_WIDTH, MIN_WIDTH, MAX_WIDTH, MAX_HEIGHT, this);
			for(Coin each: coins)
			{
				each.draw(g, coin);
			}
			startBrick.draw(g);
			for(BackgroundBricks each: bricks)
			{
				each.draw(g);
			}

			for (Ball each: balls)
			{
				each.draw(g, ball);
			}
			person.draw(g, man);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("Cash money: " + person.getValue(), 100, 100);
			g.drawString("Lives: " + person.getLives(), 100, 150);

		}

		repaint();
	}

}
