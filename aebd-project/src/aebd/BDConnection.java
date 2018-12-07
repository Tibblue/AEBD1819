package aebd;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BDConnection {
	
    public static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String DB_CONNECTION_ROOT = "jdbc:oracle:thin:@localhost:1521:orcl12c";
    public static final String DB_CONNECTION_PLUG = "jdbc:oracle:thin:@localhost:1521/orcl";
    public static final String DB_USER = "sys as sysdba";
    public static final String DB_PASSWORD = "oracle";
    public static final String DB_USER_GROUP = "grupo2";
    public static final String DB_PASSWORD_GROUP = "pass";
    
    
    
    public static Connection getBDConnection(String conn, String user, String pw) {
    	
    	Connection oc = null;
    	
    	try {
    		
    		Class.forName(DB_DRIVER);
    		
    	} catch (ClassNotFoundException e) {
    		
    		System.out.println("Error driver JDBC: "+e.getMessage());
    		
    	}
    	
    	try {

    		oc = DriverManager.getConnection(conn,user,pw);
                System.out.println("Connection successful!");
            return oc;

        } catch (SQLException e) {

            System.out.println("Cannot open connection: "+e.getMessage());

        }

        return oc;

        }
    
        public static Connection getBDConnection_root() {
    	
    	return getBDConnection(DB_CONNECTION_ROOT, DB_USER, DB_PASSWORD);

        }
        
        public static Connection getBDConnection_plug() {
    	
    	return getBDConnection(DB_CONNECTION_PLUG, DB_USER, DB_PASSWORD);

        }
        
        public static Connection getBDConnection_group() {
    	
    	return getBDConnection(DB_CONNECTION_PLUG, DB_USER_GROUP, DB_PASSWORD_GROUP);

        }
                
                
    public String connTest() throws SQLException {
    	
    	String s;
    	
    	ResultSet rs;
        
        System.out.println("GRUPO2 PLUGABBLE CONNECTION:");
    	Statement st = getBDConnection_group().createStatement();
    	
    	String query = "SELECT USERNAME FROM ALL_USERS WHERE USER_ID = 8";
    	
    	rs = st.executeQuery(query);
 
        
    	rs.next();
    	
    	s = rs.getString(1);
        System.out.println(s);
    	
    	return s;
    }
    
    public void grant() throws SQLException {
    	
    	String query = "alter session set \"_ORACLE_SCRIPT\"=true;\r\n" + 
    			"";
    	
    	PreparedStatement ps = getBDConnection_root().prepareStatement(query);
    	ps.executeQuery();
    }
    	
}
    
