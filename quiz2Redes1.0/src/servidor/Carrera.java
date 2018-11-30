package servidor;

import java.util.ArrayList;

public class Carrera {

	public static int DISTANCIA= 900;
	
	
	private String carreraId;
	
	private ArrayList<Apostador> caballos;
	
	private String ganador;
	
	public Carrera() {
		
		ganador = "";
		caballos = new ArrayList<Apostador>();
		for (int i = 0; i < 6; i++) {
			Apostador r = new Apostador("Caballo "+(i+1), 0);
			caballos.add(r);
		}	
		
		
	}

	
	
	public String getGanador() {
		return ganador;
	}





	public void setGanador(String ganador) {
		this.ganador = ganador;
	}





	public ArrayList<Apostador> getCaballos() {
		return caballos;
	}
		
	
	
	
	public void actualizarPosicion() {
		
		int[] dis = new int[6];
			
		for (int i = 0; i < dis.length; i++) {
			dis[i]=caballos.get(i).getDistanaciaRecorrida();
			if(ganador.equals("")&&caballos.get(i).getDistanaciaRecorrida()==DISTANCIA) {
				
			}
		}
		
		for (int x = 0; x < dis.length; x++) {
	        for (int i = 0; i < dis.length-x-1; i++) {
	            if(dis[i] < dis[i+1]){
	                int tmp = dis[i+1];
	                dis[i+1] = dis[i];
	                dis[i] = tmp;
	            }
	        }
	    }
		
		for (int i = 0; i < dis.length; i++) {
			
			for (int j = 0; j < dis.length; j++) {
				if(dis[i]==caballos.get(j).getDistanaciaRecorrida()) {
					caballos.get(j).setPosicion(i+1);
				}
			}
			
		}
		
		
	}
//	
//	public void actualizarPosicion() {
//		
//		String[] posiciones = new String[6];
//		String l = "";
//		for (int i = 0; i < posiciones.length; i++) {
//			posiciones[i]="";
//	l+=caballos.get(i).getDistanaciaRecorrida()+",";
//		}
//		
//		System.out.println(l);
//		
//		for (int j = 0; j < 6; j++) {
//			for (int i = j+1; i < caballos.size(); i++) {
//				
//				if(caballos.get(j).getDistanaciaRecorrida()<caballos.get(i).getDistanaciaRecorrida()) {
//					posiciones[j]= caballos.get(i).getId();
//					posiciones[i]= caballos.get(j).getId();
//				}else if(caballos.get(j).getDistanaciaRecorrida()>caballos.get(i).getDistanaciaRecorrida()) {
//					posiciones[i]= caballos.get(i).getId();
//					posiciones[j]= caballos.get(j).getId();
//				}
//			}	
//		}
//		
//		String po = "";
//		for (int i = 0; i < posiciones.length; i++) {
//		
//			for (int j = 0; j < posiciones.length; j++) {
//				if(posiciones[i].equals(caballos.get(j).getId())) {
//					caballos.get(j).setPosicion(i+1);
//				}
//			}
//			
//		}
//		System.out.println(po);
//	}
}
