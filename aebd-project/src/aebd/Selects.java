package aebd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Selects {
    private static Connection c_root;
    private static Connection c_plug;

    public Selects() {
        c_root = BDConnection.getBDConnection_root();
        c_plug = BDConnection.getBDConnection_plug();
    }
            
    
    public Connection getRoot(){
        return c_root;
    }
    
    public Connection getPlug(){
        return c_plug;
    }

     public static ResultSet selectDB() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = c_plug.prepareStatement("SELECT DBID, NAME, PLATFORM_NAME FROM V$DATABASE");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }
     
    public static String selectNrSessions() {
        String nr_s = "";
        ResultSet rs = null;
        PreparedStatement ps;
        try {
            ps = c_root.prepareStatement("SELECT COUNT(*) FROM V_$SESSION");
            rs = ps.executeQuery();
            
            rs.next();
            nr_s =  rs.getString(1);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nr_s; 
    }

    public static ResultSet selectCPU() {
        ResultSet rs = null;
        PreparedStatement ps;
        try {
            ps = c_root.prepareStatement("SELECT *  FROM DBA_CPU_USAGE_STATISTICS");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs; 
    }


    public static ResultSet selectMemory() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = c_root.prepareStatement("SELECT POOL, ALLOC_BYTES, USED_BYTES, POPULATE_STATUS FROM V$INMEMORY_AREA");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }
        

    public static ResultSet selectTablespace() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = c_plug.prepareStatement("SELECT TABLESPACE_NAME, BLOCK_SIZE,  MAX_SIZE, STATUS, CONTENTS, INITIAL_EXTENT  FROM DBA_TABLESPACES");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }
        

        
    public static ResultSet selectDatafile() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = c_plug.prepareStatement("SELECT FILE_NAME, BYTES, TABLESPACE_NAME  FROM DBA_DATA_FILES UNION SELECT FILE_NAME, BYTES, TABLESPACE_NAME  FROM DBA_TEMP_FILES");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }
    
    
        public static ResultSet selectRole() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = c_plug.prepareStatement("SELECT ROLE_ID, ROLE FROM DBA_ROLES");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static ResultSet selectUser() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = c_plug.prepareStatement("SELECT USERNAME, ACCOUNT_STATUS, DEFAULT_TABLESPACE, TEMPORARY_TABLESPACE, NVL(TO_CHAR(LAST_LOGIN),'undefined') FROM DBA_USERS");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }    
}
        
