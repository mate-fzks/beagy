
public class MATERIAL {
	
	private double Fric;
	private double Grip;
	private int[] Col = {0, 0, 0};
	
	public MATERIAL(MATERIAL mat) {
		Fric = mat.Fric;
		Grip = mat.Grip;
		Col = new int[] {mat.Col[0], mat.Col[1], mat.Col[2]};
	}
	
	public MATERIAL(String mat) {
		this.SetMat(mat);
	}

	public void SetMat(String mat) {
		switch (mat) {
        case "TARMAC":  Fric = 1; 	Grip = 300; 	int [] tarm = {50, 50, 50}; 		Col = tarm; 	break;
        case "GRASS":  	Fric = 5; 	Grip = 10;		int [] grass = {50, 200, 50}; 		Col = grass; 	break;
        case "GRAVEL":  Fric = 3; 	Grip = 3; 		int [] grav = {150, 150, 150}; 		Col = grav; 	break;
        case "MUD":  	Fric = 4; 	Grip = 2; 		int [] mud = {150, 100, 0};			Col = mud; 		break;
        case "WATER":  	Fric = 4; 	Grip = 2; 		int [] wat = {50, 50, 200}; 		Col = wat; 		break;
        case "SNOW":  	Fric = 1; 	Grip = 1.5; 	int [] snw = {250, 250, 250}; 		Col = snw; 		break;
        case "ICE":  	Fric = 1; 	Grip = 1; 		int [] ice = {210, 210, 255}; 		Col = ice; 		break;
        default: 		Fric = 6; 	Grip = 5; 		int [] uknw = {0, 0, 0}; 			Col = uknw; 	break; 
        }
    }

	public double getFric() {
		return Fric;
	}
	public double getGrip() {
		return Grip;
	}

	public int[] getCol() {
		return Col;
	}
	
	public void SetCol(int[] Col) {
		this.Col = Col;
	}

}
