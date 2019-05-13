
public class PLANNER {
	
	public int LasPosX;
	public int LasPosY;
	
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
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				
				//check if the given coordinate is within the ractangle
				
			/*
				if((i > this.LasPosX) && (i < this.LasPosX+track_length) && (j > (this.LasPosY+track_width*9.5)) && (j < (this.LasPosY+track_width*10))) {
					
					//rotating the rectangle
					
					map.TrackDraw[(int)(Math.cos(ang*Math.PI/180)*(i-this.LasPosX)-Math.sin(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosX)]
							     [(int)(Math.sin(ang*Math.PI/180)*(i-this.LasPosX)+Math.cos(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosY)]=track_side;
				}
				
				
				if((i > this.LasPosX) && (i < this.LasPosX+track_length) && (j < (this.LasPosY-track_width*9.5)) && (j > (this.LasPosY-track_width*10))) {
					
					//rotating the rectangle
					
					map.TrackDraw[(int)(Math.cos(ang*Math.PI/180)*(i-this.LasPosX)-Math.sin(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosX)]
							     [(int)(Math.sin(ang*Math.PI/180)*(i-this.LasPosX)+Math.cos(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosY)]=track_side;
				}
			*/
				if((i > this.LasPosX) && (i < this.LasPosX+track_length) && (j > (this.LasPosY-track_width/2)) && (j < (this.LasPosY+track_width/2))) {
					
					//rotating the rectangle
					
					map.TrackDraw[(int)(Math.cos(ang*Math.PI/180)*(i-this.LasPosX)-Math.sin(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosX)]
							     [(int)(Math.sin(ang*Math.PI/180)*(i-this.LasPosX)+Math.cos(ang*Math.PI/180)*(j-this.LasPosY)+this.LasPosY)]=track_surface;
				}
				
			}
		}
		this.LasPosX=(int)(this.LasPosX+track_length*Math.cos(ang*Math.PI/180));
		this.LasPosY=(int)(this.LasPosY+track_length*Math.sin(ang*Math.PI/180));
		
				
		//erosion for missing pixels due to rotation's numerical error caused by implicit conversion
		/*
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				if(map.TrackDraw[i][j]==track_surface) {
					map.TrackDraw[i-1][j-1]=track_surface;
					map.TrackDraw[i-1][j]=track_surface;
					map.TrackDraw[i][j-1]=track_surface;
				}
			}
		}
		*/
		//map.Blur();
		return map;
	}
	
	public MAP CreateCircle(MAP map,int ori, int ang, int track_radius,  int track_width, MATERIAL track_surface) {
		//ang +-180
		int xpos, ypos, xc, yc;
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
		


		/*
		for (int i=0; i<map.GetWidth(); i++) {
			for (int j=0; j<map.GetHeight(); j++) {
				if(map.TrackDraw[i][j]==track_surface) {
					map.TrackDraw[i-1][j-1]=track_surface;
					map.TrackDraw[i-1][j]=track_surface;
					map.TrackDraw[i][j-1]=track_surface;
				}
			}
		}*/
		
		//this.map.TrackDraw[i][j] =;
		return map;
	}

	
	public boolean LoopClosureCheck(MAP map) {
		boolean closeloop=true;

		for (int i=1; i<map.GetWidth(); i++) {
			for (int j=1; j<map.GetHeight(); j++) {
				if((((i-100)/(this.LasPosX-100+0.005))<((j-700/2)/(this.LasPosY-700/2+0.01)+0.01)) && ((i-100)/(this.LasPosX-100+0.01)>((j-700/2)/(this.LasPosY-700/2+0.01)-0.005)) && (j>Math.min(this.LasPosY,700/2)) && (j<Math.max(this.LasPosY,700/2)) && (i>Math.min(this.LasPosX, 100)) && (i<Math.max(this.LasPosX, 100))) {
					if(map.TrackDraw[i][j]!=map.mat) closeloop=false;
				}
			}
		}
		return closeloop;

	}
	
	public MAP LoopClosure(MAP map) {
		boolean closeloop=true;
		MATERIAL close = new MATERIAL("TARMAC");
		/*
		for (int i=1; i<map.GetWidth(); i++) {
			for (int j=1; j<map.GetHeight(); j++) {
				if((((i-100)/(this.LasPosX-100+0.005))<((j-700/2)/(this.LasPosY-700/2+0.01)+0.01)) && ((i-100)/(this.LasPosX-100+0.01)>((j-700/2)/(this.LasPosY-700/2+0.01)-0.005)) && (j>Math.min(this.LasPosY,700/2)) && (j<Math.max(this.LasPosY,700/2)) && (i>Math.min(this.LasPosX, 100)) && (i<Math.max(this.LasPosX, 100))) {
					if(map.TrackDraw[i][j]!=map.mat) closeloop=false;
				}
			}
		}
		*/
		for (int i=1; i<map.GetWidth(); i++) {
			for (int j=1; j<map.GetHeight(); j++) {
				if((((i-100)/(this.LasPosX-100+0.005))<((j-700/2)/(this.LasPosY-700/2+0.01)+0.01)) && ((i-100)/(this.LasPosX-100+0.01)>((j-700/2)/(this.LasPosY-700/2+0.01)-0.005)) && (j>Math.min(this.LasPosY,700/2)) && (j<Math.max(this.LasPosY,700/2)) && (i>Math.min(this.LasPosX, 100)) && (i<Math.max(this.LasPosX, 100)) && closeloop) {
					map.TrackDraw[i][j]=close;
				}
			}
		}
		
		for(int k=0; k<50; k++) {
			for (int i=1; i<map.GetWidth(); i++) {
				for (int j=1; j<map.GetHeight(); j++) {
					if((map.TrackDraw[i][j]!=map.mat) && closeloop) {
						map.TrackDraw[i-1][j-1]=map.TrackDraw[i][j];
						map.TrackDraw[i-1][j]=map.TrackDraw[i][j];
						map.TrackDraw[i][j-1]=map.TrackDraw[i][j];
					}
				}
			}
		}
		/*
		for(int k=0; k<50; k++) {
			for (int i=0; i<map.GetWidth(); i++) {
				for (int j=0; j<map.GetHeight(); j++) {
					if(map.TrackDraw[i][j]!=map.mat) {
						map.TrackDraw[i-1][j-1]=map.TrackDraw[i][j];
						map.TrackDraw[i-1][j]=map.TrackDraw[i][j];
						map.TrackDraw[i][j-1]=map.TrackDraw[i][j];
					}
				}
			}
		}/*
		for(int k=0; k<50; k++) {
			for (int i=1; i<map.GetWidth(); i++) {
				for (int j=1; j<map.GetHeight(); j++) {
					if(map.TrackDraw[i][j]==map.mat) {
						map.TrackDraw[i-1][j-1]=map.TrackDraw[i][j];
						map.TrackDraw[i-1][j]=map.TrackDraw[i][j];
						map.TrackDraw[i][j-1]=map.TrackDraw[i][j];
					}
				}
			}
		}*/
		return map;
	}


}
