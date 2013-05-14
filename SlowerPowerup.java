import java.awt.Color;

public class SlowerPowerup extends MinuPowerup {
 
	public SlowerPowerup(){
		this.setColor(Color.gray);    
	}
     
	public void act() {}

	public void editPlayer(MinuWaller player) {
		player.setDelay(6);
		player.setWallLife(300); 
	}

}

