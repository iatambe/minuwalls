import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.Color;
import java.util.*;

public class InstantWinPowerup extends MinuPowerup {

 private int count;
 private static int DELAY = 3; // DELAY is in terms of steps
 private Location prev;
 
 public InstantWinPowerup() {
  prev = null;
  count = 0;
  this.setColor(Color.orange); //We're setting the player to Orange (ovirreden its prev. color) 
 }

 // move
 public void act() {
  count++;
  if (count == DELAY) { //We give it a three step latency <--- REFER TO: private static int DELAY 
   count = 0;
  } else {
   return;
  }
  Random rand = new Random(); 
 
  Location cur = super.getLocation();
  ArrayList<Location> locs = new ArrayList<Location>();
  Grid<Actor> grid = this.getGrid();
  for (int dir = 0; dir <= 315; dir += 45) { //We restrict its movement only going to 315 degrees
   Location next = cur.getAdjacentLocation(dir);
   if (grid.isValid(next) && grid.get(next) == null) {
    locs.add(next);
   }
  }

  if (prev != null) {
   int i;
   for (i = 0; i < locs.size(); i++) {
    if (locs.get(i).equals(prev)) {
     break;
    }
   }
   if (i < locs.size() && locs.size() > 1) {
    locs.remove(i);
   }

   int dirFacing = prev.getDirectionToward(cur);
   Location next = cur.getAdjacentLocation(dirFacing);
   
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

