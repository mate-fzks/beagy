
public class MAIN {

	
	
	public static void main(String[] args) {
		
	//P�lyatervez�s start
		
		PLANNER plan = new PLANNER();
		//Click Default
		MAP act_map1 = plan.DefaultMap();
		
		//Click Create
		MAP act_map2 = plan.CreateMap();
		act_map2 = plan.CreateStraight(act_map2,100,200);
		act_map2 = plan.CreateCircle(act_map2);
		act_map2 = plan.LoopClosure(act_map2);
		
	//P�lyatervez�s v�ge
		
		
        new GAMEPLAY(act_map1, false).StartGameplay();
    }
}
