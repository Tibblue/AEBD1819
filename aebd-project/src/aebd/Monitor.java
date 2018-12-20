/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aebd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class Monitor {
    
    public static void start(){
        
        System.out.println("Start Monitor");

        Selects sl = new Selects();                                  
        ResultSet rs = sl.selectDB();
        String nr_sessions = sl.selectNrSessions();
        
        // Init DB
        System.out.println("Initialize DB");
        Inserts in = new Inserts(sl);
        in.initDB(rs, nr_sessions);
        
        try {
            sl.getPlug().close();
            sl.getRoot().close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

