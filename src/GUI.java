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
	        	 
	        	 //GUI for Planner
	        	menu.setVisible(false);
	        	JFrame planner_setup;
	        	JTextField straight_angle;
	        	JTextField straight_dist;
	        	JTextField circ_orient;
	        	JTextField circ_ang;
	        	JTextField circ_rad;
	        	JLabel straight_angle_label;
	        	JLabel straight_dist_label;
	        	JLabel circ_orient_label;
	        	JLabel circ_ang_label;
	        	JLabel circ_rad_label;
	        	JComboBox track_straight;
	        	JComboBox track_circ;
	        	String tracks[]= {"TARMAC","GRASS","GRAVEL","MUD","WATER","SNOW","ICE"};
	        	JLabel track_straight_label;
	        	JLabel track_circ_label;
	        	        	
	        	planner_setup = new JFrame("Menu");
	        	planner_setup.add(gui);
	        	planner_setup.setSize(700, 500);
	        	planner_setup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        	planner_setup.setResizable(false);
				
				JPanel buttons = new JPanel();
				buttons.setLayout(null);
				
				planner_setup.add(buttons);
				
				JButton CreateStraight = new JButton("Create Straight");
				JButton CreateCircle = new JButton("Create Arc");
				JButton Clear = new JButton("Clear");
				JButton CloseLoop = new JButton("Close Loop");
				JButton StartGame = new JButton("Start Game");
				
				CreateStraight.setBounds(100, 20, 200, 20);
				CreateCircle.setBounds(400, 20, 200, 20);
				Clear.setBounds(433, 300, 100, 20);
				CloseLoop.setBounds(166,300,100,20);
				StartGame.setBounds(275, 400, 150, 40);
				
				straight_angle = new JTextField(5);
				straight_angle.setBounds(100,100,100,20);
				straight_angle_label = new JLabel("Straight Angle");
				straight_angle_label.setBounds(100,80,100,20);
				
				straight_dist = new JTextField(5);
				straight_dist.setBounds(300,100,100,20);
				straight_dist_label = new JLabel("Straight Distance");
				straight_dist_label.setBounds(300,80,100,20);
                
				track_straight = new JComboBox(tracks);
               	track_straight.setBounds(500, 100, 100, 20);
               	track_straight_label = new JLabel("Straight Surface");
               	track_straight_label.setBounds(500, 80, 100, 20);
               	
               	circ_orient = new JTextField(5);
               	circ_orient.setBounds(60,200,100,20);
               	circ_orient_label = new JLabel("Arc Orientation");
               	circ_orient_label.setBounds(60,180,100,20);
				
				circ_ang = new JTextField(5);
				circ_ang.setBounds(220,200,100,20);
				circ_ang_label = new JLabel("Arc Angle");
				circ_ang_label.setBounds(220,180,100,20);
                
				circ_rad = new JTextField(5);
				circ_rad.setBounds(380,200,100,20);
				circ_rad_label = new JLabel("Arc Radius");
				circ_rad_label.setBounds(380,180,100,20);
                
				
				track_circ = new JComboBox(tracks);
				track_circ.setBounds(540, 200, 100, 20);
				track_circ_label = new JLabel("Arc Surface");
				track_circ_label.setBounds(540, 180, 100, 20);
   
               	
				
				buttons.add(CreateStraight);
			    buttons.add(CreateCircle);
			    buttons.add(Clear); 
			   
			    buttons.add(straight_angle);
			    buttons.add(straight_angle_label);
			    
			    buttons.add(straight_dist);
			    buttons.add(straight_dist_label);
			    
			    buttons.add(track_straight);
			    buttons.add(track_straight_label);
			    
			    buttons.add(circ_orient);
			    buttons.add(circ_orient_label);

			    buttons.add(circ_ang);
			    buttons.add(circ_ang_label);

			    buttons.add(circ_rad);
			    buttons.add(circ_rad_label);

			    buttons.add(track_circ);
			    buttons.add(track_circ_label);
			    
			    buttons.add(CloseLoop);
			    

			    buttons.add(StartGame);
			    StartGame.setVisible(false);
			    planner_setup.setVisible(true);
			  //  CloseLoop.setVisible(false);
			    
			    
			    //Pályatervezés start
			    PLANNER plan = new PLANNER();
			    PLANNER plan1 = new PLANNER();
		        MAP[] act_map2 = {plan.CreateMap(),plan1.CreateMap()};
		      	int prevang[]= {0}, prevdist[]= {0};
		        GAMEPLAY[] planning = {new GAMEPLAY(act_map2[0],false)};
		        boolean closeable[]= {false};

			    CreateStraight.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
				        MATERIAL track_mat = new MATERIAL((String)track_straight.getSelectedItem());
				        act_map2[1] = plan1.CreateStraight(act_map2[1], prevang[0], prevdist[0], 5, track_mat);;
				        act_map2[0] = plan.CreateStraight(act_map2[0], Integer.parseInt(straight_angle.getText()), Integer.parseInt(straight_dist.getText()), 5, track_mat);			      
				        prevang[0] = Integer.parseInt(straight_angle.getText());
				        prevdist[0] = Integer.parseInt(straight_dist.getText());
				        planning[0].gamewindow.setVisible(false);
				        planning[0] = new GAMEPLAY(act_map2[0],false);
				        planning[0].gamewindow.setVisible(true);
				        closeable[0]=plan.LoopClosureCheck(act_map2[0]);
					    StartGame.setVisible(false);

				    //    if(!closeable[0]) CloseLoop.setVisible(false);
				   //     else CloseLoop.setVisible(true);
		           	}
			    });
			    
			    CreateCircle.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
				        MATERIAL track_mat = new MATERIAL((String)track_straight.getSelectedItem());
				       // act_map2[1] = plan1.CreateCircle(act_map2[1], prevang[0], prevdist[0], 5, track_mat);;
				        act_map2[0] = plan.CreateCircle(act_map2[0], Integer.parseInt(circ_orient.getText()), Integer.parseInt(circ_ang.getText()), Integer.parseInt(circ_rad.getText()), 5, track_mat);			      
				        //prevang[0] = Integer.parseInt(straight_angle.getText());
				        //prevdist[0] = Integer.parseInt(straight_dist.getText());
				        planning[0].gamewindow.setVisible(false);
				        planning[0] = new GAMEPLAY(act_map2[0],false);
				        planning[0].gamewindow.setVisible(true);
				        closeable[0]=plan.LoopClosureCheck(act_map2[0]);
					    StartGame.setVisible(false);

				       // if(!closeable[0]) CloseLoop.setVisible(false);
				       // else CloseLoop.setVisible(true);
		           	}
			    });
			    
			    CloseLoop.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
				        planning[0].gamewindow.setVisible(false);
				        planning[0] = new GAMEPLAY(plan.LoopClosure(act_map2[0]),false);
				        planning[0].gamewindow.setVisible(true);
					    StartGame.setVisible(true);

		           	}
			    });
		 
			    Clear.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    	act_map2[0] = plan.CreateMap();
				    planning[0].gamewindow.setVisible(false);
			        planning[0] = new GAMEPLAY(act_map2[0],false);
			        planning[0].gamewindow.setVisible(true);
				    StartGame.setVisible(false);

			    	
			    	}
			    });
			    
			    StartGame.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    	game = new GAMEPLAY(act_map2[0], false);
			        
			        game.gamewindow.setVisible(true);
		    	     
		    		single = true;

			    	
			    	}
			    });
	    
		        /*
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
		     	*/
		     	
		     	/*
		        game = new GAMEPLAY(act_map2, false);
		        
		        game.gamewindow.setVisible(true);
	    	     
	    		single = true;
	    		*/
	    		
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



