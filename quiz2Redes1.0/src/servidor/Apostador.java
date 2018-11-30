package servidor;


import java.util.ArrayList;
import java.util.Hashtable;

public class Apostador {

	private String id;
	private int valor;
	private int distanaciaRecorrida;
	private int posicion;
	
	public Apostador(String i, int valor) {
		
	id=i;
	this.valor=valor;
	distanaciaRecorrida=0;
	posicion = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor += valor;
	}

	public int getDistanaciaRecorrida() {
		return distanaciaRecorrida;
	}

	public void setDistanaciaRecorrida(int distanaciaRecorrida) {
		this.distanaciaRecorrida += distanaciaRecorrida;
	}
	
	
	
}
