package servidor;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.omg.Messaging.SyncScopeHelper;

import java.io.*;
public class HiloServidor extends Thread{
	Socket client;
	BufferedReader readerHS;
	PrintWriter writerHS;
	Carrera a;
	long time;
	
	public HiloServidor(Socket request, Carrera apostador, long startTime)
	{
		super();
		a=apostador;
		client = request;
		time = startTime;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 * Pay attention that the next method "run" has the business logic of the server 
	 */
	public void run()
	{
		try {
			readerHS = new BufferedReader(new InputStreamReader(client.getInputStream()));
			writerHS = new PrintWriter(client.getOutputStream(), true);
			
			String mensaje = readerHS.readLine();
			
			long star = System.currentTimeMillis();
			long end= System.currentTimeMillis();
			while(((end-star)+time)<SSLServerSocket.TIEMPO) {	
		
			// we are going to write the answer and send it to the client
				String[] lista= mensaje.split(",");
				
				for (int i = 0; i < a.getCaballos().size(); i++) {
					if(a.getCaballos().get(i).getId().equals(lista[0])) {
						a.getCaballos().get(i).setValor(Integer.parseInt(lista[1]));
					}
				}
			System.out.println("Apuesta recibida al " +lista[0]+ " por un valor de $"+lista[1]);
			writerHS.println(lista[0]+ ","+lista[1]);
			writerHS.flush();
			mensaje = readerHS.readLine();
			end= System.currentTimeMillis();
		
			}
			
			// we are going to close the streams and the socket associated to the request
			readerHS.close();
			writerHS.close();
			client.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}