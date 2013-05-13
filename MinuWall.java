import java.awt.Color;
import info.gridworld.actor.Actor;

public class MinuWall extends Actor
{
	private static final Color DEFAULT_COLOR = Color.RED;
	private int counter = 0;
	
	private static int LIFETIME = 100;
	
	public MinuWall()
	{
		setColor(DEFAULT_COLOR);
	} 
	
	public MinuWall(Color initialColor)
	{
		setColor(initialColor);
	} 
	
	public void setCounter(int val) {
		this.counter = val;
	} 
	
	public void act()
	{
		if (counter == LIFETIME)
		{
			removeSelfFromGrid();
		} 
		
		if (counter != -1)
		counter++;
	} 
} 

