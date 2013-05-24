import info.gridworld.actor.*;
import info.gridworld.grid.*;

/* The word "powerup" is used broadly here; it can refer to any additional object
 * that can affect a MinuWaller on the grid.
 */

public abstract class MinuPowerup extends Actor {//the abstract Powerup for our game. Contains the necessary methods for a Powerup.

	// different MinuPowerups may behave in different ways
	// (as in, how do they behave until someone hits them)
	public abstract void act();

	// this method is called to do whatever this Powerup is supposed to do
	public abstract void editPlayer(MinuWaller player);

}

