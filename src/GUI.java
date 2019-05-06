import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class GUI extends JPanel implements WindowListener{

	private GAMEPLAY game;
	private PLANNER plan;
	boolean single;

	public GUI() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				game.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				game.keyPressed(e);
			}
		});
		setFocusable(true);
	}

	
	public void GUIStart() {
	     plan = new PLANNER();
	     MAP act_map1 = plan.DefaultMap();
	     game = new GAMEPLAY(act_map1,false, this);
    	 game.init();
   	 
    	 //Ablak létrehozása
		 JFrame frame= new JFrame("Gran Turismo Forza X");
	     frame.add(this);
	     frame.addWindowListener(this);
		 frame.setSize(1200, 700);
		 frame.setVisible(true);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setResizable(false);

	     
	     int sleep = 10;
	     while (true)
	     { 
	    	 	calculate(sleep);
	 	     	repaint();
	 	        try{
	 	             Thread.sleep(sleep);
	 	        } catch (Exception exc){}
				//game.running = true;
	      } 

	}
	
	public void calculate(int sleep) {
		game.refresh(sleep);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		game.paint(g2d);	
	}
	public void windowActivated(WindowEvent arg0) {}  
	public void windowClosed(WindowEvent arg0) {}  
	public void windowClosing(WindowEvent arg0) {}  
	public void windowDeactivated(WindowEvent arg0) {}  
	public void windowDeiconified(WindowEvent arg0) {
		game.running = false;
	}  
	public void windowIconified(WindowEvent arg0) {}  
	public void windowOpened(WindowEvent arg0) {} 
}


/*
 * 
 * 
 * 		 JPanel buttons = new JPanel();
		 buttons.setLayout(new FlowLayout());
 * 
 *  JButton ButtonPlanner = new JButton("Track Planner");
		 JButton ButtonSingle = new JButton("Singleplayer");
		 JButton ButtonMulti = new JButton("Multiplayer");
		 
	     
	     //buttons.add(ButtonPlanner);
	     //buttons.add(ButtonSingle);
	     //buttons.add(ButtonMulti);   
	     
	     //frame.add(buttons);
 * 
 * 
 *  ButtonPlanner.addActionListener(new ActionListener() {
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
		 
		 //Click Single
	     ButtonSingle.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 PLANNER plan = new PLANNER();
	        	 MAP act_map1 = plan.DefaultMap();
	        	 game = new GAMEPLAY(act_map1,false);
	        	 game.init();
	        	 System.out.println("Single");
	        	 single = true;
	         }
	     });
	     
	     ButtonMulti.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            
	         }
	     });
 * */
