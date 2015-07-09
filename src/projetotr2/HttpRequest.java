/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetotr2;

import DataBase.*;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Paulo
 */
final class HttpRequest implements Runnable {
    final static String CRLF = "\r\n";
    Socket socket;
    
    // Constructor
    public HttpRequest(Socket socket) throws Exception {
	this.socket = socket;
    }
    
    // Implement the run() method of the Runnable interface.
    @Override
    public void run() {
	try {
	    processRequest();
	} catch (Exception e) {
            System.out.println("Erro! HttpRequest");
	    System.out.println(e);
	}
    }

    private void processRequest() throws Exception {
        String[] partes, partes2;
	// Get a reference to the socket's input and output streams.
	InputStream is = socket.getInputStream();
	DataOutputStream os = new DataOutputStream(socket.getOutputStream());
	
	// Set up input stream filters.
	BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Get the request line of the HTTP request message.
        String requestLine = br.readLine();

        // Extract the filename from the request line.
        StringTokenizer tokens = new StringTokenizer(requestLine);
        tokens.nextToken();  // skip over the method, which should be "GET"
        String fileName = tokens.nextToken();
        String camposGET=null, campo=null, username=null, password=null;
	
        // Prepend a "." so that file request is within the current directory.
        fileName = "." + fileName ;
        System.out.println(fileName);
        
        if(fileName.contains(Character.toString('?'))){
            partes = fileName.split("\\?");
            fileName = partes[0];
            camposGET = partes[1];
            System.out.println(fileName);
            System.out.println(camposGET);
        }
        
        if(fileName.equals("./")){
            fileName = "./home/logintr2.html";
        }
            
        if(fileName.equals("./tabb.html")){
            if(camposGET != null){
                partes = camposGET.split("\\&");
                String usernameCampo = partes[0];
                String passwordCampo = partes[1];
                partes2 = usernameCampo.split("\\=");
                username = partes2[0];
                partes2 = passwordCampo.split("\\=");
                password = partes2[0];
            }
        }
        
        if(fileName.equals("./Teste2.html")){
            ClientUDP cli = new ClientUDP(false);
            Thread t = new Thread(cli);
            fileName = "./Teste.html";
            
            t.start();
        }
        
        /*UserDataBase userDataBase = new UserDataBase();
        UserEntity user = new UserEntity();
        
        user.setUsername(username);
        user.setPassword(password);
        
        UserEntity DBuser = userDataBase.retrieve(user);
        if(DBuser.getPassword().equals(user.getPassword())){
            System.out.println("Login deu certo");
        }*/
	
	// Open the requested file.
        FileInputStream fis = null ;
        boolean fileExists = true ;
        try {
            System.out.println("Filename: " + fileName);
	    fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
	    fileExists = false ;
        }

	// Debug info for private use
	System.out.println("Incoming!!!");
	System.out.println(requestLine);
	String headerLine = null;
	while ((headerLine = br.readLine()).length() != 0) {
	    System.out.println(headerLine);
	}
	
	// Construct the response message.
        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;
        if (fileExists) {
	    statusLine = "HTTP/1.0 200 OK" + CRLF;
	    contentTypeLine = "Content-Type: " + 
		contentType(fileName) + CRLF;
        } else {
	    statusLine = "HTTP/1.0 404 Not Found" + CRLF;
	    contentTypeLine = "Content-Type: text/html" + CRLF;
	    entityBody = "<HTML>" + 
		"<HEAD><TITLE>Not Found</TITLE></HEAD>" +
		"<BODY><P>Você tentou acessar: "+fileName+"</P>"+
                "<P>404 - Not Found</P></BODY></HTML>";
        }
	// Send the status line.
        os.writeBytes(statusLine);

        // Send the content type line.
        os.writeBytes(contentTypeLine);

        // Send a blank line to indicate the end of the header lines.
        os.writeBytes(CRLF);

        // Send the entity body.
        if (fileExists) {
            System.out.println("# File exists");
	    sendBytes(fis, os);
	    fis.close();
        } else {
            System.out.println("# File NOT exists");
	    os.writeBytes(entityBody) ;
        }

        // Close streams and socket.
        os.close();
        br.close();
        socket.close();
    }

    private static void sendBytes(FileInputStream fis, 
				  OutputStream os) throws Exception {
	// Construct a 1K buffer to hold bytes on their way to the socket.
	byte[] buffer = new byte[1024];
	int bytes = 0;
	
	// Copy requested file into the socket's output stream.
	while ((bytes = fis.read(buffer)) != -1) {
	    os.write(buffer, 0, bytes);
	}
    }

    private static String contentType(String fileName) {
	if(fileName.endsWith(".htm") || fileName.endsWith(".html")) {
	    return "text/html";
	}
	if(fileName.endsWith(".ram") || fileName.endsWith(".ra")) {
	    return "audio/x-pn-realaudio";
	}
        if(fileName.endsWith(".css")) {
	    return "text/css";
	}
	return "application/octet-stream" ;
    }
}
