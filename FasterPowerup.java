import java.awt.Color;

public class FasterPowerup extends MinuPowerup {//extends abstract MinuPowerup class, makes the MinuWaller who gets it faster by changing the delay value.
	
	public FasterPowerup() {//sets color to green, the default color
		this.setColor(Color.green);
	}
	
	public void act() {} // it stays in place
	
	public void editPlayer(MinuWaller player) {
		player.setDelay(1); //set the delay of the player to 1/2 of the normal delay
		player.setWallLife(50); //sets the wall life to 1/2 the original life because the delay causes the walls to extend to 2x the length of the original length.
	}
	
}

