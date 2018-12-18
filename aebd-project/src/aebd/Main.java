package aebd;

import static java.lang.Thread.sleep;
import java.sql.SQLException;


public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException  {
       
        // Open connections
        System.out.println("Start Connections");
        BDConnection connect = new BDConnection();
        
        while (true) {
            Monitor.start();
            try {
                sleep(15000); //15 segundos
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
