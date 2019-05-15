import java.util.*;
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GAMEPLAY extends JPanel implements WindowListener{

	public MAP map;
	private boolean gameMode;
	private VEHICLE v1;
	private VEHICLE v2;
	
	public boolean running;
	public int steer;
	public int drive;
	private BufferedImage car, car2;
	public JFrame gamewindow;
	private boolean playing;
	private long tellmil, tellmilbest=100;
	private int tellsec,tellsecbest=100, tellmin,tellminbest=100, ready;
	public int lap=1;
	public int checkpoint[]= {350,100};
	boolean checked=false;
	public boolean serverorclient;
	public Server server;
	public Client client;
	public boolean win = false;
	public boolean run = true;

	
	public GAMEPLAY(MAP map,boolean multi_player, boolean serverorclient) {
		this.map = map;
		this.gameMode = multi_player;
		this.serverorclient = serverorclient;
		playing = true;
		ready = 0;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		gamewindow = new JFrame("Gran Turismo Forza X");
      //gamewindow.getContentPane().setLayout(null);
	    gamewindow.add(this);
		gamewindow.setSize(1200, 700);
		gamewindow.addWindowListener(this);
		gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamewindow.setResizable(false);
		gamewindow.setLocation(dim.width/2-gamewindow.getSize().width/2, dim.height/2-gamewindow.getSize().height/2);
		
		if(this.serverorclient)
		{
			v1 = new VEHICLE(100,400,3*Math.PI/2);
			v2 = new VEHICLE(60,400,3*Math.PI/2);
			try {car = ImageIO.read(new File("kocsi.png"));
			} catch (IOException e){}
			try {car2 = ImageIO.read(new File("kocsi2.png"));
			} catch (IOException e){}
		}
		else
		{
			v1 = new VEHICLE(60,400,3*Math.PI/2);		
			v2 = new VEHICLE(100,400,3*Math.PI/2);
			try {car = ImageIO.read(new File("kocsi2.png"));
			} catch (IOException e){}
			try {car2 = ImageIO.read(new File("kocsi.png"));
			} catch (IOException e){}
		}
		
		int dist=0;
		for(int i=0;i<this.map.GetWidth();i++) {
			for(int j=0;j<this.map.GetHeight();j++) {
				if(this.map.TrackDraw[i][j]!=this.map.mat) {
					if((i-350)*(i-350)+(j+100)*(j+100)>dist) {
						dist=(i-350)*(i-350)+(j+100)*(j+100);
						checkpoint[0]=i;
						checkpoint[1]=j;
					}
				}
			}
		}
	
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_LEFT) ||  (e.getKeyCode() == KeyEvent.VK_RIGHT))
					steer = 0;

				if ((e.getKeyCode() == KeyEvent.VK_UP) ||  (e.getKeyCode() == KeyEvent.VK_DOWN))
					drive = 0;
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					steer = -1;
				if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					steer = 1;
				if (e.getKeyCode() == KeyEvent.VK_UP)
					drive = 1;
				if (e.getKeyCode() == KeyEvent.VK_DOWN)
					drive = -1;
			}
		});
		setFocusable(true);
	}
	
	public void refresh(double tau) {

	    v1.CalcNewPos(tau, map, drive, steer);
	    if (gameMode) {
	    	if (v2 != null) {
	    		if (Math.sqrt(Math.pow(v1.PosX-v2.PosX, 2)+Math.pow(v1.PosY-v2.PosY, 2)) < 25) {
	    		 	VehicleCollosion(0.5);
	    		}
	    	}
    		if(serverorclient) {
    			server.SendDatatoClient(v1);
    			v2 = server.getVehicle2();
    		}
    		else
    		{
    			client.SendDatatoServer(v1);
    			v2 = client.getVehicle2();
    		}
	    }
	    
	}
	
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g; //must have
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 36));
		
		int d = 40;
		switch (ready) {
		case 5:	
			AffineTransform oldXForm1 = g2d.getTransform();
			paintGAMEPLAY(g2d);
			g2d.setTransform(oldXForm1);
			g2d.setColor(new Color(0, 0, 0));
			g2d.fillRect(gamewindow.getWidth()/2-2*d+5, gamewindow.getHeight()/2-d+5, 4*d-10, 2*d-10);
			break;
			
		case 4:
			g2d.setColor(new Color(255, 0, 0));
			g2d.fillOval((int)(gamewindow.getWidth()/2+0.5*d+1),gamewindow.getHeight()/2-d/2+1,d-2,d-2);
			break;
			
		case 3:
			g2d.setColor(new Color(255, 0, 0));
			g2d.fillOval((int)(gamewindow.getWidth()/2-0.5*d+1),gamewindow.getHeight()/2-d/2+1,d-2,d-2);
			break;
			
		case 2:	
			g2d.setColor(new Color(255, 0, 0));
			g2d.fillOval((int)(gamewindow.getWidth()/2-1.5*d+1),gamewindow.getHeight()/2-d/2+1,d-2,d-2);
			break;
			
		case 1: 
			oldXForm1 = g2d.getTransform();
			paintGAMEPLAY(g2d);
			g2d.setTransform(oldXForm1);
			g2d.setColor(new Color(0, 0, 0));
			g2d.fillRect(gamewindow.getWidth()/2-2*d+5, gamewindow.getHeight()/2-d+5, 4*d-10, 2*d-10);
			g2d.setColor(new Color(0, 255, 0));
			g2d.fillOval((int)(gamewindow.getWidth()/2-0.5*d+1),gamewindow.getHeight()/2-d/2+1,d-2,d-2);
			g2d.fillOval((int)(gamewindow.getWidth()/2-1.5*d+1),gamewindow.getHeight()/2-d/2+1,d-2,d-2);
			g2d.fillOval((int)(gamewindow.getWidth()/2+0.5*d+1),gamewindow.getHeight()/2-d/2+1,d-2,d-2);
			g2d.setColor(new Color(0, 0, 0));
			String time = String.format("%02d" + ":" + "%02d" + ":" + "%02d", tellmin, tellsec, tellmil/10);
			g2d.drawString(time, 10, 30);
			break;

		default:
			if (playing) { 
				AffineTransform oldXForm = g2d.getTransform();
				
				
				if (tellsec > 2 && tellsec < 4) {
					DrawMap(g2d,gamewindow.getWidth()/2-2*d+5, gamewindow.getHeight()/2-d+5, 2*d, 4*d);
				}
				paintGAMEPLAY(g2d);
				g2d.setTransform(oldXForm); // Restore transform
				g2d.setColor(new Color(0, 0, 0));
				
				time = String.format("Actual Time: %02d" + ":" + "%02d" + ":" + "%02d", tellmin, tellsec, tellmil/10);
				g2d.drawString(time, 10, 30);
				time = String.format("Best Time: %02d" + ":" + "%02d" + ":" + "%02d", tellminbest, tellsecbest, tellmilbest/10);
				g2d.drawString(time, 400, 30);
				time = String.format("Lap: %02d", lap);
				g2d.drawString(time, 800, 30);
			//	time = String.format( "%02d" + ":" + "%02d", checkpoint[0], checkpoint[1]);
			//	g2d.drawString(time, 1000, 30);
			//	time = String.format( "%b" , checked);
			//	g2d.drawString(time,1000,30);
			}
		}
	}
	
	public void paintGAMEPLAY(Graphics2D g2d) {
		
		AffineTransform oldXForm = g2d.getTransform();
		
		//autó sarkai
		double CarWidth = (Math.abs(car.getHeight()*Math.cos(v1.Ori))+Math.abs(car.getWidth()*Math.sin(v1.Ori)));
		double CarHeight = (Math.abs(car.getHeight()*Math.sin(v1.Ori))+Math.abs(car.getWidth()*Math.cos(v1.Ori)));
		int CornerX = (int)(v1.PosX-(car.getHeight()*Math.cos(v1.Ori)+car.getWidth()*Math.sin(v1.Ori))/2);
		int CornerY = (int)(v1.PosY-(car.getHeight()*Math.sin(v1.Ori)-car.getWidth()*Math.cos(v1.Ori))/2);
		
		double Ori = v1.Ori - v1.AngVel * v1.dt;
		int LeftCornerX = (int)(v1.PosX-CarWidth/2-v1.Vel*Math.cos(Ori)*v1.dt);
		int LeftCornerY = (int)(v1.PosY-CarHeight/2-v1.Vel*Math.sin(Ori)*v1.dt);
		

		//pálya rajzolása
		if (running) {
			DrawMap(g2d,LeftCornerX-10,LeftCornerY-10,(int)CarHeight+20,(int)CarWidth+20);
		}
		else {
			DrawMap(g2d,1,1,map.GetHeight(), map.GetWidth());
			running = true;
		}
		DrawMap(g2d,0,0,40,1200);
		
		//ha multi
		if (gameMode) {
			double CarWidth2 = (Math.abs(car2.getHeight()*Math.cos(v2.Ori))+Math.abs(car2.getWidth()*Math.sin(v2.Ori)));
			double CarHeight2 = (Math.abs(car2.getHeight()*Math.sin(v2.Ori))+Math.abs(car2.getWidth()*Math.cos(v2.Ori)));
			int CornerX2 = (int)(v2.PosX-(car2.getHeight()*Math.cos(v2.Ori)+car2.getWidth()*Math.sin(v2.Ori))/2);
			int CornerY2 = (int)(v2.PosY-(car2.getHeight()*Math.sin(v2.Ori)-car2.getWidth()*Math.cos(v2.Ori))/2);
			
			double Ori2 = v2.Ori - v2.AngVel * v2.dt;
			int LeftCornerX2 = (int)(v2.PosX-CarWidth2/2-v2.Vel*Math.cos(Ori2)*v2.dt);
			int LeftCornerY2 = (int)(v2.PosY-CarHeight2/2-v2.Vel*Math.sin(Ori2)*v2.dt);

			DrawMap(g2d,LeftCornerX2-10,LeftCornerY2-10,(int)CarHeight2+20,(int)CarWidth2+20);
			g2d.rotate(v2.Ori-Math.PI/2, CornerX2, CornerY2);
		    g2d.drawImage(car2, CornerX2, CornerY2, this);
		    g2d.setTransform(oldXForm);
		}
		
	    
		//autó kirajzolása	
			g2d.rotate(v1.Ori-Math.PI/2, CornerX, CornerY);
			g2d.drawImage(car, CornerX, CornerY, this);

	}
	
	public void windowActivated(WindowEvent arg0) {
		running = false;
	}  
	public void windowClosed(WindowEvent arg0) {}  
	public void windowClosing(WindowEvent arg0) {}  
	public void windowDeactivated(WindowEvent arg0) {}  
	public void windowDeiconified(WindowEvent arg0) {
		running = false;
	}  
	public void windowIconified(WindowEvent arg0) {}  
	public void windowOpened(WindowEvent arg0) {}
	
    
	public void DrawMap(Graphics2D g2d, int startX, int startY, int height, int width) {
		startX = Math.max(startX-1,0);
		startY = Math.max(startY-1,0);
		int endX = Math.min(startX+width-1,gamewindow.getWidth());
		int endY = Math.min(startY+height-1,gamewindow.getHeight());
		int x0 = startX;
		int x1 = x0;
		for (int y=startY;y<endY;y++) {
			while (x0 < endX) {
				int[] color = this.map.TrackDraw[x0][y].getCol();
				g2d.setColor(new Color(color[0], color[1], color[2]));
				
				if (x1+1 < endX) {
					while (this.map.TrackDraw[x1+1][y] == this.map.TrackDraw[x0][y] && x1+2 < endX) {
						x1++;
					}
				}

				g2d.drawLine(x0, y, x1, y);
				x1++;
				x0 = x1;
			}
			x0 = startX;
			x1 = x0;
		}
	}

	
	public void startServer(Server server) {
		this.server = server;
		if (this.server.StartServer()) {
			this.server.start_receive();
            new Thread(this.server::receive_loop).start();
        }
	}
	
	public void startClient(Client client) {
		this.client = client;
		if (this.client.ConnectToServer()){
			this.client.start_receive();
            new Thread(this.client::receive_loop).start();
        }
    }

	
	
	public void GameCycle() {
		long t0, t1, t2;
		double tau;
		
		this.repaint();
		ready = 5;
		repaint();
		try {Thread.sleep(2000);} catch(Exception e) {}
		ready = 4;
		repaint();
		try {Thread.sleep(1000);} catch(Exception e) {}
		ready = 3;
		repaint();
		try {Thread.sleep(1000);} catch(Exception e) {}
		ready = 2;
		repaint();
		try {Thread.sleep(1000);} catch(Exception e) {}
		ready = 1;
		repaint();
		try {Thread.sleep(1000);} catch(Exception e) {}
		ready = 0;
		repaint();
			
		playing = true;
	
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
	    refresh(tau);
	 	repaint();
	 	t1 = System.nanoTime();
	 	
	 	if((v1.PosX>(checkpoint[0]-100)) && (v1.PosX<(checkpoint[0]+100)) && (v1.PosY>(checkpoint[1]-100)) && (v1.PosY<(checkpoint[1]+100))) {
	 		checked=true;
	 	}
	 	
	    if((v1.PosX>50) && (v1.PosY>50) && (v1.PosY<350) && (v1.PosX<150) && checked) {
	    	t0 = System.currentTimeMillis();
	 	    t1 = System.nanoTime();
	 	    if(tellsec>2) {
	 	    	lap++;
	 	    }
	 	    if((tellmin<tellminbest) && (tellsec>2)) {
	 	    	tellminbest=tellmin;
	 	    	tellsecbest=tellsec;
	 	    	tellmilbest=tellmil;
	 	    }
	 	    else if((tellmin==tellminbest) && (tellsec>2)) {
	 	    	if(tellsec<tellsecbest) {
	 	    		tellminbest=tellmin;
		 	    	tellsecbest=tellsec;
		 	    	tellmilbest=tellmil;
	 	    	}
	 	    	else if((tellsec==tellsecbest) && (tellsec>2)) {
	 	    		if(tellmil<tellmilbest) {
	 	    			tellminbest=tellmin;
	 		 	    	tellsecbest=tellsec;
	 		 	    	tellmilbest=tellmil;
	 	    		}
	 	    	}
	 	    }
	    	tellmil=0;
	    	tellmin=0;
	    	tellsec=0;
	    	checked=false;
	    }
	    if(gameMode && v2 != null)
	    {
	    	if(lap == 5)
	    	{
	    		if(!v2.winner)
	    		{
	    		run = false;
	    		win = true;	    		
	    		v1.winner = true;
	    		if(serverorclient)
	    		{
	    			server.SendDatatoClient(v1);
	    		}
	    		else
	    		{
	    			client.SendDatatoServer(v1);
	    		}
	    		
	    		break;
	    		}
	    	}
	    	if(v2.winner)
	    	{
	    		run = false;
	    		win = false;
	    		break;
	    	}
	    	
	    }
	    }
	}
	
	public void VehicleCollosion(double k) {
		double collang = Math.atan2(v2.PosY-v1.PosY, v2.PosX-v1.PosX);
		
		double Ori1 = v1.Ori % (Math.PI*2);
		if (Ori1 > Math.PI) Ori1 = Math.PI-Ori1;
		
		double Ori2 = v2.Ori % (Math.PI*2);
		if (Ori2 > Math.PI) Ori2 = Math.PI-Ori2;
		
		double Vel10 = v1.Vel*Math.cos(collang - Ori1);
		double Vel20 = v2.Vel*Math.cos(collang - Ori2);
		
		if (Vel10-Vel20 > 0) {
			double Vel11 = (Vel10+Vel20)/2+k*(Vel20-Vel10)/2;
		
			v1.Vel = Math.sqrt(Math.pow(v1.Vel*Math.sin(collang - Ori1),2)+Math.pow(Vel11,2));
			v1.Ori = collang-Math.atan2(v1.Vel*Math.sin(collang - Ori1),Vel11);
		
			//v1.PosX = v1.PosX+0.1*v1.Vel*Math.cos(v1.Ori);
			//v1.PosY = v1.PosY+0.1*v1.Vel*Math.sin(v1.Ori);
		}
	}
	
}
