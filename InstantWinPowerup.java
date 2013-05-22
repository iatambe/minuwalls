import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.Color;
import java.util.*;
//this powerup moves around on the grid, making it harder to get, because it is an automatic win powerup.
public class InstantWinPowerup extends MinuPowerup {//extends abstract MinuPowerup

 private int count;
 private static int DELAY = 3; // DELAY is the amount of steps before the InstantWinPowerup moves.
 private Location prev;
 
 public InstantWinPowerup() {
  prev = null;
  count = 0;
  this.setColor(Color.orange); //powerup is orange
 }


 public void act() {//overrides method of parent class
  count++;
  if (count == DELAY) { //We give it a three step latency <--- REFER TO: private static int DELAY 
   count = 0;
  } else {
   return;
  }
  Random rand = new Random(); //random location selector
 
  Location cur = super.getLocation();
  ArrayList<Location> locs = new ArrayList<Location>();
  Grid<Actor> grid = this.getGrid();
  for (int dir = 0; dir <= 315; dir += 45) { //gets all adjacent locations
   Location next = cur.getAdjacentLocation(dir);
   if (grid.isValid(next) && grid.get(next) == null) {//if the adj. location is valid, add it to a list of possible locations to move to
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
   if (i < locs.size() && locs.size() > 1) {//remove the i+1th Location from the possible areas to move to, unless the Location is the only place to move to.
    locs.remove(i);
   }

   int dirFacing = prev.getDirectionToward(cur);//direction it is facing is the direction from the previous Location to the current Location
   Location next = cur.getAdjacentLocation(dirFacing);//next is the Location the powerup is facing.
   
   for (i = 0; i < locs.size(); i++) {
    if (locs.get(i).equals(next)) {
     break;
    }
   }
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

 public void editPlayer(MinuWaller player) {
  MinuWaller winner = player, loser = player.getOpponent();
  loser.setWillDie(true);
 }

}

