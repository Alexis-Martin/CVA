package algo.utils;

import graph.Argument;

import java.util.Collection;
import java.util.HashSet;

public class BreathFirst {

	public static Collection<Argument>  listBreath(Collection<Argument> listeArgs, int breath) {
		HashSet<Argument> prof = new HashSet<Argument>();

		prof.addAll(listeArgs);
		for(int i = 0; i<breath; i++ ){
			HashSet<Argument> new_prof = new HashSet<Argument>();
			for(Argument arg : prof){
				Collection<Argument> at = arg.getAttackers();
				Collection<Argument> de = arg.getDefenders();
				if(at == null){
					new_prof.addAll(at);
				}
				if(de == null){
					new_prof.addAll(de);
				}
			}
			prof = new_prof;
		}
		return prof;
		
	}
}
