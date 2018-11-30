package cliente;

import javax.swing.*;

import java.awt.*;

public class PanelClienteCaballos extends JPanel{

	private int[] coordenadasX;
	private String[] posiciones;
	private int caballoSeleccionado;
	
	public PanelClienteCaballos() {
	
		caballoSeleccionado=0;
		
		setLayout(null);
		
		ImageIcon c1 = new ImageIcon("../quiz2Redes1.0/img/C1.jpg");
		ImageIcon c2 = new ImageIcon("../quiz2Redes1.0/img/C2.jpg");
		ImageIcon c3 = new ImageIcon("../quiz2Redes1.0/img/C3.jpg");
		ImageIcon c4 = new ImageIcon("../quiz2Redes1.0/img/C4.jpg");
		ImageIcon c5 = new ImageIcon("../quiz2Redes1.0/img/C5.jpg");
		ImageIcon c6 = new ImageIcon("../quiz2Redes1.0/img/C6.jpg");
		
		JLabel lc1 = new JLabel(c1);
		lc1.setBounds(0, 0, 80, 60);
		add(lc1);
		
		JLabel lc2 = new JLabel(c2);
		lc2.setBounds(0, 70, 80, 60);
		add(lc2);
		
		JLabel lc3 = new JLabel(c3);
		lc3.setBounds(0, 140, 80, 60);
		add(lc3);
		
		JLabel lc4 = new JLabel(c4);
		lc4.setBounds(0, 210, 80, 60);
		add(lc4);
		
		JLabel lc5 = new JLabel(c5);
		lc5.setBounds(0, 280, 80, 60);
		add(lc5);
		
		JLabel lc6 = new JLabel(c6);
		lc6.setBounds(0, 350, 80, 60);
		add(lc6);
		
		coordenadasX = new int[6];
		posiciones = new String[6];
		
	}
	
	
	public int getCaballoSeleccionado() {
		return caballoSeleccionado;
	}

	public void setCaballoSeleccionado(int caballoSeleccionado) {
		this.caballoSeleccionado = caballoSeleccionado;
	}

	public int[] getCoordenadasX() {
		return coordenadasX;
	}

	public void setCoordenadasX(String[] c) {
		
		for (int i = 0; i < coordenadasX.length; i++) {
			coordenadasX[i] = Integer.parseInt(c[i]);
		}
		
	}

	public void setPosiciones(String[] split) {
		// TODO Auto-generated method stub
		
		posiciones = split;
	}


	public void paint(Graphics g) {
		
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		
		g2.drawLine(800, 0, 800, 425);
		
		ImageIcon img = new ImageIcon("../quiz2Redes1.0/img/gifCarrera.gif");
		
	
		for (int i = 0; i < coordenadasX.length; i++) {
			
			g2.drawImage(img.getImage(), coordenadasX[i]+90, (i*70),140,50,null);
	
			
			
			if((i+1)==caballoSeleccionado) {
				g2.setFont(new Font("arial", Font.BOLD,14));
				g2.setColor(Color.RED);
				g2.drawString("Caballo "+(i+1), coordenadasX[i]+90,(i*70)+65);
				g2.drawLine(120, (i*70)+50, coordenadasX[i]+220, (i*70)+50);
				
				g2.drawString("Pos: "+posiciones[i], coordenadasX[i]+160,(i*70)+65);
				
//				g2.setFont(new Font("arial", Font.BOLD,25));
//				g2.drawString("Pos: "+posiciones[i],830, (i*65));
			}else {
				g2.setColor(Color.BLACK);
				g2.setFont(new Font("arial", Font.ITALIC,11));
				g2.drawString("Caballo "+(i+1), coordenadasX[i]+90,(i*70)+65);
				g2.drawLine(120, (i*70)+50, coordenadasX[i]+220, (i*70)+50);
				
				g2.drawString("Pos: "+posiciones[i], coordenadasX[i]+160,(i*70)+65);
				
//				g2.setFont(new Font("arial", Font.BOLD,25));
//				g2.drawString("Pos: "+posiciones[i], 830, (i*65));
			}
		}
		
		
		repaint();
		validate();
	}


	
}
