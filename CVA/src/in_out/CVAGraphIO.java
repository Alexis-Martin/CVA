package in_out;

import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSink;
import org.graphstream.stream.file.FileSinkDGS;
import org.graphstream.stream.file.FileSinkDOT;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.stream.file.FileSourceDOT;
import org.graphstream.stream.file.FileSourceFactory;

import CVAGraph.CVAGraph;

public class CVAGraphIO {
	
	public class Type{
		public final static int  DOT = 0;
		public final static int  DGS = 1;	
		public final static int  IMG = 2;

	}
	


	
	
	public static void write(String path, CVAGraph cva, int type) throws IOException{
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


		fs.writeAll(cva.getGraph(), path);

		
	}
	public static void write(String path, CVAGraph cva ) throws IOException{
		write(path, cva, Type.DGS);
	}
	
	
	public void read(String path, CVAGraph cva, int type) throws IOException, LoadingTypeException{
		FileSource fs;
		
		switch(type){
		
			case 0: fs = new FileSourceDOT();
					break;
			case 1: fs = new FileSourceDGS();
					break;
			case 2: throw new LoadingTypeException("image");
			
			default : fs = new FileSourceDGS();
			
		}

		fs.addSink(cva.getGraph()); 
		fs.readAll(path);
		fs.removeSink(cva.getGraph());
		
	}
	public void read(String path, CVAGraph cva) throws IOException, LoadingTypeException{
		
		FileSource fs = FileSourceFactory.sourceFor(path);
		fs.addSink(cva.getGraph()); 
		fs.readAll(path);
		fs.removeSink(cva.getGraph());
		
	}
}
