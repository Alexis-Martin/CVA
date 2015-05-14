package algo.utils;

import graph.AGraph;

public class Threshold {
	public static  int nbNodes(AGraph graph){
		return graph.getArguments().size();
	}
	public static  int nbNodesSquare(AGraph graph){
		return (int) Math.floor(Math.pow(graph.getArguments().size(), 2));
	}
}
