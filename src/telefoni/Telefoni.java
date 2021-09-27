package telefoni;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Telefoni extends Frame {
	
	private ArrayList<Telefon> telefoni = new ArrayList<>();
	
	private void dodajTelefone() {
		this.setLayout(new GridLayout(1, 2));
		this.add(telefoni.get(0));
		this.add(telefoni.get(1));
	}
	
	private void dodajOsluskivace() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				for (Telefon t : telefoni) {
					t.tastatura.prekini();
				}
				dispose();
			}
		});
	}
	
	public Telefoni() {
		this.setBounds(600, 300, 500, 500);
		
		this.setResizable(false);
		
		this.setTitle("Telefoni");
		
		telefoni.add(new Telefon(new Broj("+3816311"), Color.GREEN));
		telefoni.add(new Telefon(new Broj("+3876433"), Color.YELLOW));
		
		this.dodajTelefone();
		this.dodajOsluskivace();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Telefoni();
	}
}
