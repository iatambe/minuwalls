import java.awt.Color;

public class NormalizePowerup extends MinuPowerup {

	public NormalizePowerup(){
		this.setColor(Color.magenta);    
	}
     
	public void act() {} // it stays in place

	public void editPlayer(MinuWaller player) {
		player.setDelay(2);
		player.setWallLife(MinuWall.LIFETIME);
	}

}
