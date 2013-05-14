import java.awt.Color;

public class FasterPowerup extends MinuPowerup {

	public FasterPowerup() {
		this.setColor(Color.green);    
	}
     
	public void act() {} // it stays in place

	public void editPlayer(MinuWaller player) {
		player.setDelay(1);
		player.setWallLife(50);
	}

}

