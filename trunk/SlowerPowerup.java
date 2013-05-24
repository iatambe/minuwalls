import java.awt.Color;

public class SlowerPowerup extends MinuPowerup {//makes the player slower, extends abstract class MinuPowerup so it will implement needed methods.
 
	public SlowerPowerup(){
		this.setColor(Color.gray); //sets color to gray.
	}
     
	public void act() {}//does nothing when act is called.

	public void editPlayer(MinuWaller player) {
		player.setDelay(6); //sets the delay to a higher value, making the MinuWaller, or player, be slower.
		player.setWallLife(300); //makes the wall life left behind by the MinuWaller to higher so the walls will remain the same length.
	}

}

