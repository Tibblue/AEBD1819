package aebd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Selects {
	
    private static Connection c_root;
    private static Connection c_plug;
    private static Connection c_group;
            
        public Selects() {
            c_root = BDConnection.getBDConnection_root();
            c_plug = BDConnection.getBDConnection_plug();
            c_group = BDConnection.getBDConnection_group();
        }
    
	public static ResultSet selectCPU() {
            
            ResultSet rs = null;
            
            PreparedStatement ps;
            try {
                ps = c.prepareStatement("SELECT * FROM DBA_CPU_USAGE_STATISTICS");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
           return rs; 
        }
	
	public static ResultSet selectUser() {
            ResultSet rs = null;
                
            try {
                c = BDConnection.getBDConnection();
                PreparedStatement ps = c.prepareStatement("SELECT USERNAME, EXPIRY_DATE, CREATED, COMMON, ACCOUNT_STATUS, PROFILE, DEFAULT_TABLESPACE, TEMPORARY_TABLESPACE FROM DBA_USERS");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
        }
	
	public static ResultSet selectPrivilege() {
		
            ResultSet rs = null;
                
            try {
                PreparedStatement ps = c.prepareStatement("SELECT GRANTEE, PRIVILEGE, ADMIN_OPTION FROM DBA_SYS_PRIVS");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
	}
	
	public static ResultSet selectTablespace() {
		
            ResultSet rs = null;
                
            try {
                PreparedStatement ps = c.prepareStatement("SELECT MAX_SIZE, BLOCK_SIZE, TABLESPACE_NAME, CONTENTS, NEXT_EXTENT, INITIAL_EXTENT FROM DBA_TABLESPACES");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
	}
        
        public static ResultSet selectMemory() {
           
            ResultSet rs = null;
                
            try {
                PreparedStatement ps = c.prepareStatement("SELECT ALLOC_BYTES, USED_BYTES, POOL, POPULATE_STATUS FROM V$INMEMORY_AREA");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
	}
        
        public static ResultSet selectDatafile() {
           
            ResultSet rs = null;
                
            try {
                PreparedStatement ps = c.prepareStatement("SELECT Substr(df.file_name,1,80) \"File Name\"," +
                                                        "Round(df.bytes/1024/1024,0) \"Size (M)\"," +
                                                        "decode(e.used_bytes,NULL,0,Round(e.used_bytes/1024/1024,0)) \"Used (M)\"," +
                                                        "decode(f.free_bytes,NULL,0,Round(f.free_bytes/1024/1024,0)) \"Free (M)\"," +
                                                        "df.autoextensible," +
                                                        "df.status," +
                                                        "df.tablespace_name " +
                                                        "FROM    DBA_DATA_FILES DF," +
                                                        "        (SELECT file_id," +
                                                        "        sum(bytes) used_bytes " +
                                                        "        FROM dba_extents " +
                                                        "        GROUP by file_id) E," +
                                                        "        (SELECT sum(bytes) free_bytes," +
                                                        "        file_id\n" +
                                                        "        FROM dba_free_space" +
                                                        "        GROUP BY file_id) f " +
                                                        "WHERE    e.file_id (+) = df.file_id " +
                                                        "AND      df.file_id  = f.file_id (+) " +
                                                        "ORDER BY df.tablespace_name, " +
                                                        "         df.file_name");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
	}
        
        public static ResultSet selectRole() {
           
            ResultSet rs = null;
                
            try {
                PreparedStatement ps = c.prepareStatement("SELECT ROLE, AUTHENTICATION_TYPE, COMMON FROM DBA_ROLES");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
	}
        
        public static ResultSet selectSession() {
           
            ResultSet rs = null;
                
            try {
                PreparedStatement ps = c.prepareStatement("SELECT TYPE, COMMAND, MODULE, MACHINE, OSUSER, STATUS, SQL_ID, SQL_CHILD_NUMBER, SECONDS_IN_WAIT, USERNAME FROM V$SESSION");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
	}
        
}

