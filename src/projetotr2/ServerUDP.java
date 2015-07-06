/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetotr2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Paulo
 */
public class ServerUDP implements Runnable{
    @Override
    public void run(){
        try{
            processRequest();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void processRequest() throws Exception{
        DatagramSocket serverSocket = new DatagramSocket(9876); 

          byte[] receiveData = new byte[1024]; 
          byte[] sendData  = new byte[1024]; 

          while(true) 
            { 
              DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
              serverSocket.receive(receivePacket); 
              String sentence = new String(receivePacket.getData()); 

              InetAddress IPAddress = receivePacket.getAddress(); 

              int port = receivePacket.getPort();
              String capitalizedSentence = sentence.toUpperCase(); 

              sendData = capitalizedSentence.getBytes(); 

              DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); 

              serverSocket.send(sendPacket); 
            } 
    }
}
