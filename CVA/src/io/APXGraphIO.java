package io;

import helper.FileHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import af.Relation;
import af.ArgumentationFramework;
import af.Argument;
import af.GSArgumentationFramework;


public class APXGraphIO {

	public static void write(String path, ArgumentationFramework g){
		File file = new File(path);
		
		for(Argument a : g.getArguments()){
			String line = "arg("+a.getId()+").";
			FileHelper.writeLine(file, line, true);
		}
		
		for(Relation e : g.getRelations()){
			String line = "att("+e.getSource().getId()+","+e.getTarget().getId()+").";
			FileHelper.writeLine(file, line, true);
		}
	}
	
	
	public static ArgumentationFramework read(String path){
		File file = new File(path);
		ArgumentationFramework g = new GSArgumentationFramework(file.getName());
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
