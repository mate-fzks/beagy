import java.io.Serializable;

public class VEHICLE implements Serializable{

	public double Ori;
	public double Vel;
	public double AngVel;
	public double dt;
	public double PosX;
	public double PosY;
	
	private static double MinRadius = 60; //maximum kanyarod�si sz�gsebess�ge az aut�nak
	
	public VEHICLE(double x, double y, double Ori) {
		PosX = x;
		PosY = y;
		this.Ori = Ori;
		Vel = 0;
		AngVel = 0;
	}
	
	public void CalcNewPos(double tau, MAP map, double drive, int steer) {
		dt = tau/1000;	//id�l�p�s
		int GridX;
		int GridY;
		double DriveForce = 500*drive; //max sebess�g �ll�t�sa
		if (Vel < 0) DriveForce = DriveForce/3; 
		int mass = 2; //gyorsul�s �ll�t�s�ra
		if (PosX <= 0) {
			GridX = 0;
		}
		else if (PosX > map.TrackDraw.length-1) {
			GridX = map.TrackDraw.length-1;
		}
		else {
			GridX = (int)PosX;
		}
		if (PosY <= 0) {
			GridY = 0;
		}
		else if (PosY > map.TrackDraw[0].length-1) {
			GridY = map.TrackDraw[0].length-1;
		}
		else {
			GridY = (int)PosY;
		}
		double Fric = map.TrackDraw[GridX][GridY].getFric();	//p�lyaelem s�rl�d�sa
		double Grip = map.TrackDraw[GridX][GridY].getGrip();	//p�lyaelem tapad�sa
		
		double MaxAngVel = Math.abs(Vel)/VEHICLE.MinRadius;
		//sz�gsebess�g sz�m�t�sa
		if (Vel == 0) 	AngVel = 0;
		else 			AngVel = 2*Math.signum(steer)*Grip/Vel;	//sebess�ggel ford. ar�nyos -> kics�sz�s modellez�se
		//viszont kis sebess�gekn�l ez nagyon nagy sz�gsebess�get eredm�nyezne
		if (AngVel > MaxAngVel) {
			AngVel = MaxAngVel;
		}
		else if (AngVel < -MaxAngVel) {
			AngVel = -MaxAngVel;
		}
		
		
		//�j poz�ci�k �s orient�ci�k sz�m�t�sa els�fok� k�zel�t�ssel
		PosX = PosX + Vel*Math.cos(Ori)*dt;
		PosY = PosY + Vel*Math.sin(Ori)*dt;
		Ori = Ori + AngVel * dt;
		
		if (PosX <= 0) PosX = 0;
		else if (PosX > map.TrackDraw.length-1) PosX = map.TrackDraw.length-1;
		if (PosY <= 0) PosY = 0;
		else if (PosY > map.TrackDraw[0].length-27) PosY = map.TrackDraw[0].length-27;

		
		//�j sebess�g sz�m�t�sa els�fok� k�zel�t�ssel
		double a = (DriveForce - Fric*Vel)/mass;
		double MinVel = 1;
		
		Vel = Vel + a*dt;
		if (Math.abs(Vel) < MinVel) Vel = 0; 	//Nagyon kicsi sebess�gek eset�n a sebess�g legyen nulla		
		
	}
}
