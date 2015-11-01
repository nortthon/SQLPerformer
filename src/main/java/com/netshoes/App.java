package com.netshoes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class App {
	
    public static void main( String[] args ) {
    	
    	Connection con = null;
    	Statement statement = null;
    	ResultSet rs = null;
    	OutputStream output = null;
    	
    	if (args[0] == null || args[0].equals("")){
    		throw new IllegalArgumentException("File Properties must be passed");
    	}
    	
    	try {
    		Properties prop = new Properties();
    		prop.load(new FileInputStream(args[0]));
    		
    		for (int t=0; t < Integer.valueOf(prop.getProperty("app.qtd.execute").trim()).intValue(); t++){
    			System.out.println("==> Start Execute");
    		
	    		StringBuilder str = new StringBuilder();
	    		str.append("jdbc:oracle:thin:@").append((prop.getProperty("app.db.host").trim()))
	    										.append(":")
	    										.append(prop.getProperty("app.db.port").trim())
	    										.append("/")
	    										.append(prop.getProperty("app.db.database").trim());
	    		
		    	Calendar inicio = Calendar.getInstance();
		    	System.out.println("Execution " + t + " - Start Time: " + inicio.getTime());
		    	
		        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		        con = DriverManager.getConnection(str.toString(), 
		        								  prop.getProperty("app.db.user").trim(), 
		        								  prop.getProperty("app.db.pass").trim());
		
		        statement = con.createStatement();
		        
		        List<String> querys = getPropertyList(prop, "app.query");
		        
		        for (int i =0; i < querys.size(); i++){
		        	rs = statement.executeQuery(querys.get(i));
		        }
		        
		        Calendar fim = Calendar.getInstance();
		    	System.out.println("Execution " + t + " - End Time: " + fim.getTime());
		        
		    	System.out.println("Execution " + t + " - Total goal: " + (fim.getTimeInMillis() - inicio.getTimeInMillis()) + "ms");
		    	
				rs.close();
				statement.close();
				con.close();
		    	
				System.out.println("==> Stop Execute");
    		}
	    	
    	} catch (SQLException ex ){
    		ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
				rs.close();
				statement.close();
				con.close();
				
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	
    }
    
    public static List<String> getPropertyList(Properties properties, String name) 
    {
        List<String> result = new ArrayList<String>();
        for (Map.Entry<Object, Object> entry : properties.entrySet())
        {
            if (((String)entry.getKey()).matches("^" + Pattern.quote(name) + "\\.\\d+$"))
            {
                result.add((String) entry.getValue());
            }
        }
        return result;
    }
}
