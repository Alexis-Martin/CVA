package algo.utils;

import af.ArgumentationFramework;

public class Threshold {
	public static  int nbNodes(ArgumentationFramework graph){
		return graph.getArguments().size();
	}
	public static  int nbNodesSquare(ArgumentationFramework graph){
		return (int) Math.floor(Math.pow(graph.getArguments().size(), 2));
	}
}
