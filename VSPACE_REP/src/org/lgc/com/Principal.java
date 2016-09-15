package org.lgc.com;

import org.teiid.net.socket.SingleInstanceCommunicationException;
import org.teiid.net.socket.SocketServerInstanceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Properties;

import org.lgc.com.dao.Project;
import org.lgc.com.dao.User;
import org.teiid.core.types.BlobImpl;


@SuppressWarnings("unused")
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
		
		Connection connection; 

		try {
			Class.forName("org.teiid.jdbc.TeiidDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Error Encontrando el Driver de Teiid");
			e.printStackTrace();
		} 
        try {
			connection = DriverManager.getConnection(url, user, password);	
			
			
			if (args[0].equals("users")){
				Principal.getvSpaceUsers(connection);
				connection.close();
			}
			else if (args[0].equals("projects_all")){
				Principal.getvSpaceProjectsAll(connection);
			}else if (args[0].equals("projects_db")){
				Principal.getvSpaceDBProjects(connection);
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
}

