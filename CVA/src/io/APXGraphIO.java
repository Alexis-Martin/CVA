package io;

import graph.AGraph;
import graph.GSAGraph;
import helper.FileHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class APXGraphIO {

	public static void write(String path, AGraph g){
		System.out.println("Enregistremment en .apx pas encore implémenté");
	}
	
	
	public static AGraph read(String path){
		File file = new File(path);
		AGraph g = new GSAGraph(file.getName());
		List<String> lines = null;
		Pattern nodePattern = Pattern.compile("arg\\((.+)\\)\\.");
		Pattern edgePattern = Pattern.compile("att\\(([a-zA-Z0-9_-]+),\\s?([a-zA-Z0-9_-]+)\\)\\.");
		Matcher m;
		
		try {
			lines = FileHelper.readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String line : lines){
			m = nodePattern.matcher(line);
			if(m.matches()){
				g.addArgument(m.group(1));
				continue;
			}
			m = edgePattern.matcher(line);
			if(m.matches()){
				g.addAttack(m.group(1), m.group(2));
			}
		}
		return g;
	}
}
