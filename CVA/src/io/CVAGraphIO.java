package io;

import graph.AGraph;
import graph.adapter.AGraphAdapter;

import java.io.IOException;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSink;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.stream.file.FileSinkDOT;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.stream.file.FileSourceDOT;
import org.graphstream.stream.file.FileSourceFactory;

public class CVAGraphIO {
	
	public class Type{
		public final static int  DOT = 0;
		public final static int  DGS = 1;	
		public final static int  IMG = 2;

	}
	
	public static void write(String path, AGraph g, int type) throws IOException{
		FileSink fs;
		
		switch(type){
		
			case 0: fs = new FileSinkDOT();
					break;
			case 1: fs = new FileSinkDGS();
					break;
			case 2: fs = new FileSinkImages();
					break;
			default : fs = new FileSinkDGS();
			
		}


		fs.writeAll(AGraphAdapter.agraphToGraphstream(g, "save_graphe"), path);	
	}
	public static void write(String path, AGraph g) throws IOException{
		write(path, g , Type.DGS);
	}
	
	
	public static AGraph read(String path, int type) throws IOException, LoadingTypeException{
		FileSource fs;
		
		switch(type){
		
			case 0: fs = new FileSourceDOT();
					break;
			case 1: fs = new FileSourceDGS();
					break;
			case 2: throw new LoadingTypeException("image");
			
			default : fs = new FileSourceDGS();
			
		}
		MultiGraph g =new MultiGraph("load_graph");
		fs.addSink(g); 
		fs.readAll(path);
		fs.removeSink(g);
		return AGraphAdapter.graphstreamToAGraph(g);
		
	}
	
	public static AGraph read(String path) throws IOException, LoadingTypeException{
		
		FileSource fs = FileSourceFactory.sourceFor(path);
		MultiGraph g =new MultiGraph("load_graph");
		fs.addSink(g); 
		fs.readAll(path);
		fs.removeSink(g);
		return AGraphAdapter.graphstreamToAGraph(g);
		
	}
}
