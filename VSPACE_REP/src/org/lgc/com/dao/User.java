package org.lgc.com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class User {
	
	public String ID;
	public String name;
	public String email;
	
	//Getters and Setters
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String area;
	
	// Retorna la lista de Usuarios en Objeto
	public static LinkedList<User> getUsers(Connection connection) throws SQLException{
		
		LinkedList<User> users = new LinkedList<User>();
		
		String sql = "select * from \"VSPACE_REP\".\"VS_USER\"";
		    
		Statement statement = connection.createStatement();         
		ResultSet results = statement.executeQuery(sql);
		
		while(results.next()) {
			User user = new User();
			user.ID = results.getString(1);
			user.name = results.getString(2);
			user.email = results.getString(3);
			user.area = results.getString(4);
		    users.add(user);           
		}         
		results.close();         
		statement.close();  
		
		return users;
	}
	
}


