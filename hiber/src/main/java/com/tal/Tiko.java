package com.tal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tal.hibernate.HibernateUtil;
import com.tal.hibernate.dao.ActorDao;
import com.tal.hibernate.model.Actor;

public class Tiko {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/sakila";
	static final String USER = "user1";
	static final String PASS = "user1";

	public static void main1(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("actor_id");
				String first = rs.getString("first_name");
				String last = rs.getString("last_name");

				System.out.print("ID: " + id);
				System.out.print(", First: " + first);
				System.out.println(", Last: " + last);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
	
		 HibernateUtil.buildSessionFactory();
		 ActorDao actorDao = new ActorDao();
		 for (Actor a : actorDao.getAllActors()) {
			System.out.println(a.getFirst_name() + " " + a.getLast_name());
		 };
		 HibernateUtil.killSessionFactory();
	}
}
