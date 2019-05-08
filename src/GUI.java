import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;


public class GUI extends JPanel implements WindowListener{

	private GAMEPLAY game;
	private PLANNER plan;
	private boolean playing;
	private volatile boolean single;
	private long tellmil;
	private int tellsec, tellmin;
	
	private JFrame menu, gamewindow;

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
   	 
		 
		
		SetUpGUI(this);
	    MainMenu();
 
		
		while (true) {
			if (single) {
				GameCycle();
			}
		}
		

	}
	
	public void SetUpGUI(GUI gui) {
		playing = false;
		
		menu = new JFrame("Gran Turismo Forza X");
	    menu.add(gui);
		menu.setSize(400, 400);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setResizable(false);
		
		gamewindow = new JFrame("Gran Turismo Forza X");
	    gamewindow.add(gui);
		gamewindow.setSize(1200, 700);
		gamewindow.addWindowListener(gui);
		gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamewindow.setResizable(false);
		
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
	    		 playing = true;
	    		 menu.setVisible(false);
	    		 plan = new PLANNER();
	    	     MAP act_map1 = plan.DefaultMap();
	    	     game = new GAMEPLAY(act_map1,false, gui);
	        	 game.init();
	        	 
	    		 gamewindow.setVisible(true);
	    		 	    	     
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
	
	public void MainMenu() {
		menu.setVisible(true);
	}	
	
	
	
	public void GameCycle() {
		long t0, t1, t2;
		double tau;
	    t0 = System.currentTimeMillis();
	    t1 = System.nanoTime();
	    while (true) {		
	    do {
	    	t2 = System.nanoTime();
		  	tau = (double)(t2-t1)/1000000;
	    } while(tau < 10);
	    //eltelt idõ számítása
	    tellmil = System.currentTimeMillis()-t0;
	    tellmin = (int)tellmil/1000/60;
	    tellmil = tellmil % (1000*60);
	    tellsec = (int)tellmil/1000;
	    tellmil = tellmil % (1000);
	    calculate(tau);
	 	repaint();
	 	t1 = System.nanoTime();
	    }
	} 
	
	public void calculate(double tau) {
		game.refresh(tau);
	}
	
	public void paint(Graphics g) {
		if (playing) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 36)); 
		AffineTransform oldXForm = g2d.getTransform();
		game.paint(g2d);
		
		g2d.setTransform(oldXForm); // Restore transform
		g2d.setColor(new Color(0, 0, 0));
		String time = String.format("%02d" + ":" + "%02d" + ":" + "%02d", tellmin, tellsec, tellmil/10);
		g2d.drawString(time, 10, 30);
		}
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



