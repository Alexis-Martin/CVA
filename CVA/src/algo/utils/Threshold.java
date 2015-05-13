package algo.utils;

import graph.AGraph;

public class Threshold {
	public static  double nbNodes(AGraph graph){
		return graph.getArguments().size();
	}
	public static  double nbNodesSquare(AGraph graph){
		return Math.pow(graph.getArguments().size(), 2);
	}
}
