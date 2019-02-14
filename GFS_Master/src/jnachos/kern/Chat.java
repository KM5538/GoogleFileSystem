
package jnachos.kern;

/**
 *
 * @author Neha
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jnachos.machine.*;

public class Chat implements  VoidFunctionPtr
{   
  public Chat() {
		Debug.print('t', "Entering SimpleTest");
		NachosProcess p = new NachosProcess("forked process" + 1);
		p.fork(this, new Integer(1));
		}
  @Override
  public void call(Object pArg) {
                                Socket sock;
                                MetaData map = new MetaData();
                                try {         
                                    sock = new Socket("192.168.0.14", 3032);
                                    // reading from keyboard (keyRead object)
                                    BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
                                    // sending to client (pwrite object)
                                    OutputStream ostream = sock.getOutputStream(); 
                                    PrintWriter pwrite = new PrintWriter(ostream, true);
                                    // receiving from server ( receiveRead  object)
                                    InputStream istream = sock.getInputStream();
                                    BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
                                    String receiveMessage, sendMessage;  
                                    String array[] = new String[2];
                                    if((receiveMessage = receiveRead.readLine()) != null) { //receive from server
                                                                                          array = receiveMessage.split(" ");  
                                                                                          //System.out.println(receiveMessage); // displaying at DOS prompt
                                                                                          }  
                                    // The name of the file to open.
                                    String fileName = "Data.txt";
                                    // This will reference one line at a time
                                    String line = null;
                                    try {
                                        // FileReader reads text files in the default encoding.
                                        FileReader fileReader = new FileReader(fileName);
                                        // Always wrap FileReader in BufferedReader.
                                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                                        String comp[] = new String[4];
                                        int loc=0;
                                        while((line = bufferedReader.readLine()) != null) {
                                        int c=0;
                                        System.out.println(line + "\t" + c);
                                        comp = line.split(" "); 
                                        /*for(int i=0; i<comp.length;i++) {
                                                                        System.out.println("Printing Stored Data");
                                                                        System.out.println(comp[i]);
                                                                        }*/
                                        if((comp[c].equals(array[c])) == true) {
                                                                               c++;
                                                                               if((comp[c].equals(array[c]))== true) {
                                                                                                                     c++;
                                                                                                                     map.chunk_handle = comp[c];
                                                                                                                     c++;
                                                                                                                     //System.out.println("chunk handle" + map.chunk_handle);
                                                                                                                     map.chunk_locations[loc] = comp[c];
                                                                                                                     loc++;
                                                                                                                     }                                                                    
                                                                               }
                                                                                           }   
                                        // Always close files
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
                                    //System.out.println("this one "+sendMessage);

                                    }
                                catch (IOException ex) {
                                                       Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                                                       }
                                }  
}