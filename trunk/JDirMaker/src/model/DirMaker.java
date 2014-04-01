package model;

import java.io.File;

/**
 * Class responsible for directory tree creation
 * @author Sergio Cormio
 *
 */
public class DirMaker {
	
	public boolean createSetOfPaths(String paths){
		return createSetOfPaths(paths,"/","\n","");
	}
	
	public boolean createSetOfPaths(String paths, String directoriesSeparator, String pathsSeparator,String parentPath){
		boolean result = true;
		paths = paths.replace(directoriesSeparator, "/");
		for(String path : paths.split(pathsSeparator)){
			result = result | createDirs(parentPath,path);
		}
		return result;
	}
	
	private boolean createDirs(String parentPath, String path){
		try{
			return new File(parentPath,path).mkdirs();
		}catch(Throwable t){
			t.printStackTrace();
			return false;
		}
	}
}
