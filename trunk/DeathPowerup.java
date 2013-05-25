import java.awt.Color;

public class DeathPowerup extends MinuPowerup {//extends an abstract class, MinuPowerup. If a player gets this powerup, he/she explodes.
	
	public DeathPowerup()//default constructor sets the color red
	{
        this.setColor(Color.red);
	}
	
	public void act() {}//stays in place, doesn't move
	
    //override polymorphism method from the abstract MinuPowerup class.
	public void editPlayer(MinuWaller player) {
		player.setWillDie(true);//makes the player die
	}
	
}

