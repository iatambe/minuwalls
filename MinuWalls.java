// Driver for MinuWalls.
import java.lang.Object.*; 
import javax.swing.JOptionPane; 


public class MinuWalls {
     
 public static void main(String [] args) {
  JOptionPane i = new JOptionPane();
  System.setProperty("info.gridworld.gui.selection", "hide");
  InstructionManual s = new InstructionManual(i);
  s.showInstruction();

  MWWorld game = new MWWorld();
  game.startNew();
  game.show();
     
     
 }
     
}

