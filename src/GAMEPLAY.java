import java.util.*;
import java.awt.*;
import javax.swing.*;
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
	private BufferedImage car;
	public JFrame gamewindow;
	private boolean playing;
	private long tellmil;
	private int tellsec, tellmin, ready;
	
	public GAMEPLAY(MAP map,boolean multi_player) {
		this.map = map;
		this.gameMode = multi_player;
		playing = true;
		ready = 0;
		
		gamewindow = new JFrame("Gran Turismo Forza X");
	    gamewindow.add(this);
		gamewindow.setSize(1200, 700);
		gamewindow.addWindowListener(this);
		gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamewindow.setResizable(false);
		
		v1 = new VEHICLE(100,400,3*Math.PI/2);
		running = false;
		try {car = ImageIO.read(new File("kocsi.png"));
		} catch (IOException e){}
		
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
    		v2.CalcNewPos(tau, map, drive, steer);
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
			g2d.setColor(new Color(0, 255, 0));
			g2d.fillOval((int)(gamewindow.getWidth()/2-0.5*d+1),gamewindow.getHeight()/2-d/2+1,d-2,d-2);
			g2d.fillOval((int)(gamewindow.getWidth()/2-1.5*d+1),gamewindow.getHeight()/2-d/2+1,d-2,d-2);
			g2d.fillOval((int)(gamewindow.getWidth()/2+0.5*d+1),gamewindow.getHeight()/2-d/2+1,d-2,d-2);
			oldXForm1 = g2d.getTransform();
			paintGAMEPLAY(g2d);
			g2d.setTransform(oldXForm1);
			g2d.setColor(new Color(0, 0, 0));
			String time = String.format("%02d" + ":" + "%02d" + ":" + "%02d", tellmin, tellsec, tellmil/10);
			g2d.drawString(time, 10, 30);
			break;

		default:
			if (playing) { 
				AffineTransform oldXForm = g2d.getTransform();
				paintGAMEPLAY(g2d);
				
				g2d.setTransform(oldXForm); // Restore transform
				if (tellsec > 2 && tellsec < 5) {
					DrawMap(g2d,gamewindow.getWidth()/2-2*d+5, gamewindow.getHeight()/2-d+5, 2*d, 4*d);
				}
				g2d.setColor(new Color(0, 0, 0));
				time = String.format("%02d" + ":" + "%02d" + ":" + "%02d", tellmin, tellsec, tellmil/10);
				g2d.drawString(time, 10, 30);
			}
		}
	}
	
	public void paintGAMEPLAY(Graphics2D g2d) {
		
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
			DrawMap(g2d,LeftCornerX-6,LeftCornerY-6,(int)CarHeight+14,(int)CarWidth+14);
		}
		else {
			DrawMap(g2d,1,1,map.GetHeight(), map.GetWidth());
			running = true;
		}
		DrawMap(g2d,0,0,40,150);
		
		//ha multi
		if (gameMode) {
			double CarWidth2 = (Math.abs(car.getHeight()*Math.cos(v2.Ori))+Math.abs(car.getWidth()*Math.sin(v2.Ori)));
			double CarHeight2 = (Math.abs(car.getHeight()*Math.sin(v2.Ori))+Math.abs(car.getWidth()*Math.cos(v2.Ori)));
			int CornerX2 = (int)(v2.PosX-(car.getHeight()*Math.cos(v2.Ori)+car.getWidth()*Math.sin(v2.Ori))/2);
			int CornerY2 = (int)(v2.PosY-(car.getHeight()*Math.sin(v2.Ori)-car.getWidth()*Math.cos(v2.Ori))/2);
			
			double Ori2 = v1.Ori - v1.AngVel * v1.dt;
			int LeftCornerX2 = (int)(v1.PosX-CarWidth2/2-v1.Vel*Math.cos(Ori2)*v1.dt);
			int LeftCornerY2 = (int)(v1.PosY-CarHeight2/2-v1.Vel*Math.sin(Ori2)*v1.dt);

			DrawMap(g2d,LeftCornerX2-6,LeftCornerY2-6,(int)CarHeight2+14,(int)CarWidth2+14);
			g2d.rotate(v2.Ori-Math.PI/2, CornerX2, CornerY2);
		    g2d.drawImage(car, CornerX2, CornerY2, this);
		}
		
	    
		//autó kirajzolása	
			g2d.rotate(v1.Ori-Math.PI/2, CornerX, CornerY);
			g2d.drawImage(car, CornerX, CornerY, this);

	}
	
	public void windowActivated(WindowEvent arg0) {}  
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
	
	public void StartServer() {
		
	}
	
	public void StartClient() {
		
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
	    }
	} 
	
}
