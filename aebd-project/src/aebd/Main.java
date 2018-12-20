package aebd;

import static java.lang.Thread.sleep;
import java.sql.SQLException;


public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException  {
       
        // TIMER
        int timer = 0;
                
        while (true) {
            System.out.println(timer + " seg");
            
            //START
            Monitor.start();
            
            timer += 15;
            System.out.println("--------------------------------------------- END ---------------------------------------------");
            
            try {
                sleep(15000); //15 segundos
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
            
        }
    }
}
