
public class VEHICLE extends GRID {

	public double Ori;
	private double Vel;
	private double AngVel;
	
	private static double MaxAngVel = 1; //maximum kanyarod�si sz�gsebess�ge az aut�nak
	
	public VEHICLE(double x, double y, double Ori) {
		PosX = x;
		PosY = y;
		this.Ori = Ori;
	}
	
	public void CalcNewPos(double tau, MAP map, double drive, int steer) {
		double dt = tau/1000;												//id�l�p�s
		double Fric = map.TrackDraw[(int)PosX][(int)PosY].getFric();	//p�lyaelem s�rl�d�sa
		double Grip = map.TrackDraw[(int)PosX][(int)PosY].getGrip();	//p�lyaelem tapad�sa
		
		
		//sz�gsebess�g sz�m�t�sa
		if (Vel == 0) 	AngVel = 0;
		else 			AngVel = Math.signum(steer)*Grip/Vel;	//sebess�ggel ford. ar�nyos -> kics�sz�s modellez�se
		//viszont kis sebess�gekn�l ez nagyon nagy sz�gsebess�get eredm�nyezne
		if (AngVel > VEHICLE.MaxAngVel) {
			AngVel = VEHICLE.MaxAngVel;
		}
		else if (AngVel < -VEHICLE.MaxAngVel) {
			AngVel = -VEHICLE.MaxAngVel;
		}
		
		
		//�j poz�ci�k �s orient�ci�k sz�m�t�sa els�fok� k�zel�t�ssel
		PosX = PosX + Vel*Math.cos(Ori)*dt;
		PosY = PosY + Vel*Math.sin(Ori)*dt;
		Ori = Ori + AngVel * dt;
		
		
		//�j sebess�g sz�m�t�sa els�fok� k�zel�t�ssel
		double a = drive - Fric*Vel;
		double MinVel = 0.01;
		
		Vel = Vel + a*dt;
		if (Math.abs(Vel) < MinVel) Vel = 0; 	//Nagyon kicsi sebess�gek eset�n a sebess�g legyen nulla		
		
	}
}
