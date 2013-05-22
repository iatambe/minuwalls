import javax.swing.JOptionPane;

public class InstructionManual {

	/*
	private String p1name, p2name;
	private static int colorOfPlayers1;
	private static int colorOfPlayers2;
	*/
	private JOptionPane dialogBox;
	private String [] optionsYesNo;
	private int retVal;
	
	public InstructionManual(JOptionPane s) {
		
		this.dialogBox = s;
		
		this.optionsYesNo = new String[2];
		this.optionsYesNo[0] = new String("Yes");
		this.optionsYesNo[1] = new String("No");

	}
	
	public void showInstructions() {
						
		this.retVal = this.dialogBox.showOptionDialog(null, "Would you like instructions?", "MinuWalls",
			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
			null, optionsYesNo, optionsYesNo[0]);
		
		if (retVal == JOptionPane.YES_OPTION) {
			//System.out.println("YES");
			
			this.showInstructionDialog();
			
		} else if (retVal == JOptionPane.NO_OPTION) {
			//System.out.print("NO");
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
		} else if (retVal == JOptionPane.CLOSED_OPTION) {
			//System.out.print("CLOSED");
			dialogBox.showMessageDialog(null, "Okay then.",
				"MinuWalls", JOptionPane.PLAIN_MESSAGE);
		}

    }

	private void showInstructionDialog() {
		int res;
		res = dialogBox.showConfirmDialog(null, "Welcome to MinuWalls, the best GridWorld game on Earth!\n"
			+ "Let's start with Game Instructions...",
			"Welcome to MinuWalls!", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "Avoid the walls that trail from your opponent's bike. "
			+ "It's similar to Tron, or Light Bike.",
			"Instructions #1", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "For Player 1, the controls are the Arrow Keys." + '\n' 
			+ "The arrow keys' directions are self-explanatory.",
			"Instructions #2", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "For Player 2, the controls are WASD." + '\n' 
			+ "W moves up, A moves left, S moves down, D moves right.",
			"Instructions #3", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "There are powerups in the game as well. "
			+ "Collect them and you will be able to gain an advantage or disadvantage.",
			"Instructions #4", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		res = dialogBox.showConfirmDialog(null, "The game begins when the stoplight turns green. " + '\n'
			+ "The first person to reach 10 points wins!",
			"Instructions #5", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.INFORMATION_MESSAGE);
		/*
		if (res == JOptionPane.CANCEL_OPTION || res == JOptionPane.CLOSED_OPTION) {
			dialogBox.showMessageDialog(null, "Okay then.", "MinuWalls", JOptionPane.PLAIN_MESSAGE);
			return;
		}*/
		return;
	}	

	/*
	public void NCSettings () {
		String [] colorNames = {"Red", "Orange", "Blue", "Green", "Yellow"};
		
		
		dialogBox.showOptionDialog(null, "What color do you want your bike to be, Player 1", "Player 1",
								   JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
								   null, colorNames, colorNames[0]);
		//For Player 1
		
		if(dialogBox.getValue().equals("Red"));
		colorOfPlayers1 = 1;
		if(dialogBox.getValue().equals("Orange"));
		colorOfPlayers1 = 2;
		if(dialogBox.getValue().equals("Blue"));
		colorOfPlayers1 = 3;
		if(dialogBox.getValue().equals("Green"));
		colorOfPlayers1 = 4;
		if(dialogBox.getValue().equals("Yellow"));
		colorOfPlayers1 = 5;
		
		
        
		
		dialogBox.showOptionDialog(null, "What color do you want your bike to be, Player 2", "Player 2",
								   JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
								   null, colorNames, colorNames[0]);
		
		if(dialogBox.getValue().equals("Red"));
		if(colorOfPlayers1 == 1) {
			String [] colorNames1 = {"Can't!", "Orange", "Blue", "Green", "Yellow"};
			
			
			dialogBox.showOptionDialog(null, "Choose again!", "Player 2",
									   JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
									   null, colorNames1, colorNames1[0]);
			
			
			if(dialogBox.getValue().equals("Can't!"));
			System.exit(0);
			if(dialogBox.getValue().equals("Orange"));
			colorOfPlayers2 = 2;
			if(dialogBox.getValue().equals("Blue"));
			colorOfPlayers2 = 3;
			if(dialogBox.getValue().equals("Green"));
			colorOfPlayers2 = 4;
			if(dialogBox.getValue().equals("Yellow"));
			colorOfPlayers2 = 5;
			
			
		}
		else {
			colorOfPlayers2 = 1;
		}
		
		
		if(dialogBox.getValue().equals("Orange"));
		if(colorOfPlayers1 == 2) {
			String [] colorNames2 = {"Red", "Can't!", "Blue", "Green", "Yellow"};
			
			
			dialogBox.showOptionDialog(null, "Choose again!", "Player 2",
									   JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
									   null, colorNames2, colorNames2[0]);
			
			
			if(dialogBox.getValue().equals("Red"));
			colorOfPlayers2 = 1;
			if(dialogBox.getValue().equals("Can't!"));
			System.exit(0);
			if(dialogBox.getValue().equals("Blue"));
			colorOfPlayers2 = 3;
			if(dialogBox.getValue().equals("Green"));
			colorOfPlayers2 = 4;
			if(dialogBox.getValue().equals("Yellow"));
			colorOfPlayers2 = 5;
			
		}
		else {
			colorOfPlayers2 = 2;
		}
		
		
		if(dialogBox.getValue().equals("Blue"));
		if(colorOfPlayers1 == 3) {
			System.out.println("blah");
			String [] colorNames3 = {"Red", "Orange", "Can't!", "Green", "Yellow"};
			
			dialogBox.showOptionDialog(null, "Choose again!", "Player 2",
									   JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
									   null, colorNames3, colorNames3[0]);
			
			
			if(dialogBox.getValue().equals("Red"));
			colorOfPlayers2 = 1;
			if(dialogBox.getValue().equals("Orange"));
			colorOfPlayers2 = 2;
			if(dialogBox.getValue().equals("Can't!"));
			System.exit(0);
			if(dialogBox.getValue().equals("Green"));
			colorOfPlayers2 = 4;
			if(dialogBox.getValue().equals("Yellow"));
			colorOfPlayers2 = 5;
			
		}
		else {
			colorOfPlayers2 = 3;
		}
		
		if(dialogBox.getValue().equals("Green"));
		if(colorOfPlayers1 == 4) {
			String [] colorNames4 = {"Red", "Orange", "Blue", "Can't!", "Yellow"};
			
			dialogBox.showOptionDialog(null, "Choose again!", "Player 2",
									   JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
									   null, colorNames4, colorNames4[0]);
			
			
			if(dialogBox.getValue().equals("Red"));
			colorOfPlayers2 = 1;
			if(dialogBox.getValue().equals("Orange"));
			colorOfPlayers2 = 2;
			if(dialogBox.getValue().equals("Blue"));
			colorOfPlayers2 = 3;
			if(dialogBox.getValue().equals("Can't!"));
			System.exit(0);
			if(dialogBox.getValue().equals("Yellow"));
			colorOfPlayers2 = 5;
			
		}
		else {
			colorOfPlayers2 = 4;
		}
		
		if(dialogBox.getValue().equals("Yellow"));
		if(colorOfPlayers1 == 5) {
			String [] colorNames5 = {"Red", "Orange", "Blue", "Green", "Can't!"};
			
			dialogBox.showOptionDialog(null, "Choose again!", "Player 2",
									   JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
									   null, colorNames5, colorNames5[0]);
			
			
			if(dialogBox.getValue().equals("Red"));
			colorOfPlayers2 = 1;
			if(dialogBox.getValue().equals("Orange"));
			colorOfPlayers2 = 2;
			if(dialogBox.getValue().equals("Blue"));
			colorOfPlayers2 = 3;
			if(dialogBox.getValue().equals("Green"));
			colorOfPlayers2 = 4;
			if(dialogBox.getValue().equals("Yellow"));
			System.exit(0);
			
		}
		else {
			colorOfPlayers2 = 5;
		}
		
	}
	
	public void wantNames () {
		
		this.p1name = dialogBox.showInputDialog (null, "What's your name, Player 1?", 
			"Player 1", JOptionPane.WARNING_MESSAGE );
		this.p2name = dialogBox.showInputDialog (null, "What's your name, Player 2?", 
			"Player 2", JOptionPane.WARNING_MESSAGE );
		
	}
	
	public String getP1name () {
		return this.p1name;
	}
	
	public String getP2name () {
		return this.p2name;
	}
	
	public int colPlayer1 () {
		return colorOfPlayers1;
	}
	
	public int colPlayer2 () {
		return colorOfPlayers2;
	}
	
	public void showInstruction () {
		this.instructions();
		
	}
	
	public static void main (String [] args) {
		InstructionManual s = new InstructionManual(new JOptionPane());
		s.instructions();
		
	}
	*/
	
}

