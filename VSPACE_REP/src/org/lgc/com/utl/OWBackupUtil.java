package org.lgc.com.utl;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import org.lgc.com.Principal;
import org.lgc.com.dao.Project;

/* The OWBackupUtil Class provides the methods to check the file system backups for all ECP Openworks Instances*/

public class OWBackupUtil {


	public static void main(String[] args){	
        
        for(String file : getOWFilesBCK())
        	System.out.println(file);
        	System.out.println(getOWFilesBCK().size());
        
        
	}
	//Method to Obtain The Generated Backups Files for Yesterday
    public static ArrayList<String> getOWFilesBCK(){
    	//ResultList
    	ArrayList<String> fileSet = new ArrayList<String>(); 
    	
    	//Get Properties and Date
    	Properties prop = Principal.initializeProp("vspaceconfig.properties");
		ArrayList<String> instances = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date(System.currentTimeMillis()-24*60*60*1000);		
		dateFormat.format(date);
		String today = dateFormat.format(date).toString().replaceAll("/", "");
		
	    //Initialize FilePath
		instances.add(prop.getProperty("OWIN"));
		instances.add(prop.getProperty("OWIN3"));
		instances.add(prop.getProperty("OWIN4"));
		instances.add(prop.getProperty("OWIN5"));

		//Run every Path
		for(int i=0; i<instances.size();i++){
			File folder = new File(instances.get(i)+"/"+today);
			File[] listOfFiles = folder.listFiles();

			//Add Any bck File
			for (int i1 = 0; i1 < listOfFiles.length; i1++) {
				if(listOfFiles[i1].getName().contains("bck"))
			    fileSet.add( listOfFiles[i1].getName().substring (0,listOfFiles[i1].getName().length()-4));				
			}
		}
    	return fileSet;
    }
    
    
    //Get a List of String of All the current OWProjects
    public ArrayList<String> getOWPrjList(LinkedList<Project> prjs){
    	//ResultList
    	ArrayList<String> fileSet = new ArrayList<String>();
    	
    	//Add the Projects to the Array
    	for(Project pj: prjs)
    		fileSet.add(pj.getPrj_name());
    	
    	return fileSet;
    }
    //Return the List of Failed Backups
    public ArrayList<String> getFailedBackups(ArrayList<String> files, ArrayList<String> dbprj){
    	//ResultList
    	ArrayList<String> fileSet = new ArrayList<String>();
    	
    	//If The DB Project doesn't have a Backup File is added to the result list
    	for(String db:dbprj){
    		if(!files.contains(db))
    			fileSet.add(db);
    	}
    	return fileSet;
    }
    
  
	public static void backupCheck(){
	}

}
