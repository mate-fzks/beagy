
public class PLANNER {
	
	private GUI Gui;
	private MAP Map;
	private int LasPosX;
	private int LasPosY;
	
	public MAP DefaultMap() {
		int Widt = 1200;
		int Hegt = 700;
		MATERIAL mat = new MATERIAL("GRASS");
		
		MAP map=new MAP(Widt, Hegt, mat);
		
		for (int i=0;i<Widt;i++) {
			for (int j=0;j<Hegt;j++) {
				int kpx1 = 300;
				int kpy1 = 400;
				int kpx2 = 900;
				int kpy2 = 400;
				
				if (Math.sqrt((i-kpx1)*(i-kpx1)+(j-kpy1)*(j-kpy1)) > 100 && Math.sqrt((i-kpx1)*(i-kpx1)+(j-kpy1)*(j-kpy1)) < 200 && i < kpx1){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) > 100 && Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) < 200 && i > kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.abs(j-kpy1) >= 100 && Math.abs(j-kpy1) <= 200  && i >= kpx1 && i <= kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) > 100 && Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) < 200 && i >= kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-50)*(i-50)+(j-50)*(j-50)) < 20){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					map.TrackDraw[i][j] = mat2;
				}
				else {
					map.TrackDraw[i][j] = map.mat;
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
		
		MAP map=new MAP(Widt, Hegt, mat);
		
		map.Blur();
		
		
		return map;
	}
	
	public MAP CreateStraight(MAP map) {
		//this.map.TrackDraw[i][j] =;
		return map;
	}
	
	public MAP CreateCircle(MAP map) {
		//this.map.TrackDraw[i][j] =;
		return map;
	}

	public MAP LoopClosure(MAP map) {
		//this.map.TrackDraw[i][j] =;
		return map;
	}


}
