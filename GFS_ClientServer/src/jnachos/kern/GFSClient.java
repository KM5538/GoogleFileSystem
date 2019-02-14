package jnachos.kern;


import java.io.*;
import java.net.*;
import java.util.Scanner;
public class GFSClient implements VoidFunctionPtr 
{

	//static int id=0;

	public GFSClient() {
		Debug.print('t', "Entering ClientServer");

		NachosProcess p = new NachosProcess("forked process" + 1);
		p.fork(this, new Integer(1));

	}


	/*	public GFSClient(Socket sock2, int i) {
		this.sock=sock2;
	}
	 */

	@Override
	public void call(Object pArg) {

		Socket sock;

		//int count=1;

		try {


			sock = new Socket("10.1.232.118",1000);
			String array[] = new String[100];
			String ipList[]=new String[100];
			int portList[]=new int[100];
			String temp[]=new String[100];

			BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
			OutputStream ostream = sock.getOutputStream(); 
			PrintWriter pwrite = new PrintWriter(ostream, true);
			String sendMessage="Client ";  
			System.out.print("Enter data to send to Master: ");
			sendMessage += keyRead.readLine(); 
			System.out.println();
			pwrite.println(sendMessage);             
			pwrite.flush();



			InputStream istream = sock.getInputStream();
			BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
			String receiveMessage;  
			if((receiveMessage=receiveRead.readLine()) != null) //receive from server
			{
				array=  receiveMessage.split(" ");  

			}  

			System.out.println("got this from master :-p ");
			for(int j=0; j<array.length;j++)
			{
				System.out.println(array[j]);
			}


			for(int i=1;i<array.length;i++)
			{
				temp=array[i].split(":");

				ipList[i-1]=temp[0];
				portList[i-1]=Integer.parseInt(temp[1]);
			}


			System.out.println("got this from splitting :-p ");
			for(int j=0; j<array.length-1;j++)
			{
				//if(ipList!=null)
				System.out.println(ipList[j]+" "+portList[j]);

			}



			System.out.println("Connecting to ChunkServer!");
			int length=ipList.length;
			//sock = new Socket(ipList[0],portList[0]);

		/*	for (int i=0;i<length;i++)
			{
				sock = new Socket(ipList[i],portList[i]);

				PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
				out.println("Checking Connection!");
				if (out.checkError()) 
				{	
					System.out.println("ERROR writing data to socket !!!: "+ipList[i]+":"+portList[i]);
					i++;
				}

				else
				{
					System.out.println("Connection exists!!!: "+ipList[i]+":"+portList[i]);
					break;
				}
			}
		 */



			//////////


			//System.out.println("inside client run! : " + id);
		//	boolean flag=false;
/*			int i=0;
			for ( i=0;i<length;i++)
			{
				sock = new Socket(ipList[i],portList[i]);
				break;
			//	try (Socket sock1 = new Socket(ipList[i],portList[i]))
			//	{
					//	System.out.println("Accepted connection : " + sock);
			//		flag=true;
			//		break;
			//	}
				catch(IOException e)
				{
					continue;
					//i++;
				}
			}
	*/		

			
			String FILE_TO_RECEIVE = array[0]+".txt";//"test1.txt";  // you may change this
			int FILE_SIZE = 6022386; 
			int bytesRead;
			int current = 0;
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;

			try
			{
					sock = new Socket(ipList[0],portList[0]);


		
				OutputStream ostream1 = sock.getOutputStream(); 
					PrintWriter pwrite1 = new PrintWriter(ostream1, true);
					String sendMessage1;  
					sendMessage1 = array[0];
					//	System.out.println("send: "+sendMessage1);
					pwrite1.println(sendMessage1);             
					//	pwrite.flush();
			/*		if (pwrite1.checkError()) 
					{	
						System.out.println("ERROR writing data to socket !!!: "+ipList[i]+":"+portList[i]);
						i++;
					}

					else*/
					{
						System.out.println("Connection exists!!!: "+ipList[0]+":"+portList[0]);
						//break;


						// receive file
						byte [] mybytearray  = new byte [FILE_SIZE];
						InputStream is = sock.getInputStream();
						fos = new FileOutputStream(FILE_TO_RECEIVE);
						bos = new BufferedOutputStream(fos);
						bytesRead = is.read(mybytearray,0,mybytearray.length);
						current = bytesRead;

						do {
							bytesRead =is.read(mybytearray, current, (mybytearray.length-current));
							if(bytesRead >= 0) current += bytesRead;
						} while(bytesRead < -1);

						bos.write(mybytearray, 0 , current);
						//    bos.flush();
						System.out.println("File " + FILE_TO_RECEIVE
								+ " downloaded (" + current + " bytes read)");
					}
				}
				finally {
					//System.out.println("finally");
					if (fos != null) fos.close();
					if (bos != null) bos.close();
					//        if (sock != null) sock.close();
				}

				////////
			}

		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}                        