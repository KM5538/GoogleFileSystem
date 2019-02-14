package jnachos.kern;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Neha
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CS1Client implements  VoidFunctionPtr
{   
	public CS1Client() {
		Debug.print('t', "Entering SimpleTest");
		NachosProcess p = new NachosProcess("ChunkServer4 as Client" + 1);
		p.fork(this, new Integer(1));
	}
	@Override
	public void call(Object pArg){

		Socket sock = null;
		try {
			sock = new Socket("10.1.232.118", 1000);
			System.out.println("Connecting..."); 
			
			File dirPath=new File("D:\\eclipse_jee\\ws1\\proj3CSServerClient");
			File files[] = null;
			String chunkFileName;
			String ip="10.1.71.127";
			String port="1004";
			String toSend=ip+" "+port+" "; 
			String chunkHandle[]=new String[2];
	        if(dirPath.isDirectory())
	        {
	            files = dirPath.listFiles();
	            for(File dirFiles:files)
	            {                
	                    if(dirFiles.getName().endsWith(".txt"))
	                    {
	                    	chunkFileName=dirFiles.getName();
	                 //   	System.out.println("file name"+chunkFileName);

	                    	chunkHandle=chunkFileName.split("\\.");
	                    	
	                 //   	System.out.println(chunkHandle.length);
	                    	toSend+=chunkHandle[0]+" ";
	                    }
	                
	            }

	        }
			
			
	      //  BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
			OutputStream ostream = sock.getOutputStream(); 
			PrintWriter pwrite = new PrintWriter(ostream, true);
			String sendMessage;  
			sendMessage = toSend; 
			System.out.println("sendmessage: "+sendMessage);
			pwrite.println(sendMessage);             
			pwrite.flush();
			
			new ChunkServer1();
						
			
		}
		catch (IOException ex) {
			Logger.getLogger(CS1Client.class.getName()).log(Level.SEVERE, null, ex);
		}       

	}
}