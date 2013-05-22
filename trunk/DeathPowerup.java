import java.awt.Color;

public class DeathPowerup extends MinuPowerup {
//We're setting the color the red here
 public DeathPowerup()
 {
        this.setColor(Color.red);
 }
     
 public void act() {}
 
    //We're using a boolean method to determine whether the player is dead
 public void editPlayer(MinuWaller player) {
  player.setWillDie(true);
 }

}

