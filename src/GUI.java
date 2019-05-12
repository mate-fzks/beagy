import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;


public class GUI extends JPanel{

	private GAMEPLAY game;
	private volatile boolean single;

	private JFrame menu;
	
	public void GUIStart() {
		
		SetUpGUI(this);

		menu.setVisible(true);
 
		while (true) {
			if (single) {
				game.GameCycle();
			}
		}
	}
	
	public void SetUpGUI(GUI gui) {
		
		menu = new JFrame("Menu");
	    menu.add(gui);
		menu.setSize(400, 400);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setResizable(false);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		
		menu.add(buttons);
		
		JButton ButtonPlanner = new JButton("Track Planner");
		JButton ButtonSingle = new JButton("Singleplayer");
		JButton ButtonMulti = new JButton("Multiplayer");

	    ButtonPlanner.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 menu.setVisible(false);
	        	//Pályatervezés start
		        PLANNER plan = new PLANNER();
		        MAP act_map2 = plan.CreateMap();
		     	MATERIAL snow = new MATERIAL("SNOW");
		     	MATERIAL tarmac = new MATERIAL("TARMAC");

		     	MATERIAL grass = new MATERIAL("GRASS");
		     	MATERIAL gravel = new MATERIAL("GRAVEL");
		     	MATERIAL MUD = new MATERIAL("MUD");
		     	act_map2 = plan.CreateStraight(act_map2,45,100,5,snow,grass);
		     	act_map2 = plan.CreateStraight(act_map2,20,200,5,tarmac,gravel);	
		     	act_map2 = plan.CreateStraight(act_map2,70,200,5,MUD,snow);
		     	act_map2 = plan.CreateStraight(act_map2,-10,200,5,tarmac,snow);	
		     	act_map2 = plan.CreateCircle(act_map2,30,110,200, 5, snow);
		     	act_map2 = plan.CreateStraight(act_map2,180,200,5,tarmac,snow);	
		     	act_map2 = plan.LoopClosure(act_map2);
		     	
		        game = new GAMEPLAY(act_map2, false);
		        
		        game.gamewindow.setVisible(true);
	    	     
	    		single = true;
	         }          
	    });
	    ButtonSingle.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	    		 menu.setVisible(false);
	    		 PLANNER plan = new PLANNER();
	    	     MAP act_map = plan.DefaultMap();
	    	     game = new GAMEPLAY(act_map,false);
	        	 
	    		 game.gamewindow.setVisible(true);
	    		 	    	     
	    		 single = true;
	         }
	    });
	    ButtonMulti.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            
	         }
	      });
	    
	     buttons.add(ButtonPlanner);
	     buttons.add(ButtonSingle);
	     buttons.add(ButtonMulti);        
	}
	
 

}



