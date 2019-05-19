
public class PLANNER {
	
	public int LasPosX;
	public int LasPosY;
	
	public PLANNER(PLANNER plan) {
		LasPosX=plan.LasPosX;
		LasPosY=plan.LasPosY;
	}
	
	public PLANNER() {
		LasPosX=0;
		LasPosY=0;
	}

	public MAP DefaultMap() {
		int Widt = 1200;
		int Hegt = 700;
		MATERIAL mat = new MATERIAL("GRASS");
		
		MAP map=new MAP(Widt, Hegt, mat);
		
		for (int i=0;i<Widt;i++) {
			for (int j=0;j<Hegt;j++) {
				int kpx1 = 310;
				int kpy1 = 350;
				int kpx2 = 880;
				int kpy2 = 350;
				
				if (Math.sqrt((i-kpx1)*(i-kpx1)+(j-kpy1)*(j-kpy1)) > 150 && Math.sqrt((i-kpx1)*(i-kpx1)+(j-kpy1)*(j-kpy1)) < 300 && i < kpx1){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.abs(j-kpy1) >= 150 && Math.abs(j-kpy1) <= 300  && i >= kpx1 && i <= kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) > 150 && Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) < 300 && i >= kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
		
			}
		}
		map.Blur();
		
		
		return map;
	}
	
	public MAP CreateMap() {
		int Widt = 1200;
		int Hegt = 700;
		MATERIAL mat = new MATERIAL("GRASS");
		
		MAP map = new MAP(Widt, Hegt, mat);
		this.LasPosY=Hegt/2;
		this.LasPosX=100;
		map.Blur();
		
		
		return map;
	}
	
	
	public MAP CreateStraight(MAP map, int ang, int track_length, int track_width, MATERIAL track_surface) {
		//MAP tempmap=new MAP(map);
		
		if((this.LasPosX+track_length*Math.cos(ang*Math.PI/180))>map.GetWidth()) return map;
		if((this.LasPosY+track_length*Math.sin(ang*Math.PI/180))>map.GetHeight()) return map;
		
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				
				//check if the given coordinate is within the ractangle
				
					if((i > this.LasPosX) && (i < this.LasPosX+track_length) && (j > (this.LasPosY-track_width/2)) && (j < (this.LasPosY+track_width/2))) {
					
					//rotating the rectangle
					
					map.TrackDraw[(int)(Math.cos(ang*Math.PI/180)*(i-this.LasPosX)-Math.sin(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosX)]
							     [(int)(Math.sin(ang*Math.PI/180)*(i-this.LasPosX)+Math.cos(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosY)]=track_surface;
				}
				
			}
		}
		this.LasPosX=(int)(this.LasPosX+track_length*Math.cos(ang*Math.PI/180));
		this.LasPosY=(int)(this.LasPosY+track_length*Math.sin(ang*Math.PI/180));
		
			
		return map;
	}
	
	public MAP CreateCircle(MAP map,int ori, int ang, int track_radius,  int track_width, MATERIAL track_surface) {
		//ang +-180
		int xpos, ypos, xc, yc;
		if(ang>180) ang=180;
		if(ang<-180) ang=-180;
		if(ori<0) {
			ori=-ori;
			ang=-ang;
		}
		
		xpos=(int)(this.LasPosX+track_radius*Math.sin(Math.abs(ang)*Math.PI/180));
		if(Math.abs(ang)<=90) {
			ypos=(int)(this.LasPosY-Math.abs(ang)/ang*1*track_radius*(1-Math.cos(ang*Math.PI/180)));
		}
		else {
			ypos=(int)(this.LasPosY-Math.abs(ang)/ang*track_radius*(1+Math.sin((Math.abs(ang)-90)*Math.PI/180)));
		}

		xc=this.LasPosX;
		yc=this.LasPosY;
		
				
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				if ((Math.sqrt((i-this.LasPosX)*(i-this.LasPosX)+(j-this.LasPosY)*(j-this.LasPosY)) > track_radius-track_width/2) 
				 && (Math.sqrt((i-this.LasPosX)*(i-this.LasPosX)+(j-this.LasPosY)*(j-this.LasPosY)) < track_radius+track_width/2))
				{
					if (ang>0) {
						if(((Math.atan((i-this.LasPosX)/(j-this.LasPosY+0.001))+Math.PI/2) < Math.abs(ang)*Math.PI/180) && ((j-this.LasPosY)<0)) {
						//	map.TrackDraw[i-track_radius][j] = track_surface;
							map.TrackDraw[(int)(Math.cos(ori*Math.PI/180)*(i-this.LasPosX)-Math.sin(ori*Math.PI/180)*(j-this.LasPosY)+this.LasPosX-track_radius*Math.cos(ori*Math.PI/180))]
									     [(int)((Math.sin(ori*Math.PI/180)*(i-this.LasPosX)+Math.cos(ori*Math.PI/180)*(j-this.LasPosY)+this.LasPosY)-track_radius*Math.sin(ori*Math.PI/180))]=track_surface;

						}
					}
					else {
						if(((Math.atan((i-this.LasPosX)/(j-this.LasPosY+0.001))+Math.PI/2) > (180-Math.abs(ang))*Math.PI/180) && ((j-this.LasPosY)<0)) {
						//	map.TrackDraw[i+track_radius][j] = track_surface;
							map.TrackDraw[(int)(Math.cos(ori*Math.PI/180)*(i+track_radius-this.LasPosX)-Math.sin(ori*Math.PI/180)*(j-this.LasPosY)+this.LasPosX)]
										 [(int)(Math.sin(ori*Math.PI/180)*(i+track_radius-this.LasPosX)+Math.cos(ori*Math.PI/180)*(j-this.LasPosY)+this.LasPosY)]=track_surface;

						}
					}
					
				}
		
			}
		}
		xpos=(int)(this.LasPosX+track_radius*Math.sin(Math.abs(ang)*Math.PI/180));
		if(Math.abs(ang)<=90) {
			ypos=(int)(this.LasPosY-Math.abs(ang)/ang*1*track_radius*(1-Math.cos(ang*Math.PI/180)));
		}
		else {
			ypos=(int)(this.LasPosY-Math.abs(ang)/ang*track_radius*(1+Math.sin((Math.abs(ang)-90)*Math.PI/180)));
		}

		xc=this.LasPosX;
		yc=this.LasPosY;
		
		this.LasPosX=(int)(Math.cos((ori-90)*Math.PI/180) * (xpos - xc) - Math.sin((ori-90)*Math.PI/180) * (ypos - yc) + xc);
		this.LasPosY=(int)(Math.sin((ori-90)*Math.PI/180) * (xpos - xc) + Math.cos((ori-90)*Math.PI/180) * (ypos - yc) + yc);
		
		return map;
	}

	
	public boolean LoopClosureCheck(MAP map) {
		boolean closeloop=true;
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
	
			if((((i-100)/(this.LasPosX-100+0.005))<((j-700/2)/(this.LasPosY-700/2+0.01)+0.01)) && ((i-100)/(this.LasPosX-100+0.01)>((j-700/2)/(this.LasPosY-700/2+0.01)-0.005)) && (j>Math.min(this.LasPosY+5,700/2+5)) && (j<Math.max(this.LasPosY-5,700/2-5)) && (i>Math.min(this.LasPosX+5, 100+5)) && (i<Math.max(this.LasPosX-5, 100-5)))
				if(map.TrackDraw[i][j]!=map.mat) closeloop=false;
			
			}
		}
		
		if(this.LasPosX==100 && this.LasPosY==350) closeloop=true;
		
		return closeloop;

	}
		
	public MAP DisplayMap (MAP trac, MAP bac) {
		MAP background = new MAP(bac);
		MAP track = new MAP(trac);
		
		for(int k=1; k<100; k++) {
			for (int i=1; i<background.GetWidth(); i++) {
				for (int j=1; j<background.GetHeight(); j++) {
					if(background.TrackDraw[i][j]!=background.mat) {
						background.TrackDraw[i-1][j-1]=background.TrackDraw[i][j];
						background.TrackDraw[i-1][j]=background.TrackDraw[i][j];
						background.TrackDraw[i][j-1]=background.TrackDraw[i][j];
					}
				}
			}
		}
		
		for(int k=1; k<50; k++) {
			for (int i=background.GetWidth()-2; i>1; i--) {
				for (int j=background.GetHeight()-2; j>1; j--) {
					if(background.TrackDraw[i][j]!=background.mat) {
						background.TrackDraw[i+1][j+1]=background.TrackDraw[i][j];
						background.TrackDraw[i+1][j]=background.TrackDraw[i][j];
						background.TrackDraw[i][j+1]=background.TrackDraw[i][j];
					}
				}
			}
		}
		
		for(int k=1; k<70; k++) {
			for (int i=1; i<track.GetWidth(); i++) {
				for (int j=1; j<track.GetHeight(); j++) {
					if(track.TrackDraw[i][j]!=track.mat) {
						track.TrackDraw[i-1][j-1]=track.TrackDraw[i][j];
						track.TrackDraw[i-1][j]=track.TrackDraw[i][j];
						track.TrackDraw[i][j-1]=track.TrackDraw[i][j];
					}
				}
			}
		}
		
	     for (int i=0;i<background.GetWidth();i++) {
	        	for(int j=0;j<background.GetHeight();j++) {
	        		if(track.TrackDraw[i][j].getGrip()==300) {
	        			background.TrackDraw[i][j]=new MATERIAL("TARMAC");
	        		}
	        	}
	        }
	        
		return background;
	}
	
	public MAP CreateBackground (MAP map, MATERIAL mat) {
		MAP background=new MAP(map);
		MATERIAL close = new MATERIAL(mat);

		for (int i=0; i<background.GetWidth(); i++) {
			for (int j=0; j<background.GetHeight(); j++) {
				if((((i-100)/(this.LasPosX-100+0.005))<((j-700/2)/(this.LasPosY-700/2+0.01)+0.01)) && ((i-100)/(this.LasPosX-100+0.01)>((j-700/2)/(this.LasPosY-700/2+0.01)-0.005)) && (j>Math.min(this.LasPosY,700/2)) && (j<Math.max(this.LasPosY,700/2)) && (i>Math.min(this.LasPosX, 100)) && (i<Math.max(this.LasPosX, 100))) {
					background.TrackDraw[i][j]=close;
				}
			}
		}
		int Y=0;
		if(this.LasPosX==100) {	
			if(this.LasPosY>350) {
				Y=this.LasPosY;
				for (int i=Y; i>350; i--) {

					background.TrackDraw[98][i]=close;
					background.TrackDraw[99][i]=close;
					background.TrackDraw[100][i]=close;
					background.TrackDraw[101][i]=close;
					background.TrackDraw[102][i]=close;
					
				}
			}
			else {
				Y=this.LasPosY;
				for (int i=Y; i<350; i++) {
					background.TrackDraw[98][i]=close;
						background.TrackDraw[99][i]=close;
						background.TrackDraw[100][i]=close;
						background.TrackDraw[101][i]=close;
						background.TrackDraw[102][i]=close;
				}
			}
		}
		
		for(int k=1; k<100; k++) {
			for (int i=1; i<background.GetWidth(); i++) {
				for (int j=1; j<background.GetHeight(); j++) {
					if(background.TrackDraw[i][j]!=background.mat) {
						background.TrackDraw[i-1][j-1]=background.TrackDraw[i][j];
						background.TrackDraw[i-1][j]=background.TrackDraw[i][j];
						background.TrackDraw[i][j-1]=background.TrackDraw[i][j];
					}
				}
			}
		}
		
		for(int k=1; k<50; k++) {
			for (int i=background.GetWidth()-2; i>1; i--) {
				for (int j=background.GetHeight()-2; j>1; j--) {
					if(background.TrackDraw[i][j]!=background.mat) {
						background.TrackDraw[i+1][j+1]=background.TrackDraw[i][j];
						background.TrackDraw[i+1][j]=background.TrackDraw[i][j];
						background.TrackDraw[i][j+1]=background.TrackDraw[i][j];
					}
				}
			}
		}
		
		return background;
	}
	
	public MAP LoopClosure(MAP map) {

		MATERIAL close = new MATERIAL("TARMAC");
		
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				if((((i-100)/(this.LasPosX-100+0.005))<((j-700/2)/(this.LasPosY-700/2+0.01)+0.01)) && ((i-100)/(this.LasPosX-100+0.01)>((j-700/2)/(this.LasPosY-700/2+0.01)-0.005)) && (j>Math.min(this.LasPosY,700/2)) && (j<Math.max(this.LasPosY,700/2)) && (i>Math.min(this.LasPosX, 100)) && (i<Math.max(this.LasPosX, 100))) {
					map.TrackDraw[i][j]=close;
				}
			}
		}
		int Y=0;
		
		
		if(this.LasPosX==100) {	
			if(this.LasPosY>350) {
				Y=this.LasPosY;
				for (int i=Y; i>350; i--) {

					map.TrackDraw[98][i]=close;
					map.TrackDraw[99][i]=close;
					map.TrackDraw[100][i]=close;
					map.TrackDraw[101][i]=close;
					map.TrackDraw[102][i]=close;
					
				}
			}
			else {
				Y=this.LasPosY;
				for (int i=Y; i<350; i++) {
						map.TrackDraw[98][i]=close;
						map.TrackDraw[99][i]=close;
						map.TrackDraw[100][i]=close;
						map.TrackDraw[101][i]=close;
						map.TrackDraw[102][i]=close;
				}
			}
		}
		
		
		for(int k=1; k<70; k++) {
			for (int i=1; i<map.GetWidth(); i++) {
				for (int j=1; j<map.GetHeight(); j++) {
					if(map.TrackDraw[i][j]!=map.mat) {
						map.TrackDraw[i-1][j-1]=map.TrackDraw[i][j];
						map.TrackDraw[i-1][j]=map.TrackDraw[i][j];
						map.TrackDraw[i][j-1]=map.TrackDraw[i][j];
					}
				}
			}
		}
		
		return map;
	}


}
