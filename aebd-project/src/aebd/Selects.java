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
                PreparedStatement ps = c_plug.prepareStatement("SELECT TABLESPACE_NAME, BLOCK_SIZE,  MAX_SIZE, STATUS, CONTENTS, INITIAL_EXTENT,   FROM DBA_TABLESPACES");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
	}
        
        public static ResultSet selectMemory() {
           
            ResultSet rs = null;
                
            try {
                PreparedStatement ps = c_root.prepareStatement("SELECT ALLOC_BYTES, USED_BYTES, POOL, POPULATE_STATUS FROM V$INMEMORY_AREA");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
	}
        
        public static ResultSet selectDatafile() {
           
            ResultSet rs = null;
                
            try {
                PreparedStatement ps = c_plug.prepareStatement("SELECT Substr(df.file_name,1,80) \"File Name\"," +
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
        
        public static ResultSet select_sessions() {
           
            ResultSet rs = null;
                
            try {
                PreparedStatement ps = c_plug.prepareStatement("Select count(*) from V_$SESSION");
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
		
            return rs;
	}
}
        
