
public class MAP {

	private int Widt;
	private int Hegt;
	private MATERIAL[][] Track;
	public MATERIAL[][] TrackDraw;
	
	public MAP(int width, int height, MATERIAL mat) {
		Widt = width;
		Hegt = height;
		TrackDraw = new MATERIAL[Widt][Hegt];
		for (int i=0;i<Widt;i++) {
			for (int j=0;j<Hegt;j++) {
				int kpx1 = 300;
				int kpy1 = 400;
				int kpx2 = 900;
				int kpy2 = 400;
				
				if (Math.sqrt((i-kpx1)*(i-kpx1)+(j-kpy1)*(j-kpy1)) > 100 && Math.sqrt((i-kpx1)*(i-kpx1)+(j-kpy1)*(j-kpy1)) < 200 && i < kpx1){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) > 100 && Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) < 200 && i > kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					TrackDraw[i][j] = mat2;
				}
				else if (Math.abs(j-kpy1) >= 100 && Math.abs(j-kpy1) <= 200  && i >= kpx1 && i <= kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) > 100 && Math.sqrt((i-kpx2)*(i-kpx2)+(j-kpy2)*(j-kpy2)) < 200 && i >= kpx2){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					TrackDraw[i][j] = mat2;
				}
				else if (Math.sqrt((i-50)*(i-50)+(j-50)*(j-50)) < 20){
					MATERIAL mat2 = new MATERIAL("TARMAC");
					TrackDraw[i][j] = mat2;
				}
				else {
				TrackDraw[i][j] = new MATERIAL(mat);
				}
			}
		}
		Blur();
		
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
