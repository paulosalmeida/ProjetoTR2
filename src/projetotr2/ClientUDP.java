/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetotr2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author Paulo
 */
public class ClientUDP implements Runnable{
    private ArrayList<String> listaIP = new ArrayList<>();
    
    @Override
    public void run(){
        try{
            UDPrequest();
        }catch(Exception e){
            
        }finally{
            System.out.println("Lista de msgs:");
            for(String s : listaIP){
                System.out.println(s);
            }
        }
    }
    
    private void UDPrequest() throws Exception{
        String sentence;
        
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 

        do{
            DatagramSocket clientSocket = new DatagramSocket();
            clientSocket.setSoTimeout(10000);

            InetAddress IPAddress = InetAddress.getByName("255.255.255.255");

            byte[] sendData = new byte[1024]; 
            byte[] receiveData = new byte[1024]; 

            sentence = inFromUser.readLine(); 
            sendData = sentence.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8888); 

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            long t= System.currentTimeMillis();
            long end = t+7000;

            do{
                clientSocket.receive(receivePacket);
                System.out.println("Cliente 3");

                receivePacket.getAddress();

                String modifiedSentence = new String(receivePacket.getData()); 

                System.out.println("FROM SERVER:" + modifiedSentence);
                if(!listaIP.contains(receivePacket.getAddress().getHostAddress())){
                    listaIP.add(receivePacket.getAddress().getHostAddress());
                }
            }while(System.currentTimeMillis() < end);
            
            System.out.println("Saiu do loop");
            /*
            clientSocket.receive(receivePacket);
            System.out.println("Cliente 4");

            receivePacket.getAddress();

            String modifiedSentence = new String(receivePacket.getData()); 

            System.out.println("FROM SERVER:" + modifiedSentence);*/clientSocket.close();
            }while(true);
            
    }
        
    public ArrayList<String> getListaIP(){
        return listaIP;
    }
}
