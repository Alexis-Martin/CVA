package in_out;

public class LoadingTypeException extends Exception{
	
	public LoadingTypeException(String t){
			System.err.println("It's not possible to load a file of type "+t+" as a CVAGraph");
	}
		
}
