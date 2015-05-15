package helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent and parse a CSV file
 * source : {@link http://thierry-leriche-dessirier.developpez.com/tutoriels/java/csv-avec-java/}
 */

public class CsvFile {
	public final static char SEPARATOR = ';';
	
	private File file;
	private List<String> lines;
	private List<String[]> data;
	private String[] titles;
	private List<Map<String, String>> mappedData;
	
	/**
	 * Create a new {@link CsvFile} object and parse the file in parameter into a {@link List} of {@link Map}s
	 * @param file The file to parse
	 */
	public CsvFile(File file){
		this.file = file;
		init();
	}
	
	/**
	 * Parse the CSV file, and construct a {@link List} of {@link String}s that contain the lines of the file
	 */
	private void init(){
		//Read the file with the FileHelper class
		try {
			lines = FileHelper.readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Initialization of the List
		data = new ArrayList<String[]>(lines.size());
		String sep = new Character(SEPARATOR).toString();
		boolean first = true;
		
		//For each line of data, a Map is created and added to the List
		for(String line : lines){
			//If the current line is a comment or is empty, the line is ignore
			if(line.length() == 0 || line.startsWith("#")){
				continue;
			}
			
			//The line is split in an array
			//The split is made with the separator defined previously
			String[] oneData = line.split(sep);
	
			//If the current line is the first line, then it contains the titles
			//Else we add the data to the List
			if(first){
				titles = oneData;
				first = false;
			}else{
				data.add(oneData);
			}
		}
		
		mapData();
	}
	
	/**
	 * Parse each line in the {@link List} created in {@link CsvFile#init()}
	 */
	private void mapData(){
		//Creation of a List of Map that will contian our data
		mappedData = new ArrayList<Map<String, String>>(data.size());

        final int titlesLength = titles.length;
        
        //For each array of data, a new Hashmap is created and added to the List
        for (String[] oneData : data) {
        	//Creation of the Map for the current array
            final Map<String, String> map = new HashMap<String, String>();
            
            //Each data in the array is placed in the map with the title of its column as a key
            for (int i = 0; i < titlesLength; i++) {
                final String key = titles[i];
                final String value = oneData[i];
                map.put(key, value);
            }
            mappedData.add(map);
        }
	}
	
	/**
	 * @return The array containing the titles of the current CSV file
	 */
	public String[] getTitles(){
		return titles;
	}
	
	/**
	 * @return The {@link List} of the {@link Map} containing the data of the current CSV file
	 */
	public List<Map<String, String>> getMappedData() {
        return mappedData;
    }
	
}
