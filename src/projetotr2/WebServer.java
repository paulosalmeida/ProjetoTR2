/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetotr2;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Paulo
 */
public class WebServer implements Runnable{
    @Override
    public void run(){
        try{
            runServer();
        }catch(Exception e){
            System.out.println("Erro! WebServer");
            System.out.println(e);
        }
    }
    
    private void runServer() throws Exception{
    // Get the port number from the command line.
        try{
            int port = 8080;

            // Establish the listen socket.
            ServerSocket socket = new ServerSocket(port);

            // Process HTTP service requests in an infinite loop.
            while (true) {
                // Listen for a TCP connection request.
                Socket connection = socket.accept();

                // Construct an object to process the HTTP request message.
                HttpRequest request = new HttpRequest(connection);

                // Create a new thread to process the request.
                Thread tweb = new Thread(request);

                // Start the thread.
                tweb.start();
            }
        }catch(Exception e){
            System.out.println("Erro!");
            System.out.println(e);
        }
    }
}
