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
public class ServerUDP1 implements Runnable{
    @Override
    public void run(){
        try{
            processRequest();
        }catch(Exception e){
            System.out.println("Erro! ServerUDP");
            System.out.println(e);
        }
    }
    
    private void processRequest() throws Exception{
        DatagramSocket serverSocket = new DatagramSocket(8888);
        int i;
        //serverSocket.setSoTimeout(10000);

          byte[] receiveData = new byte[1024]; 
          byte[] sendData  = new byte[1024]; 
          System.out.println("udp 1");

            while(true){ 
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();
                //---Parte a mudar
                String capitalizedSentence = sentence.toUpperCase(); 
                if(sentence.equals("BroadcastUDP")){
                    //Mensagem broadcast, perguntando sobre os nós ativos
                    String stringACK = new String("BroadcastACK");
                    sendData = stringACK.getBytes();
                }
                ///---Parte a mudar

                //sendData = capitalizedSentence.getBytes(); 

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); 

                System.out.println("Servidor 3");
                serverSocket.send(sendPacket); 
                System.out.println("Servidor 4");

                Thread.sleep(3000);
                sendData = "UM".getBytes();
                DatagramPacket sendPacket1 = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket1); 

                Thread.sleep(3000);
                sendData = "DOIS".getBytes();
                DatagramPacket sendPacket2 = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket2);

                for(i=0 ; i<receiveData.length ; i++){
                    receiveData[i] = 0;
                }
                for(i=0 ; i<sendData.length ; i++){
                    sendData[i] = 0;
                }
            }
    }
}
