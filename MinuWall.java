import java.awt.Color;
import info.gridworld.actor.Actor;

public class MinuWall extends Actor
{
	private static final Color DEFAULT_COLOR = Color.RED;
	private int counter = 0;
	
	public static int LIFETIME = 100; //We want to give the life a value so we can maniulate players later. 
	private int life;

	public void setLifetime(int val) { this.life = val; }
	public int getLifetime() { return this.life; }
	
	public MinuWall()
	{
		setColor(DEFAULT_COLOR);
		life = LIFETIME;
	} 
	
	public MinuWall(Color initialColor)
	{
		setColor(initialColor); //we have the ability to customize players in our driver. 
	} 
	
	public void setCounter(int val) {
		this.counter = val; 
	} 
	
	public void act()
	{
		if (counter == life)
		{
			removeSelfFromGrid();
		} 
		
		if (counter != -1)
		counter++;
	} 
} 

