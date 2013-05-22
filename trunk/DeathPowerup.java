import java.awt.Color;

public class DeathPowerup extends MinuPowerup {
//We're setting the color the red here
 public DeathPowerup()
 {
        this.setColor(Color.red);
 }
     
 public void act() {}//stays in place, doesn't move
 
    //override polymorphism method from the abstract MinuPowerup class.
 public void editPlayer(MinuWaller player) {
  player.setWillDie(true);//makes the player die
 }

}

