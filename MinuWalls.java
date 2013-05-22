// Driver for MinuWalls.
import javax.swing.JOptionPane; 


public class MinuWalls {
     
	public static void main(String [] args) {
		JOptionPane optionPane = new JOptionPane();
		System.setProperty("info.gridworld.gui.selection", "hide");
		InstructionManual manual = new InstructionManual(optionPane);
		manual.showInstructions();
	
		
		MWWorld game = new MWWorld();
		game.startNew();
		game.show();
     	
    
	}
     
}

