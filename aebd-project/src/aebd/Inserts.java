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
        private static Connection sc;
        private static String timestamp;
        
        public Inserts(String agora) {
            timestamp = agora;
        }
	
        public static void insertCPU(ResultSet rs) {
            oc =  StatusConnection.getStatusConnection();
            String s = "insert into cpu (dbid, version, timestamp_cpu, cpu_count, cpu_core_count, cpu_socket_count, timestamp) values (?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = oc.prepareStatement(s);
                
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
                    String seis = rs.getString(6);
                    ps.setString(6, seis);
                    ps.setString(7, timestamp);
                    
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
        
        public static void insertUser(ResultSet rs) {
            oc = StatusConnection.getStatusConnection();
            Set<User> users = new HashSet<>();
            String s = "insert into dbuser (name, expirationdate, creationdate, common, accountstatus, profile, timestamp) values (?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps;
                
                while(rs.next()) {
                    User u = new User(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                    users.add(u);
                }
                
                for(User ui : users) {
                    ps = oc.prepareStatement(s);
                    
                    ps.setString(1, ui.getName());
                    ps.setString(2, ui.getExp());
                    ps.setString(3, ui.getCreation());
                    ps.setString(4, ui.getCommon());
                    ps.setString(5, ui.getAccount());
                    ps.setString(6, ui.getProfile());
                    ps.setString(7, timestamp);
                    
                    ps.executeUpdate();
               
                    String ts = ui.getTablespace();
                    String tempTs = ui.getTempTablespace();
                    
                    String getTs = "SELECT ID_TABLESPACE FROM TABLESPACE WHERE NAME = ? AND TIMESTAMP = ?";
                    String getTempTs = "SELECT ID_TABLESPACE FROM TABLESPACE WHERE NAME = ? AND TIMESTAMP = ?";
                    String getUserId = "SELECT ID_USER FROM DBUSER WHERE NAME = ?  AND CREATIONDATE = ? AND"
                            + " COMMON = ? AND ACCOUNTSTATUS = ? AND PROFILE = ? AND TIMESTAMP = ?";
                    
                    ps = oc.prepareStatement(getTs);
                    ps.setString(1, ts);
                    ps.setString(2, timestamp);
                    ResultSet idTablespace = ps.executeQuery();
                    int tsId = 0;
                    if(idTablespace.next())
                        tsId = idTablespace.getInt(1);
                    
                    ps = oc.prepareStatement(getTempTs);
                    ps.setString(1, tempTs);
                    ps.setString(2, timestamp);
                    ResultSet idTemp = ps.executeQuery();
                    int tsTempId = 0;
                    if(idTemp.next())
                        tsTempId = idTemp.getInt(1);
                    
                    ps = oc.prepareStatement(getUserId);
                    ps.setString(1, ui.getName());
                    ps.setString(2, ui.getCreation());
                    ps.setString(3, ui.getCommon());
                    ps.setString(4, ui.getAccount());
                    ps.setString(5, ui.getProfile());
                    ps.setString(6, timestamp);
                    ResultSet idUser = ps.executeQuery();
                    int userId = 0;
                    if(idUser.next())
                        userId = idUser.getInt(1);
                    
                    String insertTU = "INSERT INTO TABLESPACE_USER VALUES (null, ?,?)";
                    
                    ps = oc.prepareStatement(insertTU);
                    ps.setInt(1, tsId);
                    ps.setInt(2, userId);
                    ps.executeUpdate();
                    
                    ps.setInt(1, tsTempId);
                    ps.setInt(2, userId);
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
        
        public static void insertPrivilege(ResultSet rs) {
            oc =  StatusConnection.getStatusConnection();
            
            Comparator<Privilege> comp = (x,y) -> x.toString().compareTo(y.toString());
            Set<Privilege> privs = new TreeSet<>(comp);
            
            String s = "insert into privilege (privilege, property, timestamp) values (?,?,?)";
            String getUser = "SELECT ID_USER FROM DBUSER WHERE NAME = ? AND TIMESTAMP = ?";
            String getPriv = "SELECT IDPRIVILEGE FROM PRIVILEGE WHERE PRIVILEGE = ? AND PROPERTY = ? AND TIMESTAMP = ?";
            String insertPU = "INSERT INTO PRIVILEGE_USER VALUES (null,?,?)";
            
            
            try {
                oc = StatusConnection.getStatusConnection();
                PreparedStatement ps;
                ps = oc.prepareStatement(s);
                PreparedStatement ps1;
                ps1 = oc.prepareStatement(getUser);
                PreparedStatement ps2;
                ps2 = oc.prepareStatement(getPriv);
                PreparedStatement ps3;
                ps3 = oc.prepareStatement(insertPU);
                
                while(rs.next()) {
                    Privilege p = new Privilege(rs.getString(1), rs.getString(2), rs.getString(3));
                    privs.add(p);
                }
                rs.close();
                
                for(Privilege p : privs) {
                    
                    ps = oc.prepareStatement(s);
                    
                    ps.setString(1, p.getPrivilege());
                    ps.setString(2, p.getProperty());
                    ps.setString(3, timestamp);
                    
                    ps.executeUpdate();
                    
                    String user = p.getUser();
                    
                    
                    ps1.setString(1, user);
                    ps1.setString(2, timestamp);
                    ResultSet idUser = ps1.executeQuery();
                    int userId = 0;
                    if(idUser.next())
                        userId = idUser.getInt(1);
                    
                    
                    ps2.setString(1, p.getPrivilege());
                    ps2.setString(2, p.getProperty());
                    ps2.setString(3, timestamp);
                    ResultSet idPriv = ps2.executeQuery();
                    int privId = 0;
                    if(idPriv.next())
                        privId = idPriv.getInt(1);
                    
                    //out.println("id priv: " + privId + " nome " + p.getPrivilege());
                    //out.println("id user: " + userId + " nome " + user);
                    
                    if(userId > 0)
                    {
                        ps3.setInt(1, privId);
                        ps3.setInt(2, userId);
                        ps3.executeUpdate();
                    }
                }
                    
                    
                
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
            oc =  StatusConnection.getStatusConnection();
            
            String s = "insert into tablespace (maximumsize, ts_size, name, type, autoextend, freespace, timestamp) values (?,?,?,?,?,null,?)";
            try {
                oc = StatusConnection.getStatusConnection();
                PreparedStatement ps = oc.prepareStatement(s);
                
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
        
        public static void insertMemory(ResultSet rs) {
            String s = "insert into memory (bytes, spapool, statistic, timestamp) values (?,?,?,?)";
            try {
                oc = StatusConnection.getStatusConnection();
                PreparedStatement ps = oc.prepareStatement(s);
                
                while(rs.next())
                {
                    String um = rs.getString(1);
                    String fst = null;
                    String dois = rs.getString(2);
                    if (Integer.parseInt(um) == 0) {
                        fst = 0 + "";
                    }
                    else { 
                        int aux = (Integer.parseInt(dois) * 100)/Integer.parseInt(um);
                        fst = aux + "";
                    }
                    ps.setString(1, fst);
                    String tres = rs.getString(3);
                    ps.setString(2, tres);
                    String quatro = rs.getString(4);
                    ps.setString(3, quatro);
                    ps.setString(4, timestamp);
                    
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
        
        public static void insertRole(ResultSet rs) {
            String s = "insert into role (role, common, authentication_type, timestamp) values (?,?,?,?)";
            try {
                oc = StatusConnection.getStatusConnection();
                PreparedStatement ps = oc.prepareStatement(s);
                //SELECT ROLE, AUTHENTICATION_TYPE, COMMON FROM DBA_ROLES
                while(rs.next())
                {
                    String um = rs.getString(1);
                    String dois = rs.getString(2);
                    String tres = rs.getString(3);
                    
                    ps.setString(1, um);
                    ps.setString(2, tres);
                    ps.setString(3, dois);
                    ps.setString(4, timestamp);
                    
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
        
        public static void insertRoleUser() {
            
            String s1 = "SELECT GRANTEE, GRANTED_ROLE FROM DBA_ROLE_PRIVS";
            String s2 = "SELECT ID_USER FROM DBUSER WHERE NAME = ? AND TIMESTAMP = ?";
            String s3 = "SELECT IDROLE FROM ROLE WHERE ROLE = ? AND TIMESTAMP = ?";
            String s4 = "INSERT INTO ROLE_USER VALUES (null,?,?)";
            try {
                oc = StatusConnection.getStatusConnection();
                sc = OracleConnection.getOracleConnection();
                PreparedStatement ps1 = sc.prepareStatement(s1);
                PreparedStatement ps2 = oc.prepareStatement(s2);
                PreparedStatement ps3 = oc.prepareStatement(s3);
                PreparedStatement ps4 = oc.prepareStatement(s4);
                ResultSet rs1 = ps1.executeQuery();
                ResultSet rs2;
                ResultSet rs3;
                
                while(rs1.next())
                {
                    String um = rs1.getString(1);
                    String dois = rs1.getString(2);
                    
                    ps2.setString(1, um);
                    ps2.setString(2, timestamp);
                    rs2 = ps2.executeQuery();
                    int idUser = 0;
                    if (rs2.next()) {
                        idUser = rs2.getInt(1);
                    }
                    
                    ps3.setString(1, dois);
                    ps3.setString(2, timestamp);
                    rs3 = ps3.executeQuery();
                    int idRole = 0;
                    if(rs3.next()) {
                        idRole = rs3.getInt(1);
                    }
                    
                    rs2.close();
                    rs3.close();
                    
                    if(idRole != 0 && idUser != 0) {
                        ps4.setInt(1, idUser);
                        ps4.setInt(2, idRole);
                        ps4.executeUpdate();
                    }
                }
                rs1.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                try {
                    oc.close();
                    sc.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        public static void insertRolePrivilege() {
            
            String s1 = "SELECT ROLE, PRIVILEGE FROM ROLE_SYS_PRIVS";
            String s2 = "SELECT IDPRIVILEGE FROM PRIVILEGE WHERE PRIVILEGE = ? AND TIMESTAMP = ?";
            String s3 = "SELECT IDROLE FROM ROLE WHERE ROLE = ? AND TIMESTAMP = ?";
            String s4 = "INSERT INTO ROLE_PRIVILEGE VALUES (null,?,?)";
            try {
                oc = StatusConnection.getStatusConnection();
                sc = OracleConnection.getOracleConnection();
                PreparedStatement ps1 = sc.prepareStatement(s1);
                PreparedStatement ps2 = oc.prepareStatement(s2);
                PreparedStatement ps3 = oc.prepareStatement(s3);
                PreparedStatement ps4 = oc.prepareStatement(s4);
                ResultSet rs1 = ps1.executeQuery();
                ResultSet rs2;
                ResultSet rs3;
                
                
                while(rs1.next())
                {
                    String dois = rs1.getString(1);
                    String um = rs1.getString(2);
                    
                    ps2.setString(1, um);
                    ps2.setString(2, timestamp);
                    rs2 = ps2.executeQuery();
                    int idPrivilege = 0;
                    if (rs2.next()) {
                        idPrivilege = rs2.getInt(1);
                    }
                    
                    ps3.setString(1, dois);
                    ps3.setString(2, timestamp);
                    rs3 = ps3.executeQuery();
                    int idRole = 0;
                    if(rs3.next()) {
                        idRole = rs3.getInt(1);
                    }
                    
                    rs2.close();
                    rs3.close();
                    
                    if(idRole != 0 && idPrivilege != 0) {
                        ps4.setInt(1, idRole);
                        ps4.setInt(2, idPrivilege);
                        ps4.executeUpdate();
                    }
                }
                rs1.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                try {
                    oc.close();
                    sc.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        public static void insertDatafile(ResultSet rs) {
            String s 
                    = "insert into datafile (freespace, autoextend, name, status, maximumsize, df_size, id_tablespace, timestamp) values (?,?,?,?,?,?,(select ID_TABLESPACE from tablespace where tablespace.name = ? and timestamp = ?),?)";
            try {
                oc = StatusConnection.getStatusConnection();
                PreparedStatement ps = oc.prepareStatement(s);
                 
                while(rs.next())
                {
                    String free = rs.getString(4);
                    String auto = rs.getString(5);
                    String name = rs.getString(1);
                    String status = rs.getString(6);
                    String ms = rs.getString(2);
                    String size = rs.getString(3);
                    String tablespace_name = rs.getString(7);
                    
                    ps.setString(1, free);
                    ps.setString(2, auto);
                    ps.setString(3, name);
                    ps.setString(4, status);
                    ps.setString(5, ms);
                    ps.setString(6, size);
                    ps.setString(7, tablespace_name);
                    ps.setString(8, timestamp);
                    ps.setString(9, timestamp);
                    
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
        
        public static void insertSession(ResultSet rs) {
            String s = "insert into sessiondb (type, command, module, machine, osuser, status, sql_id, sql_child_number, secs_in_wait, user_iduser, timestamp) values (?,?,?,?,?,?,?,?,?, (select id_user from dbuser where dbuser.name = ? and timestamp = ?),?)";
            try {
                oc = StatusConnection.getStatusConnection();
                PreparedStatement ps = oc.prepareStatement(s);
                while(rs.next())
                {
                    String type = rs.getString(1);
                    String command = rs.getString(2);
                    String module = rs.getString(3);
                    String machine = rs.getString(4);
                    String osuser = rs.getString(5);
                    String status = rs.getString(6);
                    String sql_id = rs.getString(7);
                    String sql_child = rs.getString(8);
                    String secs = rs.getString(9);
                    String username = rs.getString(10);
                    
                    ps.setString(1, type);
                    ps.setString(2, command);
                    ps.setString(3, module);
                    ps.setString(4, machine);
                    ps.setString(5, osuser);
                    ps.setString(6, status);
                    ps.setString(7, sql_id);
                    ps.setString(8, sql_child);
                    ps.setString(9, secs);
                    ps.setString(10, username);
                    ps.setString(11, timestamp);
                    ps.setString(12, timestamp);
                    
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
        
}
