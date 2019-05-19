
public class MAP {

	private int Widt;
	private int Hegt;
	public MATERIAL mat;
	private MATERIAL[][] Track;
	public MATERIAL[][] TrackDraw;
	
	public MAP() {
		Widt = 1200;
		Hegt = 700;
		TrackDraw = new MATERIAL[Widt][Hegt];
		Track = new MATERIAL[Widt][Hegt];
		MATERIAL material = new MATERIAL("GRASS");
		this.mat = material;
		for(int i=0; i<Widt ; i++)
		{
			for(int j=0; j< Hegt; j++) {
				TrackDraw[i][j]=material;
			}
		}
	}
	
	public MAP(MAP map) {
		this.Widt=map.GetWidth();
		this.Hegt=map.GetHeight();
		this.mat=new MATERIAL(map.mat);
		this.TrackDraw = new MATERIAL[map.GetWidth()][map.GetHeight()];
		this.Track = new MATERIAL[map.GetWidth()][map.GetHeight()];
		for(int i=0; i<Widt ; i++)
		{
			for(int j=0; j< Hegt; j++) {
				TrackDraw[i][j]=this.mat;
			}
		}
		for(int i=0; i<Widt ; i++)
		{
			for(int j=0; j< Hegt; j++) {
					if(map.TrackDraw[i][j]!=map.mat){
						TrackDraw[i][j] = new MATERIAL(map.TrackDraw[i][j]);
					}
				}
			}
		}

	
	public MAP(int width, int height, MATERIAL mat) {
		Widt = width;
		Hegt = height;
		TrackDraw = new MATERIAL[Widt][Hegt];
		Track = new MATERIAL[Widt][Hegt];
		this.mat = mat;
		for(int i=0; i<Widt ; i++)
		{
			for(int j=0; j< Hegt; j++) {
				TrackDraw[i][j]=mat;
			}
		}
	}
	
	public void Blur() {
		int[][][] temp = new int[Widt][Hegt][3];
		for (int i=0;i<Widt;i++) {
			for (int j=0;j<Hegt;j++) {
				for (int k=0;k<3;k++)
				temp[i][j][k] = TrackDraw[i][j].getCol()[k];
			}
		}
		
		int[][] col = { {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0} };
		int[] col2 = {0, 0, 0};
		
		for (int i=1;i<Widt-1;i++) {
			for (int j=1;j<Hegt-1;j++) {
				col[0] = TrackDraw[i-1][j-1].getCol();
				col[1] = TrackDraw[i][j-1].getCol();
				col[2] = TrackDraw[i+1][j-1].getCol();
				col[3] = TrackDraw[i-1][j].getCol();
				col[4] = TrackDraw[i][j].getCol();
				col[5] = TrackDraw[i+1][j].getCol();
				col[6] = TrackDraw[i-1][j+1].getCol();
				col[7] = TrackDraw[i][j+1].getCol();
				col[8] = TrackDraw[i+1][j+1].getCol();
				
				for (int k=0;k<3;k++) {
					col2[k] = (int)((0*col[0][k]+col[1][k]+0*col[2][k]+col[3][k]+4*col[4][k]+col[5][k]+0*col[6][k]+col[7][k]+0*col[8][k])/8.0);
				}
								
				temp[i][j][0] = col2[0];
				temp[i][j][1] = col2[1];
				temp[i][j][2] = col2[2];
			}
		}
		
		for (int i=1;i<Widt;i++) {
			for (int j=1;j<Hegt;j++) {
				int[] col3 = {temp[i][j][0], temp[i][j][1], temp[i][j][2]};
				TrackDraw[i][j].SetCol(col3);
			}
		}
	}

	public int GetWidth() {
		return Widt;
	}
	
	public int GetHeight() {
		return Hegt;
	}
	
	public void SetTrack(MATERIAL m) {
		
	}
	
	public void SaveTrack() {
		
	}
}
