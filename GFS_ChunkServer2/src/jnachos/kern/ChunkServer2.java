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

public class ChunkServer2 implements  VoidFunctionPtr
{   
	public ChunkServer2() {
		System.out.println("ChunkServer 2 started as Server!");
		Debug.print('t', "Entering SimpleTest");
		NachosProcess p = new NachosProcess("forked process" + 1);
		p.fork(this, new Integer(1));
	}
	@Override
	public void call(Object pArg){
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		Socket sock = null;
		try {
			
			ServerSocket sersock = new ServerSocket(1004);
			
			sock = sersock.accept( );       

			/*InputStream istream = sock.getInputStream();
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
			String receiveMessage, chunk_handler = null;
			if((receiveMessage = receiveRead.readLine()) != null) { //receive from client
				chunk_handler = receiveMessage;  
			} 
			System.out.println("chunk_handler "+chunk_handler);
			*/
			InputStream istream1 = sock.getInputStream();
			BufferedReader receiveRead1 = new BufferedReader(new InputStreamReader(istream1));
			String receiveMessage1, chunk_handler1 = null;
			if((receiveMessage1 = receiveRead1.readLine()) != null) { 
				System.out.println("receiveMessage1 "+receiveMessage1);
				chunk_handler1 = receiveMessage1;  
			} 
			System.out.println("chunk_handler1 "+chunk_handler1);

			// send file
			String FILE_TO_SEND = chunk_handler1 +".txt";
			File myFile = new File (FILE_TO_SEND);
			byte [] mybytearray  = new byte [(int)myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);
			os = sock.getOutputStream();
			System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
			os.write(mybytearray,0,mybytearray.length);
			os.flush();
			System.out.println("Done.");
		}
		catch (IOException ex) {
			Logger.getLogger(ChunkServer2.class.getName()).log(Level.SEVERE, null, ex);
		}       
		finally {
				try {
					if (bis != null) bis.close();
					if (os != null) os.close();
					//           if (sock!=null) sock.close();
				}
				catch (IOException ex) {
					Logger.getLogger(ChunkServer2.class.getName()).log(Level.SEVERE, null, ex);
				}
		}          

	}
}