import info.gridworld.actor.*;
import info.gridworld.grid.*;

/* The word "powerup" is used broadly here; it can refer to any additional object
 * that can affect a MinuWaller on the grid.
 */

/* All powerups in the game extend the MinuPowerup class, which extends Actor. Its act()
 * method is declared abstract, so that child classes can define their act() in any way
 * they want. The other abstract method is editPlayer(). When a MinuWaller hits this MinuPowerup, that
 * MinuWaller calls this this MinuPowerup's editPlayer() method, passing itself (the MinuWaller) as
 * the argument.
 */

public abstract class MinuPowerup extends Actor {//the abstract Powerup for our game. Contains the necessary methods for a Powerup.
	
	// different MinuPowerups may behave in different ways
	// (as in, how do they behave on the grid until someone hits them)
	public abstract void act();
	
	// this method is called to do whatever this Powerup is supposed to do.
	// when a MinuWaller hits this MinuPowerup, editPlayer() is called,
	// and the MinuWaller passes itself as the argument.
	public abstract void editPlayer(MinuWaller player);
	
}

