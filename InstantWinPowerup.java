import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.Color;
import java.util.*;

/* When a player "eats" the InstantWinPowerup, that player's opponent explodes instantly,
 * causing the first player to win. The InstantWinPowerup moves around randomly on the grid,
 * making it harder to get.
 */

public class InstantWinPowerup extends MinuPowerup {//extends abstract MinuPowerup
 /* this.count counts the steps before the InstantWinPowerup should
  * make its next random move on the grid.
  */
 private int count;
 private static int DELAY = 3; // DELAY is the amount of steps before the InstantWinPowerup moves.
 private Location prev; // the previous location; is null at the start
 
 public InstantWinPowerup() {
  prev = null;
  count = 0;
  this.setColor(Color.orange); // default color is Orange
 }

 /* act() causes the InstantWinPowerup to move around randomly on the grid. It is designed
  * to tend to follow a path and to avoid moving back-and-forth.
  */
 public void act() {
  count++;
  if (count == DELAY) { //We give it a three step latency <--- REFER TO: private static int DELAY 
   count = 0;
  } else {
   return;
  }
  Random rand = new Random(); //random location selector
 
  Location cur = super.getLocation();
  ArrayList<Location> locs = new ArrayList<Location>(); // list of possible move locations
  Grid<Actor> grid = this.getGrid();
  
   //get all adjacent locations
  for (int dir = 0; dir <= 315; dir += 45) {
   Location next = cur.getAdjacentLocation(dir);
   if (grid.isValid(next) && grid.get(next) == null) {
    locs.add(next);
   }
  }
//the InstantWinPowerup will not move back to its previous location to avoid zig zagging, unless it has nowhere else to move.
  if (prev != null) {
   int i;
   for (i = 0; i < locs.size(); i++) {
    if (locs.get(i).equals(prev)) {//when the i+1th Location in the list is equal to the previous location, break out of the loop
     break;
    }
   }
   // removes the prev location as an option to move to IF it is not the only available option
   if (i < locs.size() && locs.size() > 1) {
    // if this point is reached, then i is the index of the previous location in locs
    locs.remove(i);
   }

   int dirFacing = prev.getDirectionToward(cur);//direction this InstantWinPowerup is facing, from the previous location
   Location next = cur.getAdjacentLocation(dirFacing);//next is the Location toward which the powerup is facing
   
   for (i = 0; i < locs.size(); i++) {
    if (locs.get(i).equals(next)) {
     break;
    }
   }
   // There is a 1/3 chance that it will automatically choose the Location
   // in the direction from the previous location.
   if (i < locs.size() && rand.nextInt(3) == 0) {
    prev = new Location(cur.getRow(), cur.getCol());
    super.moveTo(locs.get(i));
    return;
   }
  }
  
  prev = new Location(cur.getRow(), cur.getCol());
  int index = rand.nextInt(locs.size());
  super.moveTo(locs.get(index));
 }
 
 /* InstantWinPowerup's editPlayer() causes the player's opponent to be 
  * "condemned" to death.
  */
 public void editPlayer(MinuWaller player) {
  MinuWaller winner = player, loser = player.getOpponent();
  loser.setWillDie(true);
 }

}

