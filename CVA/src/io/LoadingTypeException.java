package io;

public class LoadingTypeException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoadingTypeException(String t){
			System.err.println("It's not possible to load a file of type "+t+" as a CVAGraph");
	}
		
}
