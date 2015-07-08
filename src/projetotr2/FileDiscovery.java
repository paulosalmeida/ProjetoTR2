/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetotr2;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

/**
 *
 * @author JorgeGabriel
 */
public class FileDiscovery {

    /**
     * @param args the command line arguments
     */
    static String caminhoPadrao = "./JorgeAtrasado/";
    ArrayList<File> arquivosNodais;
    HashMap<String, ArrayList<File>> arquivosRede;

    FileDiscovery() {
        //Significa: Cria uma lista com todos os arquivos File dentro
        this.arquivosNodais = new ArrayList<File>(Arrays.asList(new File(caminhoPadrao).listFiles()));
    }

    public int getTamanhoListaArquivosNodal() {
        return new File(caminhoPadrao).listFiles().length;
    }

    public void setListaArquivosNodal() {
        this.arquivosNodais = new ArrayList<File>(Arrays.asList(new File(caminhoPadrao).listFiles()));
    }

    public ArrayList<File> getListaArquivosNodal() {
        return this.arquivosNodais;
    }

    public void insertNewAgent(String IPAddress, ArrayList<File> novosArquivos) {
        this.arquivosRede.replace(IPAddress, novosArquivos);
    }
    public void removeAgent(String IPAddress) {
        this.arquivosRede.remove(IPAddress);
    }
    public int getTotalAgents() {
       
        return this.arquivosRede.size();
    }

    public static void CriarPasta(String Caminho) {
        boolean success = new File("./JorgeAtrasado/" + Caminho).mkdir();
        if (!success) {
            System.out.println("Falha na criação da pasta");
        }

    }

}
