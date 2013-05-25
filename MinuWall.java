import java.awt.Color;
import info.gridworld.actor.Actor;

/* A MinuWall is left behind by a MinuWaller as the player moves. The MinuWall's
 * act() method simply increments the counter variable, and then destroys this
 * instance of MinuWall if the counter reaches the lifetime, which is the variable
 * this.life. The default lifetime, LIFETIME, is 100, but it can be changed for 
 * individual MinuWalls.
 */ 

public class MinuWall extends Actor
{
	private static final Color DEFAULT_COLOR = Color.RED;
	private int counter = 0;
	
	public static int LIFETIME = 100; //how long this lasts in the grid, default is 100.
	private int life;//
	
	public void setLifetime(int val) { this.life = val; }//modify the lifetime
	public int getLifetime() { return this.life; }//get the life time value
	
	public MinuWall()
	{
		super.setColor(DEFAULT_COLOR);//sets the default color to red, sets the lifetime.
		this.life = LIFETIME;
	}
	
	// constructor that accepts a Color
	public MinuWall(Color initialColor)
	{
		super.setColor(initialColor);
		this.life = LIFETIME;
	}
	
	public void setCounter(int val) {//sets the counter to a value
		this.counter = val;
	}
	
	public void act()
	{
		if (this.counter == this.life)
		{
			super.removeSelfFromGrid(); // when the lifetime is up, this MinuWall removes itself.
			// as a result, the MinuWaller has a tail of a specific length.
		}
		
		// if the counter is -1, then the MinuWall never removes itself.
		// this is used in the stoplight at the beginning of each round.
		if (this.counter != -1)
			this.counter++;
	}
}

