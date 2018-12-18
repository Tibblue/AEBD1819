package aebd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Inserts {
    private static Connection oc;
    private static String timestamp;
    private static Selects sl;
    private static ResultSet result;
    
    public Inserts(String now, Selects selects) {
        timestamp = now;
        sl = selects;
    }
    

      public static void initDB(ResultSet rs, String nr_s) {
        String s = "insert into db (id_db_root, name, platform, data_storage, db_timestamp, number_sessions) values (?,?,?,(Select sum(bytes) from DBA_DATA_FILES) ,?,?)";
        try {
            oc = BDConnection.getBDConnection_group();
            PreparedStatement ps = oc.prepareStatement(s);
            
                while(rs.next()) {

                    String id_db_root = rs.getString(1);
                    String name = rs.getString(2);
                    String platform = rs.getString(3); 

                    ps.setString(1,id_db_root);
                    ps.setString(2, name);
                    ps.setString(3, platform);
                    ps.setString(4, timestamp);
                    ps.setString(5, nr_s);

                    ps.executeUpdate();

                    // CPU            
                     System.out.println("Load CPU");
                    result = sl.selectCPU();
                    insertCPU(result);

                     // MEMORY            
                     System.out.println("Load MEMORY");
                    result = sl.selectMemory();
                    insertMemory(result);

                     // ROLES            
                     System.out.println("Load ROLES");
                    result = sl.selectRole();
                    insertRoles(result);

                     // USERS            
                     System.out.println("Load USERS");
                    result = sl.selectUser();
                    insertUsers(result);

                     // USERS and ROLES            
                     System.out.println("Load UserRoles");
                    result = sl.selectRoleUser();
                    insertRoleUser(result);

                     // TABLESPACES            
                     System.out.println("Load TABLESPACES");
                    result = sl.selectTablespace();
                    insertTablespace(result);

                 }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        finally {
            try {
                oc.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void insertCPU(ResultSet rs) {
        String s = "insert into cpu (cpu_count, db_version, cpu_core_count, cpu_socket_count, cpu_timestamp, id_db_FK) values (?,?,?,?,?,db_seq.CURRVAL)";
        try {
            PreparedStatement ps = oc.prepareStatement(s);

            while(rs.next()) {
                String core_count = rs.getString(1);
                ps.setString(3, core_count);
                String count = rs.getString(2);
                ps.setString(1, count);
                String socket_count = rs.getString(3);
                ps.setString(4, socket_count);
                ps.setString(5, timestamp);
                String version = rs.getString(6);
                ps.setString(2, version);

                ps.executeUpdate();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertMemory(ResultSet rs) {
        String s = "insert into memory (pool, alloc_bytes, used_bytes, populated_status, mem_timestamp, id_db_FK) values (?,?,?,?,?,db_seq.CURRVAL)";
        try {
            PreparedStatement ps = oc.prepareStatement(s);

            while(rs.next()) {
                String pool = rs.getString(1);
                ps.setString(1, pool);
                String a_bytes = rs.getString(2);
                ps.setString(2, a_bytes);
                String u_bytes = rs.getString(3);
                ps.setString(3, u_bytes);
                String p_status = rs.getString(4);
                ps.setString(4, p_status);
                ps.setString(5, timestamp);

                ps.executeUpdate();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        public static void insertTablespace(ResultSet rs) {
        String s = "insert into tablespace (name, block_size, max_size, status, contents, initial_extent, ts_timestamp, id_db_FK) values (?,?,?,?,?,?,?,db_seq.CURRVAL)";
        try {
            PreparedStatement ps = oc.prepareStatement(s);

            while(rs.next()) {
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

                ps.executeUpdate();

            // DATAFILES            
            System.out.println("Load DATAFILES");
            result = sl.selectDatafile();
            insertDatafile(result);
            
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    public static void insertDatafile(ResultSet rs) {
        String s = "insert into datafile (name, bytes, df_timestamp, id_tablespace_FK) values (?,?,?,tablespace_seq.CURRVAL)";
        try {
            PreparedStatement ps = oc.prepareStatement(s);

            while(rs.next()) {
                String name = rs.getString(1);
                ps.setString(1, name);
                String bytes = rs.getString(2);
                ps.setString(2, bytes);
                ps.setString(3, timestamp);

                ps.executeUpdate();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        public static void insertRoles(ResultSet rs) {
        String s = "insert into role (id_role, name) values (?,?)";
        try {
            PreparedStatement ps = oc.prepareStatement(s);

            while(rs.next()) {
                String idrole = rs.getString(1);
                ps.setString(1, idrole);
                String name = rs.getString(2);
                ps.setString(2, name);
                ps.executeUpdate();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertUsers(ResultSet rs) {
        String s = "insert into usersDB (username, account_status, default_ts, temp_ts, last_login, user_timestamp, id_db_FK) values (?,?,?,?,?,?,db_seq.CURRVAL)";
        try {
            PreparedStatement ps = oc.prepareStatement(s);

            while(rs.next()) {
                String username = rs.getString(1);
                ps.setString(1, username);
                String acc_st = rs.getString(2);
                ps.setString(2, acc_st);
                String d_ts = rs.getString(3);
                ps.setString(3, d_ts);
                String t_ts = rs.getString(4);
                ps.setString(4, t_ts);
                String login = rs.getString(5);
                ps.setString(5, login);
                ps.setString(6, timestamp);

                ps.executeUpdate();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertRoleUser(ResultSet rs) {
        String s = "insert into role_user (user_id_user, role_id_role) values ((SELECT ID_USER FROM USERSDB WHERE USERNAME = ?),(SELECT ID_ROLE FROM ROLE WHERE NAME = ?))";
        try {
            PreparedStatement ps = oc.prepareStatement(s);

            while(rs.next()) {
                String user = rs.getString(1);
                ps.setString(1, user);
                String role = rs.getString(2);
                ps.setString(2, role);
                ps.executeUpdate();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
