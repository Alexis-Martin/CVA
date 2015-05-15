package utils;

import graph.Argument;

import java.util.Comparator;

public class ArgumentUtilityComparator implements Comparator<Argument> {

	public ArgumentUtilityComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Argument arg0, Argument arg1) {
		if(arg0.getUtility() > arg1.getUtility())
			return 1;
		if(arg0.getUtility() == arg1.getUtility())
			return arg0.getId().compareTo(arg1.getId());
		
		return -1;
	}

}
