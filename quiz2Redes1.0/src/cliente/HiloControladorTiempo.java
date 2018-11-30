package cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import streaming.CancionUDPCliente;

public class HiloControladorTiempo  extends Thread{

	private PanelClienteTiempo pt;
	private PanelClienteCaballos pd;
	private SSLClient principal;
	
	public HiloControladorTiempo(PanelClienteTiempo t, PanelClienteCaballos panelDibujo, SSLClient p) {
		principal =p ;
		pt =t;
		pd = panelDibujo;
	}
	
	
	public void run() {
		
		
		while (true) {
			MulticastSocket multicastSocket;
			try {
				multicastSocket = new MulticastSocket(9001);
				InetAddress inetAddress = InetAddress.getByName("229.5.6.7");
				multicastSocket.joinGroup(inetAddress);		
				
				byte[] buffer = new byte[10000];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				multicastSocket.receive(packet);
				
				
				String message= new String(packet.getData(), 0, packet.getLength());
				
				String[] m = message.split(",");
				
				boolean bandera = true;
				if(!m[1].equals("Preparación")&& bandera) {
					
					bandera = false;
					CancionUDPCliente c = new CancionUDPCliente();
					c.start();
					
					principal.getBtnApuesta().setEnabled(false);
					principal.getTextFiled().setEnabled(false);
					principal.getCbCaballos().setEnabled(false);
				}
				
				
				int ms = Integer.parseInt(m[0]);  
				int segs = ms%60; ms -= segs; ms /= 60;
				int mins = ms%60; ms -= mins; ms /= 60;
				
				
				
				
				pt.setMin("0"+mins);
				
				if(segs<10) {				
				pt.setSeg("0"+segs);}
				if(segs>=10) {
					pt.setSeg(""+segs);
				}
				
				pt.setEstado(m[1]);
				
				pd.setCoordenadasX(m[2].split("-"));
				
				pd.setPosiciones(m[3].split("-"));
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		
	}
	
}
