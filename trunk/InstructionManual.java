import java.lang.Object.*; 
import javax.swing.JOptionPane; 
public class InstructionManual {
  private String s; 
  private String z;
  private JOptionPane dialogBox; 
  private static int colorOfPlayers1; 
  private static int colorOfPlayers2;
  private String [] optionsYesNo;  
  
  public InstructionManual (JOptionPane s) {
    
    dialogBox = s; 
    
  }
  
  public void instructions (){
    
    optionsYesNo = new String[2];
    optionsYesNo[0] = new String ("Yes"); 
    optionsYesNo[1] = new String ("No"); 
    
    
    dialogBox.showOptionDialog(null, "Would you like instructions?", "GridWorld", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, optionsYesNo, optionsYesNo[0]);
    
    if(dialogBox.getValue().equals("Yes")); {
        System.out.print("YES");
        
        dialogBox.showMessageDialog(null, "Welcome to MinuWalls, the best GridWorld game on Earth"); 
        dialogBox.showMessageDialog(null, "Let's start with Game Instructions"); 
        dialogBox.showMessageDialog(null, "Avoid the walls that trail your opponent's bike. It's similar to tron or light bike"); 
        dialogBox.showMessageDialog(null, "For Player 1, the controls are the Arrow Keys." + '\n' + "To move up, press the up arrow key." + '\n' + "To move down, press the down arrow key" + '\n' + "Do the same for left and right keys as they correspond to the direction."); 
        dialogBox.showMessageDialog(null, "For Player 2, use WASD key formation" + '\n' + "W moves UP, A moves left, S moves down, D moves right"); 
        dialogBox.showMessageDialog(null, "There are powerups in the game too. Collect them and you will be able to gain an advantage or disadvantage in the game"); 
        dialogBox.showMessageDialog(null, "Wait for the stoplight to turn green and go!"); 
    }
    if(dialogBox.getValue().equals("No"));{
             System.out.print("NO");
             dialogBox.showMessageDialog(null, "Okay!"); 
    }
    }
   
  
  

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
  
  public void WantNames () {
    
    String s = dialogBox.showInputDialog (null, "What's your name, Player 1?", "Player 1", JOptionPane.WARNING_MESSAGE );
    String z = dialogBox.showInputDialog (null, "What's your name, Player 2?", "Player 2", JOptionPane.WARNING_MESSAGE );
    
  }
  
  public String ply1getName () {
    return s;   
  }
  
  public String ply2getName () {
    return z;   
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
  

}

