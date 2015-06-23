package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import Main.Main;
import algo.Algorithm;

public class AlgoFinder {

    private static final char DOT = '.';

    private static final char SLASH = '/';

    private static final String CLASS_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static List<Class<Algorithm>> findAlgo() {
    	String scannedPackage = "algo.implem";
        String scannedPath = scannedPackage;//scannedPackage.replace(DOT, SLASH);

        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(".");

        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<Algorithm>> classes = new ArrayList<Class<Algorithm>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }
    public static List<Class<Algorithm>> findAlgoInJar() {
    	String scannedPackage = "algo.implem";
        String scannedPath = scannedPackage;//scannedPackage.replace(DOT, SLASH);
        URL scannedUrl = null;
		scannedUrl = Thread.currentThread().getContextClassLoader().getSystemResource("algo/implem");
		File dir = null;
		try {
			System.out.println(scannedUrl.toURI().getPath());
			dir = new File(scannedUrl.getPath());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dir.getAbsolutePath());
        List<Class<Algorithm>> classes = new ArrayList<Class<Algorithm>>();
        if(dir.listFiles() == null){
        
        	String[] str = dir.getAbsolutePath().split("!");
        	classes = getClasseNamesInPackage(str[0].split(":")[1], scannedPackage);
        }
        else{
	        for (File file : dir.listFiles()) {
	            classes.addAll(find(file, scannedPackage));
	        }
        }
        return classes;    

    }

    @SuppressWarnings("unchecked")
	private static List<Class<Algorithm>> find(File file, String scannedPackage) {
        List<Class<Algorithm>> classes = new ArrayList<Class<Algorithm>>();
        String resource = scannedPackage + DOT + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_SUFFIX)) {
            int endIndex = resource.length() - CLASS_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
            	System.out.println(className);
                classes.add((Class<Algorithm>) Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

    private static boolean debug = true;
    public static List getClasseNamesInPackage(String jarName, String packageName){
      ArrayList classes = new ArrayList ();

      packageName = packageName.replaceAll("\\." , "/");
      if (debug) System.out.println
           ("Jar " + jarName + " looking for " + packageName);
      try{
        JarInputStream jarFile = new JarInputStream
           (new FileInputStream (jarName));
        JarEntry jarEntry;

        while(true) {
          jarEntry=jarFile.getNextJarEntry ();
          if(jarEntry == null){
            break;
          }
          if((jarEntry.getName ().startsWith (packageName)) &&
               (jarEntry.getName ().endsWith (".class")) ) {
            if (debug) System.out.println
              ("Found " + jarEntry.getName().replaceAll("/", "\\."));
            classes.add (Class.forName((jarEntry.getName().replaceAll("/", "\\.")).replaceAll(".class", "")));
          }
        }
      }
      catch( Exception e){
        e.printStackTrace ();
      }
      return classes;
   }
}