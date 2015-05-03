package helper;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A static helper class to easely and safely open, read and close a file
 * @author Edouard Delasalles
 * source : http://thierry-leriche-dessirier.developpez.com/tutoriels/java/csv-avec-java/
 */
public class FileHelper {
	
	/**
	 * @param fileName The relative path of the file to open
	 * @return The absolute path od the file to open
	 */
	public static String getResourcePath(String fileName) {
		final File f = new File("");
		final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
		return dossierPath;
	}

	/**
	 * @param fileName The absolute path of the file to open
	 * @return The file itself
	 */
	public static File getResource(String fileName) {
		final String completeFileName = getResourcePath(fileName);
		File file = new File(completeFileName);
		return file;
	}
   
	/**
	 * @param file The file to read
	 * @return A {@link List} of {@link String} containing each line of the file in parameter
	 * @throws IOException Throw an exception if the file is incorrectly read
	 */
	public static List<String> readFile(File file) throws IOException {
		
		List<String> result = new ArrayList<String>();
		
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
			
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			result.add(line);
		}
		
		br.close();
		fr.close();
		
		return result;
	}
}
