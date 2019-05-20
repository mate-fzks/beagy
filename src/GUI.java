import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;



public class GUI extends JPanel{

	private GAMEPLAY game;
	private volatile boolean single;
	private volatile boolean serverStart = false, clientstart = false;
	private volatile boolean textgot = false;
	private volatile boolean Multi = false;
	private volatile boolean newserver = true;
	private volatile boolean birmodeon = false;
	private JFrame menu, networkmenu, waitforclient, ingame_menu, ipaddmenu;
	private String ipadress;
	JCheckBox birmode;

	
	public void GUIStart() {
		
		SetUpGUI(this);

		menu.setVisible(true);
 
		while (true) {
			if (single) {
				if(game != null)
				{
					game.GameCycle();
				}
			}
			if (serverStart) {
				try {
					game = null;
					PLANNER plan[] = {new PLANNER()};
					MAP act_map[] = {plan[0].DefaultMap()};
					game = new GAMEPLAY(act_map[0],true,true,birmodeon);
				 	Server s =new Server();
				 	game.startServer(s);
				 	waitforclient.setVisible(false);
				 	game.gamewindow.setVisible(true);
				 	Multi = true;
				 	serverStart = false;

		        
		    	    ingame_menu = new JFrame("Menu");
		    		ingame_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		        	ingame_menu.setSize(1200, 100);
		    		JPanel panel = new JPanel();
		    		panel.setLayout(new FlowLayout());
		    		ingame_menu.add(panel);
		        	JButton Menu = new JButton("Menu");
		        	Menu.setBounds(50, 20, 100, 20);
		    		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		    		ingame_menu.setLocation(dim.width/2-ingame_menu.getSize().width/2, dim.height/2-ingame_menu.getSize().height/2-390);
		    		panel.add(Menu);
		    		ingame_menu.setVisible(true);
		    	    
		    		Menu.addActionListener(new ActionListener() {
				    	public void actionPerformed(ActionEvent e) {
				    		game.gamewindow.setVisible(false);
				    		game.backtomenu = true;
				    		game.server.stop_receive();
				    		game.server.StopServer();
				    		menu.setVisible(true);
				    	    ingame_menu.setVisible(false);
				    	    single = false;
				    	    Multi = false;  
				    	}
		    		});
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (clientstart) {
				game = null;
				PLANNER plan[] = {new PLANNER()};
				MAP act_map[] = {plan[0].DefaultMap()};
        		game = new GAMEPLAY(act_map[0],true,false,birmodeon);
        		Client c = new Client(ipadress);
	        		game.startClient(c);
	        		game.gamewindow.setVisible(true);
	        		Multi = true;
	        		clientstart = false;
	        		ipaddmenu.setVisible(false);
	        		ingame_menu = new JFrame("Menu");
		    		ingame_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		        	ingame_menu.setSize(1200, 100);
		    		JPanel panel = new JPanel();
		    		panel.setLayout(new FlowLayout());
		    		ingame_menu.add(panel);
		        	JButton Menu = new JButton("Menu");
		        	Menu.setBounds(50, 20, 100, 20);
		    		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		    		ingame_menu.setLocation(dim.width/2-ingame_menu.getSize().width/2, dim.height/2-ingame_menu.getSize().height/2-390);
		    		panel.add(Menu);
		    		ingame_menu.setVisible(true);
		    	    
		    		Menu.addActionListener(new ActionListener() {
				    	public void actionPerformed(ActionEvent e) {
				    		game.gamewindow.setVisible(false);
				    		game.backtomenu = true;
				    		game.client.stop_receive();
				    		game.client.Disconnect();
				    		menu.setVisible(true);
				    	    ingame_menu.setVisible(false);
				    	    single = false;
				    	    Multi = false;
				    	}
		    		});
			}
				if(Multi)
				{
					if(game != null)
					{
					game.GameCycle();
					if(game != null)
					{
					if(!game.run)
					{
						ingame_menu.setVisible(false);
						break;
					}
					}
					}
				}

		}
		showresult(game.win);

	}
	
	
	public static boolean isNum(String strNum) {
	    boolean ret = true;
	    try {

	        Double.parseDouble(strNum);

	    }catch (NumberFormatException e) {
	        ret = false;
	    }
	    return ret;
	}
	
	public void SetUpGUI(GUI gui) {

		
		menu = new JFrame("Menu");
	    menu.add(gui);
		menu.setSize(400, 200);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//menu.setResizable(false);
		
	
		birmode = new JCheckBox("Bír mod");
		
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		//új menü a multiplayerhez
		networkmenu = new JFrame("Menu");
		networkmenu.add(gui);
		networkmenu.setSize(400, 400);
		networkmenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		networkmenu.setResizable(false);
		
		//a kliensre váráshoz
		waitforclient = new JFrame();
		waitforclient.add(gui);
		JPanel waitforclientjp = new JPanel();
		JLabel waitforclientjl = new JLabel("Waiting for Client");
		waitforclient.setTitle("Menu");
		waitforclient.setSize(400,200);
		waitforclient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		waitforclientjl.setText("Waiting for Client");
		waitforclientjl.setHorizontalAlignment(JLabel.CENTER);
		waitforclientjl.setVerticalAlignment(JLabel.CENTER);
		waitforclientjp.add(waitforclientjl);
		waitforclient.add(waitforclientjp);
		
		//ip cim kéréshez 
		ipaddmenu = new JFrame();
		JPanel ipaddmenujp = new JPanel();
		JTextField ipaddmenujt = new JTextField(30);
		ipaddmenu.setTitle("Menu");
		ipaddmenu.setSize(400,200);
		ipaddmenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ipaddmenujp.add(ipaddmenujt);
		ipaddmenu.add(ipaddmenujp);
		JButton ipaddmenujb = new JButton("Enter");
		ipaddmenujp.add(ipaddmenujb);
		
		
		menu.add(buttons);
		
	
		
		JButton ButtonPlanner = new JButton("Track Planner");
		JButton ButtonSingle = new JButton("Singleplayer");
		JButton ButtonMulti = new JButton("Multiplayer");
		buttons.add(birmode);
		
		event e = new event();
		birmode.addItemListener(e);
		
		
		PLANNER plan[] = {new PLANNER()};
		MAP act_map[] = {plan[0].DefaultMap()};
		
		
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
	        	String tracks[]= {"GRAVEL","GRASS","MUD","WATER","SNOW","ICE"};
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
				JButton Redesign = new JButton("Redesign");
				JButton CloseLoop = new JButton("Close Loop");
				JButton SaveTrack = new JButton("SaveTrack");
				JButton Menu = new JButton("Menu");
				
				CreateStraight.setBounds(100, 20, 200, 20);
				CreateCircle.setBounds(400, 20, 200, 20);
				Redesign.setBounds(300, 300, 100, 20);
				CloseLoop.setBounds(100,300,100,20);
				SaveTrack.setBounds(275, 400, 150, 40);
				Menu.setBounds(500,300,100,20);
				
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
			    buttons.add(Redesign); 
			    buttons.add(Menu);
			   
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
			    

			    buttons.add(SaveTrack);
			    SaveTrack.setVisible(false);
			    planner_setup.setVisible(true);
			    CloseLoop.setVisible(false);
			    
			    
			    //Pályatervezés start
			    PLANNER plan[] = {new PLANNER()};
			    PLANNER plan1[] = {new PLANNER()};
			    PLANNER planb[] = {new PLANNER()};
			    PLANNER planb1[] = {new PLANNER()};
		        MAP[] act_map2 = {plan[0].CreateMap(),plan1[0].CreateMap()};
		        MAP[] backgound = {planb[0].CreateMap(), planb1[0].CreateMap()};
		        GAMEPLAY[] planning = {new GAMEPLAY(act_map2[0],false,true,birmodeon)};
		        boolean closeable[]= {false};
		        boolean firststep[]= {true};
		        boolean straight[] = {true};
		        int prevang[]= {0},prevdist[]= {0}, prevcircori[]= {0}, prevcircang[]= {0},prevcircrad[]= {0};
		        MATERIAL track_surface = new MATERIAL("TARMAC");
		        
			    CreateStraight.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		MATERIAL track_mat = new MATERIAL((String)track_straight.getSelectedItem());
			    		if(	isNum(straight_angle.getText()) && isNum(straight_dist.getText())) {
			    			//track
			    			if(!firststep[0] && straight[0])  act_map2[1] = plan1[0].CreateStraight(act_map2[1], prevang[0], prevdist[0], 5, track_surface);
				    		if(!firststep[0] && !straight[0])  act_map2[1] = plan1[0].CreateCircle(act_map2[1], prevcircori[0],prevcircang[0],prevcircrad[0], 5, track_surface);
				    		//background
				    		if(!firststep[0] && straight[0])  backgound[1] = planb1[0].CreateStraight(backgound[1], prevang[0], prevdist[0], 5, track_mat);
				    		if(!firststep[0] && !straight[0])  backgound[1] = planb1[0].CreateCircle(backgound[1], prevcircori[0],prevcircang[0],prevcircrad[0], 5, track_mat);
				    		
				    		
				    		firststep[0]=false;
				    		straight[0]=true;
					        act_map2[0] = plan[0].CreateStraight(act_map2[0], Integer.parseInt(straight_angle.getText()), Integer.parseInt(straight_dist.getText()), 5, track_surface);	
					        backgound[0] = planb[0].CreateStraight(backgound[0], Integer.parseInt(straight_angle.getText()), Integer.parseInt(straight_dist.getText()), 5, track_mat);	

			    		}    
				        prevang[0]=Integer.parseInt(straight_angle.getText());
				        prevdist[0]=Integer.parseInt(straight_dist.getText());
		
				        planning[0].gamewindow.setVisible(false);
				        planning[0] = new GAMEPLAY(plan[0].DisplayMap(act_map2[0], backgound[0]),false,true,birmodeon);
				        planning[0].gamewindow.setVisible(true);
				        closeable[0]=plan[0].LoopClosureCheck(act_map2[0]);
				        SaveTrack.setVisible(false);

				        if(!closeable[0]) CloseLoop.setVisible(false);
				        else CloseLoop.setVisible(true);
		           	}
			    });
			    
			    CreateCircle.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
				        MATERIAL track_mat = new MATERIAL((String)track_circ.getSelectedItem());
				      if(isNum(circ_orient.getText()) && isNum(circ_ang.getText()) && isNum(circ_rad.getText())) {
					        
				    	  //track
				    	  	if(!firststep[0] && prevcircang[0]!=0 && prevcircrad[0]!=0 && !straight[0]) act_map2[1] = plan1[0].CreateCircle(act_map2[1], prevcircori[0],prevcircang[0],prevcircrad[0], 5, track_surface);
					        if(!firststep[0] && prevcircang[0]==0 && prevcircrad[0]==0) act_map2[1] = plan1[0].CreateStraight(act_map2[1], 0, 0, 5, track_surface);
					        if(!firststep[0] && straight[0])  act_map2[1] = plan1[0].CreateStraight(act_map2[1], prevang[0], prevdist[0], 5, track_surface);
				    		
					        if(!firststep[0] && prevcircang[0]!=0 && prevcircrad[0]!=0 && !straight[0]) act_map2[1] = plan1[0].CreateCircle(act_map2[1], prevcircori[0],prevcircang[0],prevcircrad[0], 5, track_surface);
					        if(!firststep[0] && prevcircang[0]==0 && prevcircrad[0]==0) act_map2[1] = plan1[0].CreateStraight(act_map2[1], 0, 0, 5, track_surface);
					        if(!firststep[0] && straight[0])  act_map2[1] = plan1[0].CreateStraight(act_map2[1], prevang[0], prevdist[0], 5, track_surface);

					      //background  
					        if(!firststep[0] && prevcircang[0]!=0 && prevcircrad[0]!=0 && !straight[0]) backgound[1] = planb1[0].CreateCircle(backgound[1], prevcircori[0],prevcircang[0],prevcircrad[0], 5, track_mat);
					        if(!firststep[0] && prevcircang[0]==0 && prevcircrad[0]==0) backgound[1] = planb1[0].CreateStraight(backgound[1], 0, 0, 5, track_mat);
					        if(!firststep[0] && straight[0])  backgound[1] = planb1[0].CreateStraight(backgound[1], prevang[0], prevdist[0], 5, track_mat);
				    		
					        if(!firststep[0] && prevcircang[0]!=0 && prevcircrad[0]!=0 && !straight[0]) backgound[1] = planb1[0].CreateCircle(backgound[1], prevcircori[0],prevcircang[0],prevcircrad[0], 5, track_mat);
					        if(!firststep[0] && prevcircang[0]==0 && prevcircrad[0]==0) backgound[1] = planb1[0].CreateStraight(backgound[1], 0, 0, 5, track_mat);
					        if(!firststep[0] && straight[0])  backgound[1] = planb1[0].CreateStraight(backgound[1], prevang[0], prevdist[0], 5, track_mat);

					        
					        straight[0]=false;
					        firststep[0]=false;
					        act_map2[0] = plan[0].CreateCircle(act_map2[0], Integer.parseInt(circ_orient.getText()), Integer.parseInt(circ_ang.getText()), Integer.parseInt(circ_rad.getText()), 5, track_surface);			      
					        backgound[0] = planb[0].CreateCircle(backgound[0], Integer.parseInt(circ_orient.getText()), Integer.parseInt(circ_ang.getText()), Integer.parseInt(circ_rad.getText()), 5, track_mat);			      

				      }
				        prevcircori[0]=Integer.parseInt(circ_orient.getText());
				        prevcircang[0]=Integer.parseInt(circ_ang.getText());
				        prevcircrad[0]=Integer.parseInt(circ_rad.getText());
				        
				        planning[0].gamewindow.setVisible(false);
				        planning[0] = new GAMEPLAY(plan[0].DisplayMap(act_map2[0], backgound[0]),false,true,birmodeon);
				        planning[0].gamewindow.setVisible(true);
				        closeable[0]=plan[0].LoopClosureCheck(act_map2[0]);
				        SaveTrack.setVisible(false);

				        if(!closeable[0]) CloseLoop.setVisible(false);
				        else CloseLoop.setVisible(true);
		           	}
			    });
			    
			    CloseLoop.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		MATERIAL track_mat = new MATERIAL((String)track_straight.getSelectedItem());
				      //  planning[0].gamewindow.setVisible(false);
				      //  planning[0] = new GAMEPLAY(plan[0].LoopClosure(act_map2[0]),false,true);
				      //  planning[0].gamewindow.setVisible(true);
				        backgound[0] = planb[0].CreateBackground(backgound[0],track_mat); 
				        act_map2[0]=plan[0].LoopClosure(act_map2[0]);
				        SaveTrack.setVisible(true);
				        CreateStraight.setVisible(false);
				        CreateCircle.setVisible(false);
				        CloseLoop.setVisible(false);
				        
				        MAP trackmap = new MAP(backgound[0]);
				        
				        for (int i=0;i<trackmap.GetWidth();i++) {
				        	for(int j=0;j<trackmap.GetHeight();j++) {
				        		if(act_map2[0].TrackDraw[i][j].getGrip()==300) {
				        			trackmap.TrackDraw[i][j]=new MATERIAL("TARMAC");
				        		}
				        	}
				        }
				        
				        planning[0].gamewindow.setVisible(false);
				        planning[0] = new GAMEPLAY(trackmap,false,true,birmodeon);
				        planning[0].gamewindow.setVisible(true);
				        act_map2[0]=trackmap;
		           	}
			    });
		 
			    Redesign.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		plan[0] = new PLANNER(plan1[0]);
			    		planb[0] = new PLANNER(planb1[0]);
			    		
		        		act_map2[0] = new MAP(act_map2[1]);
		        		backgound[0] = new MAP(backgound[1]);
	
				    	firststep[0]=true;
				    	
				    	planning[0].gamewindow.setVisible(false);
				        planning[0] = new GAMEPLAY(plan[0].DisplayMap(act_map2[0], backgound[0]),false,true,birmodeon);
				        planning[0].gamewindow.setVisible(true);
				        closeable[0]=plan[0].LoopClosureCheck(act_map2[0]);
				        SaveTrack.setVisible(false);
				        CreateStraight.setVisible(true);
				        CreateCircle.setVisible(true);
				        if(!closeable[0]) CloseLoop.setVisible(false);
				        else CloseLoop.setVisible(true);

			    	  	}
			    });
			    
			    SaveTrack.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		act_map[0]=act_map2[0];
			    		planner_setup.setVisible(false);
			    		menu.setVisible(true);
				    	planning[0].gamewindow.setVisible(false);
			    	}
			    });
			    
			    Menu.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		planner_setup.setVisible(false);
			    		menu.setVisible(true);
				    	planning[0].gamewindow.setVisible(false);
			    	
			    	}
			    });
	       		
	         }          
	    });
	    //single player mód kiválasztva
	    ButtonSingle.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	    		menu.setVisible(false);

	    	    game = new GAMEPLAY(act_map[0],false,true,birmodeon);
	    	    
	    	    game.gamewindow.setVisible(true);
	    	     
	    		single = true;
	    		
	        
	    	    ingame_menu = new JFrame("Menu");
	    		ingame_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        	ingame_menu.add(gui);
	        	ingame_menu.setSize(1200, 100);
	    		JPanel panel = new JPanel();
	    		panel.setLayout(new FlowLayout());
	    		ingame_menu.add(panel);
	        	JButton Menu = new JButton("Menu");
	        	Menu.setBounds(50, 20, 100, 20);
	    		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    		ingame_menu.setLocation(dim.width/2-ingame_menu.getSize().width/2, dim.height/2-ingame_menu.getSize().height/2-390);
	    		panel.add(Menu);
	    	    ingame_menu.setVisible(true);
	    	    
	    		Menu.addActionListener(new ActionListener() {
			    	public void actionPerformed(ActionEvent e) {
			    		game.gamewindow.setVisible(false);
			    		game.backtomenu = true;
			    		menu.setVisible(true);
			    	    ingame_menu.setVisible(false);
			    	    single = false;
			    	   
			    	    while(true)
			    	    {
			    	    	if(game.endnow)
			    	    	{
			    	    		game = null;
			    	    		break;
			    	    	}
			    	    }
			    	}
			 });
	    	     
	    	     
	    		 
	         }
	    });
	    ButtonMulti.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	menu.setVisible(false);
	     		single = false;
	         	JPanel multiplayerbuttons = new JPanel();
	         	multiplayerbuttons.setLayout(new FlowLayout());
	         	
	         	networkmenu.add(multiplayerbuttons); 
	         	
	         	JButton createserver = new JButton("Create Server");
	         	JButton joinserver = new JButton("Join Server");
	         	JButton backtomenu = new JButton("Back to menu");
	         	
	         	networkmenu.setVisible(true);
	         	
	         	createserver.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						menu.setVisible(false);
			    	    single = false;
						networkmenu.setVisible(false);
						waitforclient.setVisible(true);
						serverStart = true;						
						
	     	         }          
	         	});
	         	joinserver.addActionListener(new ActionListener() {
	        	         public void actionPerformed(ActionEvent e) {     
					    	single = false;	        	        	 
	        	        	networkmenu.setVisible(false);
	         	        	ipaddmenu.setVisible(true);
	        	        	ipaddmenujb.addActionListener(new ActionListener()
	        	        	{
	        	        	
	        	        	public void actionPerformed(ActionEvent d)
       	        		{
	        	        		ipadress = ipaddmenujt.getText();
	        	        		clientstart = true;
	        	        		
       	        		}
	        	        	});
	        	        	
	        	        	ipaddmenu.add(ipaddmenujp);
	     				  	}          
	             	});
	         	backtomenu.addActionListener(new ActionListener() {
	         		 public void actionPerformed(ActionEvent e) { 
	         			networkmenu.setVisible(false);
	         			menu.setVisible(true);
	         		} 
	         	});
	         	multiplayerbuttons.add(createserver);
	         	multiplayerbuttons.add(joinserver); 
	         	multiplayerbuttons.add(backtomenu);
	         }
	      });

	    
	     buttons.add(ButtonPlanner);
	     buttons.add(ButtonSingle);
	     buttons.add(ButtonMulti);        
	}
	
	public void showresult(boolean win)
	{
		JFrame result = new JFrame();
		JPanel resultjp = new JPanel();
		JLabel resultjl = new JLabel();
		result.setSize(400,200);
		result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		resultjl.setHorizontalAlignment(JLabel.CENTER);
		resultjl.setVerticalAlignment(JLabel.CENTER);
		if(win)
		{
			result.setTitle("You Win!");
			resultjl.setText("You Win!");
		}
		else
		{
			result.setTitle("You Lost!");
			resultjl.setText("You Lost!");
		}
		resultjp.add(resultjl);
		result.add(resultjp);
		result.setVisible(true);
	}

	public class event implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if(birmode.isSelected())
			{
				birmodeon= true;
			//	 System.out.println("birmode on");
			}
			else
			{
				birmodeon = false;
		//		System.out.println("birmode off");
			}
		} 	
	}
}

