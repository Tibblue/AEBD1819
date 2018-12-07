/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aebd;

import static java.lang.System.out;
import static java.lang.Thread.sleep;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author GilCunha
 */
public class Main {
    


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         BDConnection conn = new BDConnection();
        try {
            conn.connTest();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
