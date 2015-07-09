/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetotr2;

import java.net.ServerSocket;
import java.net.Socket;
import DataBase.*;

public class ProjetoTR2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String argv[]) /*throws Exception*/ {
	// Get the port number from the command line.
        try{
            WebServer web = new WebServer();
            ServerUDP serv = new ServerUDP();
            
            /*UserEntity user = new UserEntity();
            user.setPassword("senha");
            user.setUsername("blah");
            
            UserDataBase udb = new UserDataBase();
            udb.create(user);*/

            // Create a new thread to process the request.
            Thread tweb = new Thread(web);
            Thread tserv = new Thread(serv);

            // Start the thread.
            tweb.start();
            tserv.start();
                
        }catch(Exception e){
            System.out.println("Erro!");
            System.out.println(e);
        }
    }
    
}
