package web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ServerDataBase {

	
	File f ;
	
	FileReader rr;
	BufferedReader br;
	
	FileWriter wr;
	BufferedWriter bw;
	
	
		public ServerDataBase() {
			
			try {			
				f = new File("pagina/username.txt");
		
				
				
				wr = new FileWriter(f,true);
				bw = new BufferedWriter(wr);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			wr.write(username+","+getTxtValorApuesta()+","+fecha+","+getSeleccionCaballo()+"\n");
						
		}
		
		
		
		public ArrayList<String> listaInfo(String username) {
			
			ArrayList<String> list = new ArrayList<String>();
			
			try {
				rr = new FileReader(f);
				br = new BufferedReader(rr);
				
				String user = "";
				user= br.readLine();
				
				while(user!=null) {
					System.out.println(user);
					
					String id= user.split(",")[0];
			
					if(id.equals(username)) {
						list.add(user);
					}
					user= br.readLine();
				}
				
				br.close();
				rr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
			return list;
		}
		
		
	public boolean consultarID(String username) throws IOException {
		
		boolean b = false;
		try {
			
			rr = new FileReader(f);
			br = new BufferedReader(rr);
			
			
			String user = "";
			user= br.readLine();
			
			while(user!=null) {
				System.out.println(user);
				
				String id= user.split(",")[0];
		
				if(id.equals(username)) {
					b=true;
				}
				user= br.readLine();
			}
			
			br.close();
			rr.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
		
	}



	public void consultarCarrera() {
		// TODO Auto-generated method stub
		
	}
	

}
