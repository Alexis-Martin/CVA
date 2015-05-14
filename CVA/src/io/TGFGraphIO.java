package io;

import graph.AEdge;
import graph.AGraph;
import graph.Argument;
import graph.GSAGraph;
import helper.FileHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TGFGraphIO {
	
	public static void write(String path, AGraph g){
		File file = new File(path);
		
		for(Argument a : g.getArguments()){
			String line = a.getId();
			FileHelper.writeLine(file, line, true);
		}
		
		FileHelper.writeLine(file, "#", true);
		
		for(AEdge e : g.getRelations()){
			String line = e.getSource().getId()+" "+e.getTarget().getId();
			FileHelper.writeLine(file, line, true);
		}
	}
	
	
	public static AGraph read(String path){
		File file = new File(path);
		AGraph g = new GSAGraph(file.getName());
		List<String> lines = null;
		Pattern nodePattern = Pattern.compile("([a-zA-Z0-9_-]+)");
		Pattern edgePattern = Pattern.compile("([a-zA-Z0-9_-]+)\\s+([a-zA-Z0-9_-]+)");
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
