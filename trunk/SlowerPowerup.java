import java.awt.Color;

public class SlowerPowerup extends MinuPowerup {
 
	public SlowerPowerup(){
		this.setColor(Color.gray); //Gray color change
	}
     
	public void act() {}

	public void editPlayer(MinuWaller player) {
		player.setDelay(6); //6 STEPS late
		player.setWallLife(300); 
	}

}

