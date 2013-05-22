import java.awt.Color;

public class NormalizePowerup extends MinuPowerup {//extends the abstract class

	public NormalizePowerup(){
		this.setColor(Color.magenta); //sets color to magenta
	}
     
	public void act() {} // it stays in place

	public void editPlayer(MinuWaller player) {
		player.setDelay(2); //sets the delay value back to the defaults.
		player.setWallLife(MinuWall.LIFETIME);//sets the wall life to defaults.
	}

}
