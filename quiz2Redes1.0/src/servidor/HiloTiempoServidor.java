package servidor;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import streaming.CancionUDPServer;

public class HiloTiempoServidor extends Thread {

	private MulticastSocket sender;
	private long tiempo;
	private InetAddress host;
	private Carrera carrera;
	

	
	public HiloTiempoServidor(MulticastSocket dataSocket, InetAddress host, long time, Carrera carrera) {
		
		sender= dataSocket;
		tiempo = time;
		this.host = host;
		this.carrera = carrera;
	}

	public void preparacion() {
		
		
		while(tiempo>=0) {
			String time = (tiempo+",Preparación,0-0-0-0-0-0,0-0-0-0-0-0");

			byte[] buffer = time.getBytes();

			try {
				DatagramPacket datagrama= new DatagramPacket(buffer, buffer.length,host,9001);
				sender.send(datagrama);
				sleep(1000);
				tiempo=tiempo-1;
			}catch(Exception e) {
				e.printStackTrace();
			}

		}

	}
	
	
	public void arranco() {
		
		tiempo = (int)(SSLServerSocket.TIEMPO/1000);
		
		CancionUDPServer cancion = new CancionUDPServer();
		cancion.start();
		
		while(tiempo>=0){
			
			String e = ",";
			String p = "";
			for (int i = 0; i < carrera.getCaballos().size(); i++) {
				int numero = (int) (Math.random() * 5) + 1;
				carrera.getCaballos().get(i).setDistanaciaRecorrida(numero);
				if(i==carrera.getCaballos().size()-1) {
				e+=(carrera.getCaballos().get(i).getDistanaciaRecorrida()+"");
				
				carrera.actualizarPosicion();
				p+=(carrera.getCaballos().get(i).getPosicion()+"");
				}else {
					e+=(carrera.getCaballos().get(i).getDistanaciaRecorrida()+"-");
					
					carrera.actualizarPosicion();
					p+=(carrera.getCaballos().get(i).getPosicion()+"-");
					
				}	
			}
			
			String time = (tiempo+", ¡Arrancó!")+e+","+p;
			byte[] buffer = time.getBytes();
			try {
				DatagramPacket datagrama= new DatagramPacket(buffer, buffer.length,host,9001);
				sender.send(datagrama);
				sleep(1000);
				tiempo=tiempo-1;
			}catch(Exception i) {
				i.printStackTrace();
			}
		}
		
	}
	

	public void run(){

		
		//--------------------------------------------------------------------------------------
		//antes de arrancar la carrera
		//--------------------------------------------------------------------------------------
		preparacion();
		
		//--------------------------------------------------------------------------------------
		//cuando la carrera arranca
		//--------------------------------------------------------------------------------------
		arranco();

		//--------------------------------------------------------------------------------------
		//cuando la carrera arranca
		//--------------------------------------------------------------------------------------
		finalizo();
	}

	private void finalizo() {
		// TODO Auto-generated method stub
		String e = ",";
		String p = "";
		for (int i = 0; i < carrera.getCaballos().size(); i++) {
			if(carrera.getGanador().equals("")&&carrera.getCaballos().get(i).getPosicion()==1) {
				carrera.setGanador(carrera.getCaballos().get(i).getId());
			}
			
			int numero = (int) (Math.random() * 5) + 1;
			carrera.getCaballos().get(i).setDistanaciaRecorrida(numero);
			if(i==carrera.getCaballos().size()-1) {
			e+=(carrera.getCaballos().get(i).getDistanaciaRecorrida()+"");
			
			carrera.actualizarPosicion();
			p+=(carrera.getCaballos().get(i).getPosicion()+"");
			}else {
				e+=(carrera.getCaballos().get(i).getDistanaciaRecorrida()+"-");
				
				carrera.actualizarPosicion();
				p+=(carrera.getCaballos().get(i).getPosicion()+"-");
				
			}	
			
		}
		
		String time = (tiempo+", Finalizo")+e+","+p;

		byte[] buffer = time.getBytes();

		try {
			DatagramPacket datagrama= new DatagramPacket(buffer, buffer.length,host,9001);
			sender.send(datagrama);
			sleep(1000);
			//tiempo=tiempo-1;
			
			
		}catch(Exception i) {
			i.printStackTrace();
		}

	}


	
}
