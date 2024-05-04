package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	private static String cfn = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/jdbc_event";
	private static String name = "root";
	private static String pass = "root";
	
	public static Connection connection;
	
	public DAO() {
		if(connection == null) {
			try {
				Class.forName(cfn);
				connection = DriverManager.getConnection(url, name, pass);
				System.out.println("Kết nối cơ sở dữ liệu thành công!");
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Kết nối cơ sở dữ liệu thất bại!");
				e.printStackTrace();
			}
		}
	}
}
