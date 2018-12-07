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
        
    public static ResultSet selectUser() {
        ResultSet rs = null;
        try {
            c_plug = BDConnection.getBDConnection_plug();
            PreparedStatement ps = c_plug.prepareStatement("SELECT USERNAME, ACCOUNT_STATUS, DEFAULT_TABLESPACE, TEMPORARY_TABLESPACE, LAST_LOGIN FROM DBA_USERS");
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
        
    public static ResultSet selectDatafile() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = c_plug.prepareStatement("SELECT file_name, bytes, tablespace_name from DBA_DATA_FILES");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }
        
    public static ResultSet selectDB() {
        ResultSet rs = null;
        try {
            PreparedStatement ps = c_plug.prepareStatement("SELECT dbid, name, platform FROM V$DATABASE");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }
    
}
        
