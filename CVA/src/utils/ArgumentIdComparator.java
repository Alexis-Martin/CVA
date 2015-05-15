package utils;

import graph.Argument;

import java.util.Comparator;

public class ArgumentIdComparator implements Comparator<Argument> {

	public ArgumentIdComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Argument o1, Argument o2) {
		return o1.getId().compareTo(o2.getId());
	}

}
