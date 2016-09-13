package org.lgc.com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Project {
	
	public String prj_name;
	public String prj_db_name;
	public String sid;
	public String remark;
	public String district;
	
	//Getters and Setters
	public String getPrj_name() {
		return prj_name;
	}
	public void setPrj_name(String prj_name) {
		this.prj_name = prj_name;
	}
	public String getPrj_db_name() {
		return prj_db_name;
	}
	public void setPrj_db_name(String prj_db_name) {
		this.prj_db_name = prj_db_name;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	// Retorna la lista de Proyectos en Objeto
	public static LinkedList<Project> getProjects(Connection connection) throws SQLException{
		
		LinkedList<Project> projects = new LinkedList<Project>();
		
		String sql = "select PROJECT_NAME, PROJECT_DATABASE_NAME, NODE_DB_NAME, REMARK, DISTRICT from \"VSPACE_REP\".\"OW_SYS_PROJECT\"";
		    
		Statement statement = connection.createStatement();         
		ResultSet results = statement.executeQuery(sql);
		
		while(results.next()) {
			Project prj = new Project();
			prj.prj_name = results.getString(1);
			prj.prj_db_name = results.getString(2);
			prj.sid = results.getString(3);
			prj.remark  = results.getString(4);
			prj.district  = results.getString(5);
		    projects.add(prj);           
		}         
		results.close();         
		statement.close();  
		
		return projects;
	}
	public static LinkedList<Project> getDBProjects(Connection connection) throws SQLException {
LinkedList<Project> projects = new LinkedList<Project>();
		
		String sql = "select DISTINCT PROJECT_NAME, PROJECT_DATABASE_NAME, NODE_DB_NAME, REMARK, DISTRICT from \"VSPACE_REP\".\"OW_SYS_PROJECT\" WHERE INTERPRETATION_PRJ_ID = 0";
		    
		Statement statement = connection.createStatement();         
		ResultSet results = statement.executeQuery(sql);
		
		while(results.next()) {
			Project prj = new Project();
			prj.prj_name = results.getString(1);
			prj.prj_db_name = results.getString(2);
			prj.sid = results.getString(3);
			prj.remark  = results.getString(4);
			prj.district  = results.getString(5);
		    projects.add(prj);           
		}         
		results.close();         
		statement.close();  
		
		return projects;
	}
	

}
