package si.jdbc.exemplos.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ClienteQueryMain {

	static final String currentDBMS = "PostgreSQL"; // PostgreSQL ou MySQL ou SQLServer
	static final String currentDatabase = "EMPRESA";

	// PosrgreSQL JDBC driver name and database URL
	static final String POSTGRESQL_CONNECTION_URL = "jdbc:postgresql://iserver:5432/postgres?currentSchema="
			+ currentDatabase;
	static final String POSTGRESQL_USER = "postgres";
	static final String POSTGRESQL_PASSWORD = "is";

	// MySQL JDBC driver name and database URL
	static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String MYSQL_CONNECTION_URL = "jdbc:mysql://iserver:3306/" + currentDatabase + "?useSSL=false";
	static final String MYSQL_USER = "root";
	static final String MYSQL_PASSWORD = "is";

	// Microsoft SQL Server JDBC driver name and database URL
	static final String MSSQL_JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String MSSQL_CONNECTION_URL = "jdbc:sqlserver://iserver:1433;databaseName=" + currentDatabase;
	static final String MSSQL_USER = "SA";
	static final String MSSQL_PASSWORD = "to be established";

	public static void main(String[] args) {

		String urlForConnection = null, USER = null, PASSWORD = null;

		// Initialize current JDBC driver name, database URL, user and password
		System.out.println("Current DBMS: " + currentDBMS);
		switch (currentDBMS) {
		case "PostgreSQL": {
			urlForConnection = POSTGRESQL_CONNECTION_URL;
			USER = POSTGRESQL_USER;
			PASSWORD = POSTGRESQL_PASSWORD;
			break;
		}
		case "MySQL": {
			urlForConnection = MYSQL_CONNECTION_URL;
			USER = MYSQL_USER;
			PASSWORD = MYSQL_PASSWORD;
			break;
		}
		case "SQLServer": {
			urlForConnection = MSSQL_CONNECTION_URL;
			USER = MSSQL_USER;
			PASSWORD = MSSQL_PASSWORD;
			break;
		}
		default:
			System.out.println("ERROR: not a defined DBMS...");
			System.exit(1);
		}

		Connection connection = null;
		Statement statement = null;
		String sql;

		ResultSet resultSet = null;
		try {
			Properties properties = new Properties();
			properties.put("user", USER);
			properties.put("password", PASSWORD);

			System.out.println("URL = " + urlForConnection + "  User = " + USER + "  Password = " + PASSWORD);
			connection = DriverManager.getConnection(urlForConnection, properties); // from JDBC version 2.0

			System.out.println("Creating statement...");
			statement = connection.createStatement();
			sql = "SELECT * FROM EMPREGADO";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int numCc = resultSet.getInt("NumCc");
				String nomeProprio = resultSet.getString("NomeProprio");
				String apelido = resultSet.getString("Apelido");
				System.out.print("[Cursor: " + resultSet.getRow() + "] ");
				System.out.print("NumCc: " + numCc);
				System.out.print(",NomeProprio: " + nomeProprio);
				System.out.print(",Apelido: " + apelido);
				System.out.println();
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Fim do exemplo...");
	}
}
