import java.awt.Color;

public class FasterPowerup extends MinuPowerup {

	public FasterPowerup() {
		this.setColor(Color.green);    
	}
     
	public void act() {} // it stays in place

	public void editPlayer(MinuWaller player) {
		player.setDelay(1); //We want it to go a little faster than usual so we use our very own setDelay method
		player.setWallLife(50); //the life limit of how many blocks it will be able to speed through. 
	}

}

