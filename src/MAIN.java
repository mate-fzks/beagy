
public class MAIN {

	
	
	public static void main(String[] args) {
		
	//Pályatervezés start
		
		PLANNER plan = new PLANNER();
		//Click Default
		
	//	MAP act_map1 = plan.DefaultMap();
		
		//Click Create
		MAP act_map2 = plan.CreateMap();
		MATERIAL snow = new MATERIAL("SNOW");
		MATERIAL tarmac = new MATERIAL("TARMAC");

		MATERIAL MUD = new MATERIAL("MUD");
		
		act_map2 = plan.CreateStraight(act_map2,45,100,50,snow);
		act_map2 = plan.CreateStraight(act_map2,20,200,50,tarmac);	
		act_map2 = plan.CreateStraight(act_map2,70,200,50,MUD);
		act_map2 = plan.CreateStraight(act_map2,-10,200,50,tarmac);	
		act_map2 = plan.CreateCircle(act_map2,50,140,200, 50, snow);
		act_map2 = plan.CreateStraight(act_map2,180,200,50,tarmac);	
		act_map2 = plan.LoopClosure(act_map2);
		/*
		act_map2 = plan.LoopClosure(act_map2);
		 */
	//Pályatervezés vége
		
		
        new GAMEPLAY(act_map2, false).StartGameplay();
    }
}
