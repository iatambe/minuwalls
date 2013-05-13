import java.awt.Color;

public class InvinciblePowerup extends MinuPowerup {

     public InvinciblePowerup()
     {
        this.setColor(Color.yellow);    
     }
     
 public void act() {}
 
 public void editPlayer(MinuWaller player) {
  //player.becomeInvincible();
 }
 
}
