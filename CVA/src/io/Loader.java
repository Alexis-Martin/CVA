package io;

import java.io.IOException;

import graph.AGraph;

public class Loader {
	public static AGraph load(String path)throws IOException, LoadingTypeException{
		String[] split = path.split("\\.");
		String ext = split[split.length-1];
		if(ext.equals("dgs") || ext.equals("dot")){
			return CVAGraphIO.read(path);
		}
		if(ext.equals("apx")){
			return APXGraphIO.read(path);
		}
		return null;
	}
}
