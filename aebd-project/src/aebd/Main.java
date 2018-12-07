package aebd;

import java.sql.SQLException;
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
