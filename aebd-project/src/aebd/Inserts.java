package aebd;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


public class Inserts {
	
        
	private static Connection oc;
        private static String timestamp;
        private static String id_db;
        
        public Inserts(String now) {
            timestamp = now;
        }
	
        public static void insertCPU(ResultSet rs) {
            oc =  BDConnection.getBDConnection_group();
            String s = "insert into cpu (cpu_count, db_version, cpu_core_count,cpu_socket_count,cpu_timestamp) values (?,?,?,?,?,)";
            try {
                PreparedStatement ps = oc.prepareStatement(s);
                
                while(rs.next())
                {
                    String core_count = rs.getString(1);
                    ps.setString(3, core_count);
                    String count = rs.getString(2);
                    ps.setString(1, count);
                    String socket_count = rs.getString(3);
                    ps.setString(4, socket_count);
                    ps.setString(6, id_db);
                    ps.setString(5, timestamp);
                    String version = rs.getString(6);
                    ps.setString(2, version);

                    
                    ps.executeUpdate();
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                try {
                    oc.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        public static void insertUsers(ResultSet rs) {
            
            String s = "insert into db_users (username, account_status, default_ts, temp_ts, last_login, user_timestamp, id_db, role) values (?,?,?,?,?,?,?,?,?)";
            try {
                oc = BDConnection.getBDConnection_group();
                PreparedStatement ps = oc.prepareStatement(s);
                
                /*TODO: acabar isto com base no insert db. como inserri role?.*/
                while(rs.next())
                {
                    String um = rs.getString(1);
                    ps.setString(1, um);
                    String dois = rs.getString(2);
                    ps.setString(2, dois);
                    String tres = rs.getString(3);
                    ps.setString(3, tres);
                    String quatro = rs.getString(4);
                    ps.setString(4, quatro);
                    String cinco = rs.getString(5);
                    ps.setString(5, cinco);
                    ps.setString(6, timestamp);
                    
                    ps.executeUpdate();
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                try {
                    oc.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
       
        
        public static void insertTablespace(ResultSet rs) {
            
            String s = "insert into tablespace (name, block_size, max_size, status, contents, initial_extent, ts_timestamp) values (?,?,?,?,?,?,?,?)";
            try {
                oc = BDConnection.getBDConnection_group();
                PreparedStatement ps = oc.prepareStatement(s);
                
                while(rs.next())
                {
                    String name = rs.getString(1);
                    ps.setString(1, name);
                    String b_size = rs.getString(2);
                    ps.setString(2, b_size);
                    String m_size = rs.getString(3);
                    ps.setString(3, m_size);
                    String status = rs.getString(4);
                    ps.setString(4, status);
                    String contents = rs.getString(5);
                    ps.setString(5, contents);
                    String i_extent = rs.getString(6);
                    ps.setString(6, i_extent);
                    ps.setString(7, timestamp);
                    ps.setString(8, id_db);
                    ps.executeUpdate();
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                try {
                    oc.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        public static void insertMemory(ResultSet rs) {
            String s = "insert into memory (pool, alloc_bytes, used_bytes, populated_status, mem_timestamp, id_db) values (?,?,?,?,?,?)";
            try {
                oc = BDConnection.getBDConnection_group();
                PreparedStatement ps = oc.prepareStatement(s);
                
                while(rs.next())
                {
                    String pool = rs.getString(1);
                    ps.setString(1, pool);
                    String a_bytes = rs.getString(2);
                    ps.setString(2, a_bytes);
                    String u_bytes = rs.getString(3);
                    ps.setString(3, u_bytes);
                    String p_status = rs.getString(4);
                    ps.setString(4, p_status);
                    ps.setString(5, timestamp);
                    ps.setString(6, id_db);
                    
                    ps.executeUpdate();
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                try {
                    oc.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
              
       
        
        public static void insertDatafile(ResultSet rs) {
            String s 
                    = "insert into datafile (name, bytes, df_timestamp, id_tablespace) values (?,?,?,(select ID_TABLESPACE from tablespace where tablespace.name = ? and timestamp = ?))";
            try {
                oc = BDConnection.getBDConnection_group();
                PreparedStatement ps = oc.prepareStatement(s);
                 
                while(rs.next())
                {
                    String name = rs.getString(1);
                    ps.setString(1, name);
                    String bytes = rs.getString(2);
                    ps.setString(2, bytes);
                    ps.setString(3, timestamp);
                    String ts_name = rs.getString(3);
                    ps.setString(4, ts_name);
                    ps.setString(5,timestamp);
                    
                    ps.executeUpdate();
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                try {
                    oc.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
                public static void insertDB(ResultSet rs) {
            
                String s = "insert into db (id_db, name, plataform, data_storage, db_timestamp, number_sessions) values (?,?,?,?,(Select count(*) from V_$SESSION))";
            try {
                oc = BDConnection.getBDConnection_group();
                PreparedStatement ps = oc.prepareStatement(s);
                
                String id_db = rs.getString(1);
                String name = rs.getString(2);
                String platform = rs.getString(3);  
                    
                int data_storage = 0;
                /*todo*/          
                    
                ps.setString(1, name);
                ps.setString(2, platform);
                ps.setString(3, Integer.toString(data_storage));
                ps.setString(4, timestamp);
                ps.setString(5, Integer.toString(nr_s));
                    
                ps.executeUpdate();

                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                try {
                    oc.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
}
