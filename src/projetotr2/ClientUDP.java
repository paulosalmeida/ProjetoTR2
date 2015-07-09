/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetotr2;

import DataBase.IPAddressDatabase;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Paulo
 */
public class ClientUDP implements Runnable{
    private ArrayList<String> listaIP = new ArrayList<>();
    private boolean loop = true;
    
    //Construtores
    public ClientUDP(boolean loop) throws Exception {
	this.loop = loop;
    }
    public ClientUDP() throws Exception {
        this.loop = true;
    }
    
    @Override
    public void run(){
        do{
            try{
                UDPrequest();
            }catch(Exception e){

            }finally{
                System.out.println("Lista de msgs:");
                for(String s : listaIP){
                    System.out.println(s);
                }
                
                try{
                    Thread.sleep(10000);
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }while(loop);
    }
    
    private void UDPrequest() throws Exception{
        String sentence, ip;
        ArrayList<String> IPsRodada = new ArrayList<>();
        IPAddressDatabase ipdb = new IPAddressDatabase();
        
        listaIP.removeAll(listaIP);
        
        //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            DatagramSocket clientSocket = new DatagramSocket();
            clientSocket.setSoTimeout(5000);

            InetAddress IPAddress = InetAddress.getByName("255.255.255.255");

            byte[] sendData = new byte[1024]; 
            byte[] receiveData = new byte[1024]; 

            //sentence = inFromUser.readLine(); 
            sendData = "HelloUDP".getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8888); 

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            /*long t= System.currentTimeMillis();
            long end = t+10000,*/
            int i=0;

            do{
                clientSocket.receive(receivePacket);

                receivePacket.getAddress();

                String modifiedSentence = new String(receivePacket.getData()); 

                System.out.println("FROM SERVER:" + modifiedSentence);
                ip = receivePacket.getAddress().getHostAddress();
                IPsRodada.add(receivePacket.getAddress().getHostAddress());
                listaIP.add(receivePacket.getAddress().getHostAddress());
                ipdb.create(receivePacket.getAddress().getHostAddress());
            }while(i<1);
            
            //System.out.println("Saiu do loop");
            /*
            clientSocket.receive(receivePacket);
            System.out.println("Cliente 4");

            receivePacket.getAddress();

            String modifiedSentence = new String(receivePacket.getData()); 

            System.out.println("FROM SERVER:" + modifiedSentence);*/
            clientSocket.close();
        }
    }
    
    public ArrayList<String> getListaIP(){
        return listaIP;
    }
}
