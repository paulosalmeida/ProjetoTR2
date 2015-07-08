package projetotr2;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author JorgeGabriel, Alisson Lógica pensada: Estabelecer uma conexão inicial
 * entre todos os membros da conversa por Broadcast UDP. Logo após, contar
 * membros e arquivos. Um agente, chamado servidor, organiza toda a informação e
 * distribui os arquivos entre os membros, mantendo as tabelas com todos os
 * arquivos e membros. Usando KeepAlive, conta os membros ativos. Quando alguém
 * entra/sai, refaz o processo.
 *
 * Desafios: -Contar e identificar por IP agentes no programa -Contar e
 * manipular arquivos entre agentes -Transferir arquivos por TCP (De onde,
 * servidor ou de cada peer? Para quem?) -Manter a escuta em Broadcast UDP após
 * fazer o load balance -Criar algoritmo de Load Balance
 *
 * Esquema inicial: Escuta/entra na rede => Contagem de arquivos e agentes =>
 * Distribuição => Escuta...
 */
public class TrabalhoTR2 implements Runnable {

    public static TrabalhoTR2 getInstance() {
        return DiscoveryThreadHolder.INSTANCE;
    }

    private static class DiscoveryThreadHolder {

        private static final TrabalhoTR2 INSTANCE = new TrabalhoTR2();
    }

    DatagramSocket socket;

    //Descobre todos os agentes na rede escutando na porta 8888 e retorna seus IPs individuais
    private ArrayList<String> DiscoverNetwork(){//FileDiscovery ArvoreDeArquivos) {
        ArrayList<String> IPList = new ArrayList<>();
        try {
            DatagramSocket Socket = new DatagramSocket();
            Socket.setBroadcast(true);
            byte[] sendData = "ENTRAR_NA_REDE".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
            Socket.send(sendPacket);

            
            
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            Socket.receive(receivePacket);
            
            String message = new String(receivePacket.getData()).trim();
                if (message.equals("ENTRAR_NA_REDE")) {
                    sendData = "BEM_VINDO".getBytes();
                    System.out.println("Entrando no fluxo");

                    //Send a response
                    sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    Socket.send(sendPacket);
               }
            
            IPList.add(receivePacket.getAddress().getHostAddress());
    
            Socket.close();
            
            return IPList;
        } catch (IOException e) {

        }
        return IPList;
    }

    private void DistributeFileData(FileDiscovery ArvoreDeArquivos, ArrayList<String> IPList) {

        try {
                     
            for(String temp : IPList)
            {
                Socket clientSocket = new Socket(temp, 8889);
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject(ArvoreDeArquivos.getListaArquivosNodal());
                clientSocket.close();
            }
            
            
            ServerSocket welcomeSocket = new ServerSocket(8889);
            Socket connectionSocket = welcomeSocket.accept(); 
            InputStream stream = connectionSocket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(stream);
            
            ArrayList<File> NovosArquivos = (ArrayList<File>) in.readObject();
            ArvoreDeArquivos.insertNewAgent(connectionSocket.getInetAddress().getHostAddress(), NovosArquivos);
            
            connectionSocket.close();
        }
        catch (ClassNotFoundException ce){
            
        }
        catch (IOException e) {

        }
    }
    
    private void ComputeBestLoad(FileDiscovery ArvoreDeArquivos){
        
    }

    @Override
    public void run() {
        try {
            //Keep a socket open to listen to all the UDP trafic that is destined for this port
            socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);
            //FileDiscovery ArvoreDeArquivos = new FileDiscovery();
            ArrayList<String> IPList;

            while (true) {

                IPList = DiscoverNetwork();//ArvoreDeArquivos);
                //DistributeFileData(ArvoreDeArquivos, IPList);
                //ComputeBestLoad(ArvoreDeArquivos);
               
                }
            }
         catch (IOException ex) {
            //Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public static void main1(String[] args) throws Exception {
        Thread discoveryThread = new Thread(TrabalhoTR2.getInstance());
        discoveryThread.start();
    }
}
