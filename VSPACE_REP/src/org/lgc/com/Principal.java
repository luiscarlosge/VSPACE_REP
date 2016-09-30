package org.lgc.com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import org.lgc.com.dao.Project;
import org.lgc.com.dao.User;
import org.lgc.com.utl.OWBackupUtil;


public class Principal {

	public static void main(String[] args) throws IOException, InterruptedException {
		
	/*	File folder = new File("/tmp");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }*/
	    Properties prop = Principal.initializeProp("vspaceconfig.properties");
		   

		String url = prop.getProperty("dsdsurl");
		String user = prop.getProperty("dsdsuser");
		String password = prop.getProperty("dsdspassword");
		ArrayList<String> instances = new ArrayList<String>();
		instances.add(prop.getProperty("OWIN"));
		instances.add(prop.getProperty("OWIN3"));
		instances.add(prop.getProperty("OWIN4"));
		instances.add(prop.getProperty("OWIN5"));
		
		Connection connection; 

        try {
			connection = Principal.initializeConnection(url, user, password);	
				
			if (args[0].equals("users")){
				Principal.getvSpaceUsers(connection);
				connection.close();
			}
			else if (args[0].equals("projects_all")){
				Principal.getvSpaceProjectsAll(connection);
			}else if (args[0].equals("projects_db")){
				Principal.getvSpaceDBProjects(connection);
				
			}else if (args[0].equals("backup_files")){
				
				Principal.checkBackupFiles(connection,instances);
				
			}else{
				
				System.out.println("Opcion no valida");
				System.out.println("Opciones:");
				System.out.println("users - Retorna todos los usuarios Openworks");
				System.out.println("projects_all - Retorna todos los proyectos Openworks (IP y ALL_DATA)");
				System.out.println("projects_db - Retorna solo los proyectos Openworks ALL_DATA");
			}
		} catch (SQLException e) {
			System.out.println("Error en Conexion al Integration Server");
			e.printStackTrace();
		} catch (java.lang.ArrayIndexOutOfBoundsException e){
			System.out.println("No indico Parametro");
			System.out.println("Opciones:");
			System.out.println("users - Retorna todos los usuarios Openworks");
			System.out.println("projects_all - Retorna todos los proyectos Openworks (IP y ALL_DATA)");
			System.out.println("projects_db - Retorna solo los proyectos Openworks ALL_DATA");
		}        
	}
	
	
	public static void getvSpaceUsers(Connection connection) throws SQLException{
		 LinkedList<User> users = User.getUsers(connection);
	        
	        for(int i=0;i<users.size();i++){       	
	        	System.out.println(users.get(i).ID +"\t" + users.get(i).name +"\t" + users.get(i).email +"\t"+ users.get(i).area);        	
	        }
		
	}
	public static void getvSpaceProjectsAll(Connection connection) throws SQLException{
		 LinkedList<Project> prjs = Project.getProjects(connection);
	        
	        for(int i=0;i<prjs.size();i++){       	
	        	System.out.println(prjs.get(i).prj_name);        	
	        }
		
	}
	public static void getvSpaceDBProjects(Connection connection) throws SQLException{
		 LinkedList<Project> prjs = Project.getDBProjects(connection);
	        
	        for(int i=0;i<prjs.size();i++){       	
	        	System.out.println(prjs.get(i).prj_name);        	
	        }
		
	}
	
	public static Properties initializeProp(String path){
		
		Properties prop = new Properties();
	    try {
			InputStream inputStream = new FileInputStream(path);
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		} catch (IOException e) {
			System.out.println("File not Found");
		}
		return prop;
		
	}
	
	public static Connection initializeConnection(String url, String user, String password){
		Connection connection = null;
		
		try {
			Class.forName("org.teiid.jdbc.TeiidDriver");
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al inicializar la Clase");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error en la Conexion con DS Data Server");
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public static void checkBackupFiles(Connection connection, ArrayList<String> instances) throws SQLException{
		
		LinkedList<Project> projects = Project.getDBProjects(connection);
		OWBackupUtil list = new OWBackupUtil(projects,instances);
		ArrayList<String> backups = list.getFailedBackups();
		for(String s: backups)
			System.out.println(s);
		
		
	}
	
}

