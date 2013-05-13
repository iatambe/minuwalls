import java.awt.Color;

public class DeathPowerup extends MinuPowerup {

 public DeathPowerup()
 {
        this.setColor(Color.red);
 }
     
 public void act() {}
 
 public void editPlayer(MinuWaller player) {
  player.setWillDie(true);
 }

}

