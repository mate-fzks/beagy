
public class VEHICLE extends GRID {

	public double Ori;
	private double Vel;
	private double AngVel;
	
	private static double MaxAngVel = 1; //maximum kanyarodási szögsebessége az autónak
	
	public VEHICLE(double x, double y, double Ori) {
		PosX = x;
		PosY = y;
		this.Ori = Ori;
	}
	
	public void CalcNewPos(double tau, MAP map, double drive, int steer) {
		double dt = tau/1000;												//idõlépés
		double Fric = map.TrackDraw[(int)PosX][(int)PosY].getFric();	//pályaelem súrlódása
		double Grip = map.TrackDraw[(int)PosX][(int)PosY].getGrip();	//pályaelem tapadása
		
		
		//szögsebesség számítása
		if (Vel == 0) 	AngVel = 0;
		else 			AngVel = Math.signum(steer)*Grip/Vel;	//sebességgel ford. arányos -> kicsúszás modellezése
		//viszont kis sebességeknél ez nagyon nagy szögsebességet eredményezne
		if (AngVel > VEHICLE.MaxAngVel) {
			AngVel = VEHICLE.MaxAngVel;
		}
		else if (AngVel < -VEHICLE.MaxAngVel) {
			AngVel = -VEHICLE.MaxAngVel;
		}
		
		
		//új pozíciók és orientációk számítása elsõfokú közelítéssel
		PosX = PosX + Vel*Math.cos(Ori)*dt;
		PosY = PosY + Vel*Math.sin(Ori)*dt;
		Ori = Ori + AngVel * dt;
		
		
		//új sebesség számítása elsõfokú közelítéssel
		double a = drive - Fric*Vel;
		double MinVel = 0.01;
		
		Vel = Vel + a*dt;
		if (Math.abs(Vel) < MinVel) Vel = 0; 	//Nagyon kicsi sebességek esetén a sebesség legyen nulla		
		
	}
}
