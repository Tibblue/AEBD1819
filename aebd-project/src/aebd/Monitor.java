/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aebd;

import java.sql.ResultSet;
import java.time.LocalDateTime;


public class Monitor {
    
    public static void start(){
        
        System.out.println("Start Monitor");
        String now = LocalDateTime.now().toString().replace("T", " ");
        Selects sl = new Selects();                                  
        ResultSet rs = sl.selectDB();
        String nr_sessions = sl.selectNrSessions();
        
        // Init DB
        System.out.println("Initialize DB");
        Inserts in = new Inserts(now, sl);
        in.initDB(rs, nr_sessions);
    }
    
}

