package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi{
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String DATABASE =
			"jdbc:mysql://localhost:3306/dbaplikasiperpustakaan";
	private final String USER = "root";
	private final String PASSWORD = "";
	
	private Connection connection;
	private String pesanKesalahan;
	
	public String getPesanKesalahan(){
		return pesanKesalahan;
	}
	
	public Connection getConnection(){
		connection = null;
		pesanKesalahan = "";
		
	try{
	Class.forName(DRIVER);
	} catch (ClassNotFoundException ex){
		pesanKesalahan = "JDBC Driver tidak ditemukan atau rusak\n"+ex;
	}
	
	if(pesanKesalahan.equals("")){
		try{
			connection = DriverManager.getConnection(DATABASE+"?user="+USER+"&password="+PASSWORD+"");
			} catch (SQLException ex){
			pesanKesalahan = "Koneksi ke"+DATABASE+"gagal\n"+ex;
			}
		}
		return connection;
		}
	}
			