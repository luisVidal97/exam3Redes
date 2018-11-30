package cliente;
import java.io.*;


import java.net.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;

import org.omg.PortableServer.ServantLocatorPOA;

import streaming.AudioUDPClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SSLClient extends JFrame implements ItemListener,ActionListener{
	
	public static final String TRUSTTORE_LOCATION = "C:/Users/luisf/keystore.jks";

	
	public static String username;
	public static String  APUESTA = "Apostar";
	
	private String seleccionCaballo;
	
	private PanelClienteCaballos panelDibujo;
	private  PanelClienteTiempo panelTiempo;
	
	private JLabel lblCaballo;
	private JComboBox<String> cbCaballos ;
	private JLabel lblValorApuesta;
	private JTextField txtValorApuesta;
	private JButton btnApuesta;
	
	private PrintWriter out;
	private BufferedReader br;
	File f ;
	FileWriter wr;
	BufferedWriter bw;

	public SSLClient() {
		
		
	
		
		

		
		
		//-------------------------------------------------------------------------------------------------------------
		//Incializar componentes de flujo socket-----------------------------------------------------------------------
		//-------------------------------------------------------------------------------------------------------------
		System.setProperty("javax.net.ssl.trustStore", TRUSTTORE_LOCATION);
		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		
		Socket socket;
		
		
		try {
			socket = sf.createSocket("localhost", 8000);
			//flujo de salida(se escribe los datos a enviar)
			out = new PrintWriter(socket.getOutputStream(), true);
			//flujo de entrada(recibe la respuesta del servidor)
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		//-------------------------------------------------------------------------------------
		
		setTitle("Carrera de caballos ¡A otro nivel!");
		setSize(1000, 550);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);
		
		seleccionCaballo = "Caballo 1";
		
		//Panel Norte--------------------------------------------------------------------------
		
		
		//Panel Sur----------------------------------------------------------------------------
		JPanel auxPanel = new JPanel();
		auxPanel.setLayout(new GridLayout(1, 5));
		
		lblCaballo = new JLabel("               Caballo:");
		
		cbCaballos = new JComboBox<String>();
		cbCaballos.addItem("Caballo 1");
		cbCaballos.addItem("Caballo 2");
		cbCaballos.addItem("Caballo 3");
		cbCaballos.addItem("Caballo 4");
		cbCaballos.addItem("Caballo 5");
		cbCaballos.addItem("Caballo 6");
		cbCaballos.addItemListener(this);
		
		lblValorApuesta = new JLabel("                      Valor apuesta:");
		
		txtValorApuesta = new JTextField();
		
		btnApuesta = new JButton(APUESTA);
		btnApuesta.setActionCommand(APUESTA);
		btnApuesta.addActionListener(this);
		
		auxPanel.add(lblCaballo);
		auxPanel.add(cbCaballos);
		auxPanel.add(lblValorApuesta);
		auxPanel.add(txtValorApuesta);
		auxPanel.add(btnApuesta);
		//---------------------------------------------------------------------------------------
		
		panelDibujo = new PanelClienteCaballos();
		panelTiempo = new PanelClienteTiempo();
		panelTiempo.setPreferredSize(new Dimension(900, 50));;
		
		add(panelTiempo, BorderLayout.NORTH);
		add(panelDibujo,BorderLayout.CENTER);
		add(auxPanel, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		HiloControladorTiempo tiempo = new HiloControladorTiempo(panelTiempo, panelDibujo,this);
		tiempo.start();
	}
	
	
	public String getSeleccionCaballo() {
		return seleccionCaballo;
	}


	public void setSeleccionCaballo(String seleccionCaballo) {
		this.seleccionCaballo = seleccionCaballo;
	}

	

	public String getTxtValorApuesta() {
		return txtValorApuesta.getText();
	}


	public void setTxtValorApuesta(String txtValorApuesta) {
		this.txtValorApuesta.setText(txtValorApuesta);
	}

	
	
	public JComboBox<String> getCbCaballos() {
		return cbCaballos;
	}

	public JTextField getTextFiled() {
		return txtValorApuesta;
	}
	
	public JButton getBtnApuesta() {
		return btnApuesta;
	}


	public void setTxtValorApuesta(JTextField txtValorApuesta) {
		this.txtValorApuesta = txtValorApuesta;
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==cbCaballos) {
			seleccionCaballo=(String) cbCaballos.getSelectedItem();
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
		if(e.getActionCommand().equals(APUESTA)) {
		
			//----------------------------------------------------------------------------------------------
			try {
				Calendar date = Calendar.getInstance();
				
				
				String dia=Integer.toString(date.get(Calendar.DATE));
				String mes=Integer.toString(date.get(Calendar.MONTH)+1);
				String ano=Integer.toString(date.get(Calendar.YEAR));
				String hora=Integer.toString(date.get(Calendar.HOUR));
				String min=Integer.toString(date.get(Calendar.MINUTE));
				String seg=Integer.toString(date.get(Calendar.SECOND));
				String fecha = hora+":"+min+","+seg+"   "+dia+"/"+mes+"/"+ano;
				
				
					f = new File("pagina/username.txt");
					wr = new FileWriter(f,true);
					bw = new BufferedWriter(wr);
				
				
				wr.write(username+","+getTxtValorApuesta()+","+fecha+","+getSeleccionCaballo()+"\n");
				
				wr.close();
				bw.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
				out.println(getSeleccionCaballo()+","+getTxtValorApuesta());
				
				String[] c =  getSeleccionCaballo().split(" ");
				int ca = Integer.parseInt(c[1]);
				
				panelDibujo.setCaballoSeleccionado(ca);
				getCbCaballos().setEnabled(false);
				
				try {
					String l = br.readLine();
					System.out.println(l);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				
				setTxtValorApuesta("");
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		SSLClient client = new SSLClient();
		client.setVisible(true);
		
		String cliente = JOptionPane.showInputDialog(null,"Username:","Login");
		username = cliente;
		
		
		AudioUDPClient audioCliente = new AudioUDPClient();
		audioCliente.start();
		
	}
	
	
	
	
}
