package com.netshoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

/**
 * Hello world!
 *
 */
public class App {
	
	public static final String DBURL = "jdbc:oracle:thin:@HOST_NAME:PORT/DB";
	public static final String DBUSER = "USER";
	public static final String DBPASS = "PASS";
	
    public static void main( String[] args ) throws SQLException {
    	
    	Calendar inicio = Calendar.getInstance();
    	System.out.println("Inicio em:" + inicio.getTime());
    	
    	// Load Oracle JDBC Driver
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        
        // Connect to Oracle Database
        Connection con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

        Statement statement = con.createStatement();

        ResultSet rs = statement.executeQuery(args[0]);
        
        Calendar fim = Calendar.getInstance();
    	System.out.println("Fim em:" + fim.getTime());
        
    	System.out.println("Tempo de execução:" + (fim.getTimeInMillis() - inicio.getTimeInMillis()) + "ms");
    	
        rs.close();
        statement.close();
        con.close();
    }
}
