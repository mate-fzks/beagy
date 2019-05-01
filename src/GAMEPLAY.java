import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GAMEPLAY extends JPanel{

	private GUI gui;
	public MAP map;
	private boolean gameMode;
	private VEHICLE v1;
	private VEHICLE v2;
	
	public int steer;
	public int drive;
	
	
	public GAMEPLAY(MAP map,boolean multi_player) {
		this.map = map;
		this.gameMode = multi_player;
		
	}
	
	public void StartGameplay() {
		//int drive;
		//int steer;
		v1 = new VEHICLE(200,400,Math.PI/2);
		
		
	    JFrame frame= new JFrame("Gran Turismo Forza X");
	    GAMEPLAY game = new GAMEPLAY(map, false);
	    
	    game.map = map;
	    game.v1 = v1;
	    
	    //JPanel panel = new JPanel();
	   
	    frame.add(game);
	    frame.setSize(1200, 700);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    	
	    		
	    drive = 250;
	    steer = -1;
	    while (true)
	      {
	    	v1.CalcNewPos(10, map, drive, steer);

	        game.repaint();
	        try{
	             Thread.sleep(10);
	        } catch (Exception exc){}
	        
	      } 
	}
	
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//pálya kirajzolása
		int x0 = 0;
		int x1 = 0;
		

		for (int y=0;y<map.GetHeight();y++) {
			while (x0 < map.GetWidth()) {
				int[] color = this.map.TrackDraw[x0][y].getCol();
				g2d.setColor(new Color(color[0], color[1], color[2]));
				
				if (x1+1 < map.GetWidth()) {
					while (this.map.TrackDraw[x1+1][y] == this.map.TrackDraw[x0][y] && x1+2 < map.GetWidth()) {
						x1++;
					}
				}

				g2d.drawLine(x0, y, x1, y);
				x1++;
				x0 = x1;
			}
			x0 = 0;
			x1 = 0;
		}
		
		g2d.setColor(new Color(50, 50, 50));
		g2d.fillOval(80, 30, 40, 40);
		g2d.drawLine(100,100,200,200);
		
		//súlypont helyét jelölõ vonalak
		g2d.setColor(new Color(0, 0, 0));
	    g2d.drawLine((int)v1.PosX, 0, (int)v1.PosX, 700);
	    g2d.drawLine(0, (int)v1.PosY, 1200, (int)v1.PosY);
	    
	    
		//autó kirajzolása
		Image car = Toolkit.getDefaultToolkit().getImage("kocsi.png");
		int CornerX = (int)(v1.PosX-car.getHeight(this)*Math.cos(v1.Ori)/2-car.getWidth(this)*Math.sin(v1.Ori)/2);
		int CornerY = (int)(v1.PosY-car.getHeight(this)*Math.sin(v1.Ori)/2+car.getWidth(this)*Math.cos(v1.Ori)/2);
	    g2d.rotate(v1.Ori-Math.PI/2, CornerX, CornerY);
	    g2d.drawImage(car, CornerX, CornerY, this);
	    

	}
	
    
	public void ReadKey() {
		
	}
	
	public void StartServer() {
		
	}
	
	public void StartClient() {
		
	}
	
	public void SetGUI(GUI gui) {
		
	}
}
