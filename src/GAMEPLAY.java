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
	
	private boolean running;
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
	    
	    running = true;
	    int sleep = 1;
	    while (true)
	      { 
	    	v1.CalcNewPos(sleep, map, drive, steer);
	    	if (gameMode) {
	    		v2.CalcNewPos(sleep, map, drive, steer);
	    	}
	        game.repaint();
	        try{
	             Thread.sleep(sleep);
	        } catch (Exception exc){}
	        
	      } 
	}
	
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		//autó sarkai
		Image car = Toolkit.getDefaultToolkit().getImage("kocsi.png");
		double CarWidth = (car.getHeight(this)*Math.cos(v1.Ori)+car.getWidth(this)*Math.sin(v1.Ori));
		double CarHeight = (car.getHeight(this)*Math.sin(v1.Ori)-car.getWidth(this)*Math.cos(v1.Ori));
		int CornerX = (int)(v1.PosX-CarWidth/2);
		int CornerY = (int)(v1.PosY-CarHeight/2);
		
		

		//pálya rajzolása
		if (running) {
			DrawMap(g2d,CornerX,CornerY,(int)CarHeight,(int)CarWidth);
		}
		else {
			DrawMap(g2d,1,1,map.GetHeight(), map.GetWidth());
		}
		
		//ha multi
		if (gameMode) {
			double CarWidth2 = (car.getHeight(this)*Math.cos(v2.Ori)+car.getWidth(this)*Math.sin(v2.Ori));
			double CarHeight2 = (car.getHeight(this)*Math.sin(v2.Ori)-car.getWidth(this)*Math.cos(v2.Ori));
			int CornerX2 = (int)(v2.PosX-CarWidth/2);
			int CornerY2 = (int)(v2.PosY-CarHeight/2);
			DrawMap(g2d,CornerX2,CornerY2,(int)CarHeight2,(int)CarWidth2);
			g2d.rotate(v2.Ori-Math.PI/2, CornerX2, CornerY2);
		    g2d.drawImage(car, CornerX2, CornerY2, this);
		}
		
	    
		//autó kirajzolása		
	    g2d.rotate(v1.Ori-Math.PI/2, CornerX, CornerY);
	    g2d.drawImage(car, CornerX, CornerY, this);
	    

	}
	
    
	public void DrawMap(Graphics2D g2d, int startX, int startY, int height, int width) {
		int x0 = startX-1;
		int x1 = x0;
		for (int y=startY-1;y<height;y++) {
			while (x0 < width) {
				int[] color = this.map.TrackDraw[x0][y].getCol();
				g2d.setColor(new Color(color[0], color[1], color[2]));
				
				if (x1+1 < height) {
					while (this.map.TrackDraw[x1+1][y] == this.map.TrackDraw[x0][y] && x1+2 < width) {
						x1++;
					}
				}

				g2d.drawLine(x0, y, x1, y);
				x1++;
				x0 = x1;
			}
			x0 = startX-1;
			x1 = x0;
		}
	}
	
	public void StartServer() {
		
	}
	
	public void StartClient() {
		
	}
	
	public void SetGUI(GUI gui) {
		
	}
}
