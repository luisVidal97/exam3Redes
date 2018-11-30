package servidor;

import java.io.*;

import java.net.*;
import java.util.ArrayList;

import javax.net.ssl.SSLServerSocketFactory;

import streaming.AudioUDPServer;
import web.ServerDataBase;

public class SSLServerSocket {

	public static final String KEYSTORE_LOCATION = "C:/Users/luisf/keystore.jks";
	public static final String KEYSTORE_PASSWORD = "123456";
	public static final long TIEMPO = 60000;
	
	
	public static final long PRETIEMPO = 60000;
	

	public static void main(String[] args) {

		System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		
		
		
		try {
			ServerSocket serverSocket = ssf.createServerSocket(8000);
			long end= System.currentTimeMillis();
			long start= System.currentTimeMillis();
		
//			ArrayList<Apostador> apostador = new ArrayList<Apostador>();
//			for (int i = 0; i < 6; i++) {
//				Apostador r = new Apostador("Caballo "+(i+1), 0);
//				apostador.add(r);
//			}
			ServerDataBase bd= new ServerDataBase();
			bd.consultarCarrera();
			Carrera carrera = new Carrera();
			
			MulticastSocket multisocketTiempo = new MulticastSocket();
			InetAddress inetAddress = InetAddress.getByName("229.5.6.7");
			multisocketTiempo.joinGroup(inetAddress);
			
			AudioUDPServer audioServidor = new AudioUDPServer();
			audioServidor.start();
			
			
			boolean bandera = true;
			
			while((end-start)<(TIEMPO+PRETIEMPO)) {
				
				if((end-start)<PRETIEMPO) {
				Socket socket = serverSocket.accept();
				
				long t = (int)((PRETIEMPO-(end-start))/1000);
				HiloServidor thread= new HiloServidor(socket, carrera, end-start);
				thread.start();
				
				if(bandera) {
				HiloTiempoServidor threadData = new HiloTiempoServidor(multisocketTiempo,inetAddress,t,carrera);
				threadData.start();
				bandera = false;
				}
			
				end = System.currentTimeMillis();
				}
				
				//w3scholls
					
				
			}
			
			
			serverSocket.close();
			
			System.out.println("-----------------------------------------------------------------------------------------");
			System.out.println("SSLServerSocket Teminated");
			
			for (int i = 0; i < carrera.getCaballos().size(); i++) {
				System.out.println("Para el caballo C"+(i+1)+" se ha apostado $"+ carrera.getCaballos().get(i).getValor());
			}
			System.exit(0);
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}