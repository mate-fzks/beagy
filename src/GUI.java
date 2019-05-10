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
	        	//Pályatervezés start
		        PLANNER plan = new PLANNER();
		        MAP act_map2 = plan.CreateMap();
		     	MATERIAL snow = new MATERIAL("SNOW");
		     	MATERIAL tarmac = new MATERIAL("TARMAC");

		     	MATERIAL MUD = new MATERIAL("MUD");
		     	act_map2 = plan.CreateStraight(act_map2,45,100,50,snow);
		     	act_map2 = plan.CreateStraight(act_map2,20,200,50,tarmac);	
		     	act_map2 = plan.CreateStraight(act_map2,70,200,50,MUD);
		     	act_map2 = plan.CreateStraight(act_map2,-10,200,50,tarmac);	
		     	act_map2 = plan.CreateCircle(act_map2,50,140,200, 50, snow);
		     	act_map2 = plan.CreateStraight(act_map2,180,200,50,tarmac);	
		     	act_map2 = plan.LoopClosure(act_map2);
		        //new GAMEPLAY(act_map2, false).StartGameplay(frame);
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



