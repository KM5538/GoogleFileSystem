/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnachos.kern;

/**
 *
 * @author Neha
 */



import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jnachos.machine.*;

public class Master1 implements VoidFunctionPtr,Runnable 
{

	//static int id=0;
	Socket sock;

	public Master1() {
		Debug.print('t', "Entering ClientServer");
             //   System.out.println("Master1 called");
		NachosProcess p = new NachosProcess("Master1 as Server" + 1);
		p.fork(this, new Integer(1));
                

	}


	public Master1(Socket sock2, int i) {
		this.sock=sock2;
	}


	@Override
	public void call(Object pArg) {
		
 //System.out.println("Master1 Call called");
		//		do
		//		{
		//final String client4="10.1.85.189";
		//final String client3="10.1.204.166";

		int count=0;
	//	int count=1;

		try {
			ServerSocket sersock = new ServerSocket(1000); ///PORT OF MASTER. CHANGE IF REQUIRED
                        // System.out.println("Master try called");

			while(true)
			{
				Socket sock = sersock.accept( );    
                               //  System.out.println("Master1 client connected");
				SocketAddress clientip=sock.getRemoteSocketAddress();
				String clientipp=clientip.toString();
                               
				clientipp=clientipp.substring(1);
                                
				String client[]=new String[2];
				client=clientipp.split(":");
				clientipp=client[0];

				//System.out.println("Client ip: "+sock.getRemoteSocketAddress());
				//System.out.println("Client port: "+sock.getPort());


				//if(count==1)
				//{
                                        count += 1;
					Master1 cs=new Master1(sock,count);
					//JNachos.gfsClientThreadId.put(count,cs);


					Runnable runn=new Master1(sock,count);

					//count;

					Thread thread=new Thread(runn);

					thread.start();			
			}
                        

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void run() {
            MetaData map = new MetaData();
		try {


			String name = Thread.currentThread().getName();

			System.out.println("Client "+name+" accepted the connection");

			
			String array[] = new String[100];
			Master1 pp;
                        String ip;
                        String store="";
                        String port;
                        InputStream istream = sock.getInputStream();
                        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
                        String receiveMessage;// chunk_handler = null;
                       OutputStream ostream = sock.getOutputStream(); 
                                    PrintWriter pwrite = new PrintWriter(ostream, true);
			if((receiveMessage = receiveRead.readLine()) != null) { //receive from server
                                                        System.out.println( "receive message" + receiveMessage);
                                                        array = receiveMessage.split(" ");
                                                                                           
                         }
                        if(array[0].equals("Client"))
                        {
                             String fileName = "Data.txt";
                             String fileName2 = "Data2.txt";
                             String sendMessage;
                                    // This will reference one line at a time
                                    String line = null;
                                    String line2 = null;
                                   
                                    try {
                                       
                                        FileReader fileReader = new FileReader(fileName);
                                        FileReader fileReader2 = new FileReader(fileName2);
                                        
                                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                                        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
                                        String comp[] = new String[4];
                                        String comp2[] = new String[4];
                                        int loc=0;
                                         int loc2=0;
                                         
                                        while((line = bufferedReader.readLine()) != null) {
                                        int c=0,d=1;
                                        System.out.println(line + "\t" );
                                        comp = line.split(" "); 
                                        System.out.println("line split");
                                        
                                        System.out.println("comp[0] "+ comp[c] + "array[1] "+ array[d]);
                                        if((comp[c].equals(array[d])) == true) {
                                                                               c++; d++;
                                                                               System.out.println("comp[1] "+ comp[c] + "array[2] "+ array[d]);
                                                                               if((comp[c].equals(array[d]))== true) {
                                                                                                                     c++;
                                                                                                                     map.chunk_handle = comp[c];
                                                                                                                     System.out.println("chunk handler" + map.chunk_handle);
                                                                                                                     while((line2 = bufferedReader2.readLine()) != null) {
                                        
                                                                                                                                                                          System.out.println(line2 + "\t");
                                                                                                                                                                          comp2 = line2.split(" "); 
                                                                                                                                                                          System.out.println("chunk_handle" +comp2[2]);
                                                                                                                                                                          if((map.chunk_handle.equals(comp2[2])) == true) {

                                                                                                                                                                                                                            map.chunk_locations[loc2] = comp2[0] + ":" + comp2[1];
                                                                                                                                                                                                                            loc2++;
                                                                                                                                                                                                                          }
                                                                                                                                                                          } 
                                                                                                                     }                                                                    
                                                                               }
                                                                                           }   
                                       
                                        bufferedReader.close();         
                                        }
                                    catch(FileNotFoundException ex) {
                                                                    System.out.println("Unable to open file '" + fileName + "'");                
                                                                    }
                                    catch(IOException ex) {
                                                          System.out.println("Error reading file '" + fileName + "'");                  
                                                          }                                        
                                    sendMessage = map.chunk_handle + " ";
                                    //int flag = 0;
                                    //System.out.println("Handler sent" + sendMessage);
                                    for(int i=0; i<map.chunk_locations.length;i++) {
                                                                                   if(map.chunk_locations[i] != null) {
                                                                                                                      map.chunk_locations[i] += " ";
                                                                                                                      sendMessage += map.chunk_locations[i];  // keyboard reading
                                                                                                                      System.out.println("this "+sendMessage); 
                                                                                                                      }
                                                                                   else{
                                                                                       //flag = 1;
                                                                                       break;
                                                                                       }
                                                                                   }
                                    pwrite.println(sendMessage);       // sending to server
                                    pwrite.flush();  // flush the data
                                    System.out.println("Message Sent");
                                    System.out.println("this one "+sendMessage);                             
                                 
                        }
                        else
                        {
                            ip=array[0];
                            port=array[1];

                            //File logFile = new File("Data2.txt");
                             BufferedWriter writer = new BufferedWriter(new FileWriter("Data2.txt",true));


                            for(int i=2;i<array.length;i++){
                                                            store = ip + " " + port + " " + array[i] + '\n';
                                                            System.out.println( "store" + store);
                                                           // sw.println("Hello World");
                                                            //writer.write(store);  
                                                             writer.append(store);
                                                            // writer.append('\n');
                                                           }
                            writer.close();
                        }
                      //new Master();                                                                                      
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}