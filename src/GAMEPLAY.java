import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GAMEPLAY extends JPanel{

	private GUI gui;
	public MAP map;
	private boolean gameMode;
	private VEHICLE v1;
	private VEHICLE v2;
	
	public boolean running;
	public int steer;
	public int drive;
	
	
	public GAMEPLAY(MAP map,boolean multi_player, GUI gui) {
		this.map = map;
		this.gameMode = multi_player;
		this.gui = gui;
		
	}
	
	public void refresh(int sleep) {

	    v1.CalcNewPos(sleep, map, drive, steer);
    	if (gameMode) {
    		v2.CalcNewPos(sleep, map, drive, steer);
    	}
	}
	
	public void init() {
		v1 = new VEHICLE(100,400,3*Math.PI/2);
		running = false;
	}
	
	public void paint(Graphics2D g2d) {
		
		
		//autó sarkai
		BufferedImage car = null;
		try
	    {
			car = ImageIO.read(new File("kocsi.png"));
	    } 
	    catch (IOException e)
	    {
	    }
		double CarWidth = (Math.abs(car.getHeight()*Math.cos(v1.Ori))+Math.abs(car.getWidth()*Math.sin(v1.Ori)));
		double CarHeight = (Math.abs(car.getHeight()*Math.sin(v1.Ori))+Math.abs(car.getWidth()*Math.cos(v1.Ori)));
		int CornerX = (int)(v1.PosX-(car.getHeight()*Math.cos(v1.Ori)+car.getWidth()*Math.sin(v1.Ori))/2);
		int CornerY = (int)(v1.PosY-(car.getHeight()*Math.sin(v1.Ori)-car.getWidth()*Math.cos(v1.Ori))/2);
		
		double Ori = v1.Ori - v1.AngVel * v1.dt;
		int LeftCornerX = (int)(v1.PosX-CarWidth/2-v1.Vel*Math.cos(Ori)*v1.dt);
		int LeftCornerY = (int)(v1.PosY-CarHeight/2-v1.Vel*Math.sin(Ori)*v1.dt);
		

		//pálya rajzolása
		if (running) {
			DrawMap(g2d,LeftCornerX-3,LeftCornerY-3,(int)CarHeight+7,(int)CarWidth+7);
		}
		else {
			DrawMap(g2d,1,1,map.GetHeight(), map.GetWidth());
			running = true;
		}
		
		//ha multi
		if (gameMode) {
			double CarWidth2 = (Math.abs(car.getHeight()*Math.cos(v2.Ori))+Math.abs(car.getWidth()*Math.sin(v2.Ori)));
			double CarHeight2 = (Math.abs(car.getHeight()*Math.sin(v2.Ori))+Math.abs(car.getWidth()*Math.cos(v2.Ori)));
			int CornerX2 = (int)(v2.PosX-(car.getHeight()*Math.cos(v2.Ori)+car.getWidth()*Math.sin(v2.Ori))/2);
			int CornerY2 = (int)(v2.PosY-(car.getHeight()*Math.sin(v2.Ori)-car.getWidth()*Math.cos(v2.Ori))/2);
			
			double Ori2 = v1.Ori - v1.AngVel * v1.dt;
			int LeftCornerX2 = (int)(v1.PosX-CarWidth2/2-v1.Vel*Math.cos(Ori2)*v1.dt);
			int LeftCornerY2 = (int)(v1.PosY-CarHeight2/2-v1.Vel*Math.sin(Ori2)*v1.dt);

			DrawMap(g2d,CornerX2-3,CornerY2-3,(int)CarHeight2+7,(int)CarWidth2+7);
			g2d.rotate(v2.Ori-Math.PI/2, CornerX2, CornerY2);
		    g2d.drawImage(car, CornerX2, CornerY2, this);
		}
		
	    
		//autó kirajzolása	
			g2d.rotate(v1.Ori-Math.PI/2, CornerX, CornerY);
			g2d.drawImage(car, CornerX, CornerY, this);

	}
	
    
	public void DrawMap(Graphics2D g2d, int startX, int startY, int height, int width) {
		startX = Math.max(startX-1,0);
		startY = Math.max(startY-1,0);
		int endX = Math.min(startX+width-1,gui.getWidth());
		int endY = Math.min(startY+height-1,gui.getHeight());
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
	
	public void keyReleased(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_LEFT) ||  (e.getKeyCode() == KeyEvent.VK_RIGHT))
			steer = 0;

		if ((e.getKeyCode() == KeyEvent.VK_UP) ||  (e.getKeyCode() == KeyEvent.VK_DOWN))
			drive = 0;
	}
	
	public void StartServer() {
		
	}
	
	public void StartClient() {
		
	}
	
	public void SetGUI(GUI gui) {
		
	}
}
