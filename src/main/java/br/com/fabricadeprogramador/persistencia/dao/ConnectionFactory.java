package br.com.fabricadeprogramador.persistencia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static Connection con;
	public static Connection getConnection() {
		String url = "jdbc:postgresql://localhost:5432/fabricawebdb";
		String usuario = "postgres";
		String senha = "postgres";

		try {
			Class.forName("org.postgresql.Driver");
			//Singleton
			if (con==null){
				//cria um novo
				con =  DriverManager.getConnection(url, usuario, senha);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
}
