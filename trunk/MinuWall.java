import java.awt.Color;
import info.gridworld.actor.Actor;

public class MinuWall extends Actor//this is what is left behind by the MinuWallers.
{
	private static final Color DEFAULT_COLOR = Color.RED;
	private int counter = 0;
	
	public static int LIFETIME = 100; //how long this lasts in the grid, default is 100. 
	private int life;//

	public void setLifetime(int val) { this.life = val; }//modify the lifetime
	public int getLifetime() { return this.life; }//get the life time value
	
	public MinuWall()
	{
		setColor(DEFAULT_COLOR);//sets the default color to red, sets the lifetime.
		life = LIFETIME;
	} 
	
	public MinuWall(Color initialColor)
	{
		setColor(initialColor); //color constructor 
	} 
	
	public void setCounter(int val) {//sets the counter to a value
		this.counter = val; 
	} 
	
	public void act()
	{
		if (counter == life)
		{
			removeSelfFromGrid();//when the lifetime is up, removes itself, resulting in a tail.
		} 
		
		if (counter != -1)//increments life counter everytime it acts.
		counter++;
	} 
} 

