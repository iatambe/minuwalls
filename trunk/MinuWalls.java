// Driver for MinuWalls.

public class MinuWalls {
     
 public static void main(String [] args) {
  System.setProperty("info.gridworld.gui.selection", "hide");

  MWWorld game = new MWWorld();
  game.startNew();
  game.show();
 }
     
}

