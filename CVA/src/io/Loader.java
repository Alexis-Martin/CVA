package io;

import java.io.IOException;

import af.ArgumentationFramework;

public class Loader {
	public static ArgumentationFramework load(String path)throws IOException, LoadingTypeException{
		String[] split = path.split("\\.");
		String ext = split[split.length-1];
		if(ext.equals("dgs") || ext.equals("dot")){
			return GSGraphIO.read(path);
		}
		if(ext.equals("apx")){
			return APXGraphIO.read(path);
		}
		if(ext.equals("tgf")){
			return TGFGraphIO.read(path);
		}
		return null;
	}
	public static void write(String path, ArgumentationFramework af)throws IOException, LoadingTypeException{
		String[] split = path.split("\\.");
		String ext = split[split.length-1];
		if( ext.equals("dgs")){
			GSGraphIO.write(path,af,GSGraphIO.Type.DGS);
		}
	}
}
