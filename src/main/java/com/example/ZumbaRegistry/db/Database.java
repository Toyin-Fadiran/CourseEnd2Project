package com.example.ZumbaRegistry.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

	private Connection connection;
	private static Database db = new Database();

	private Database() {
		// Private constructor to enforce singleton pattern
	}

	public static Database getInstance() {
		return db;
	}

	public Connection getConnection() {
		if (connection == null || isClosed(connection)) {
			// Re-establish the connection if it's null or closed
			connect();
		}
		return connection;
	}

	private void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String user = "john";
			String password = "P@ssw0rd1";
			String url = "jdbc:mysql://localhost/zumba";
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isClosed(Connection connection) {
		try {
			return connection == null || connection.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}
	}

	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int executeUpdate(PreparedStatement preparedStatement) {
		int result = 0;

		try {
			result = preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return result;

	}

	public ResultSet executeQuery(PreparedStatement statement) {
		ResultSet set = null;
		try {
			set = statement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return set;
	}

}
