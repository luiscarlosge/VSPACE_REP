package org.lgc.com.utl;
import java.io.File;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import org.lgc.com.dao.Project;

/* The OWBackupUtil Class provides the methods to check the file system backups for all ECP Openworks Instances*/

public class OWBackupUtil {
	
	private LinkedList<Project> owdb; //VDB Projects
	private ArrayList<String> instances; //FilePaths
	
	//Constructor - instances must be Initialized on the Caller Class
	public OWBackupUtil(LinkedList<Project> owdb, ArrayList<String> instances) {
		super();
		this.owdb = owdb;
		this.instances = instances;
	}
	
	//Returns the amount of OW PRojects in DB
	public int returnDBTotalProjects(){
		return owdb.size();
	}
	
	//Returns the amount of Backup Files generated in the last Diary Backup
	public int returnDBTotalFiles(){
		return this.getOWFilesBCK().size();
	}
	
	//Returns the Size in GB of the Filepaths
	public BigInteger[] returnFilePathSizes(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date(System.currentTimeMillis()-24*60*60*1000);		
		dateFormat.format(date);
		String today = dateFormat.format(date).toString().replaceAll("/", "");
		BigInteger[] filepaths = new BigInteger[(instances.size())* 2];
		
		for(int i=0; i<instances.size();i++){
			File folder = new File(instances.get(i)+"/"+today);
			File[] listOfFiles = folder.listFiles();
			BigInteger size = BigInteger.valueOf(0);
			for (int j = 0; j < listOfFiles.length; j++)
				size = size.add(BigInteger.valueOf(listOfFiles[j].length()));
			
			size= size.divide(BigInteger.valueOf(1073741824));

			
		    filepaths[i]=size;
			
		}
		return filepaths;		
	}
	

	//Method to Obtain The Generated Backups Files for Yesterday
    public  ArrayList<String> getOWFilesBCK(){
    	//ResultList
    	ArrayList<String> fileSet = new ArrayList<String>(); 
    	
    	//Get Properties and Date
    
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date(System.currentTimeMillis()-24*60*60*1000);		
		dateFormat.format(date);
		String today = dateFormat.format(date).toString().replaceAll("/", "");
		
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
    private ArrayList<String> getOWPrjList(LinkedList<Project> prjs){
    	//ResultList
    	ArrayList<String> fileSet = new ArrayList<String>();
    	
    	//Add the Projects to the Array
    	for(Project pj: prjs)
    		fileSet.add(pj.getPrj_name());
    	
    	return fileSet;
    }
    //Return the List of Failed Backups
    public ArrayList<String> getFailedBackups(){
    	//ResultList
    	ArrayList<String> fileSet = new ArrayList<String>();
    	ArrayList<String> files = this.getOWFilesBCK();
    	//If The DB Project doesn't have a Backup File is added to the result list
    	for(String db:this.getOWPrjList(this.owdb)){
    		if(!files.contains(db))
    			fileSet.add(db);
    	}
    	return fileSet;
    }
    
}
