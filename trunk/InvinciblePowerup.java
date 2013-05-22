import java.awt.Color;

public class InvinciblePowerup extends MinuPowerup {

     public InvinciblePowerup()
     {
        this.setColor(Color.yellow);
        //Set color to Yellow for the player when he becomes invinceable 
     }
     
 public void act() {}
 
 public void editPlayer(MinuWaller player) {
  //player.becomeInvincible();
 }
 
}
