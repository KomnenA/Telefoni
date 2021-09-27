package telefoni;

import java.awt.*;

public class Telefon extends Panel {
	
	private Broj broj;
	private Color boja;
	private Imenik imenik;
	Tastatura tastatura;
	private Button dodaj = new Button("Dodaj kontakt"), iskljuci = new Button("Iskljuci telefon");
	
	private synchronized void dodajOsluskivace() {
		dodaj.addActionListener(ae -> {
			if (tastatura.ime.getText().equals("")) {
				tastatura.promeniRezim();
			}
			else {
				imenik.add(new Kontakt(tastatura.ime.getText(), new Broj(tastatura.broj.getText())));
				imenik.revalidate();
				tastatura.ime.setText("");
				tastatura.broj.setText("");
				tastatura.promeniRezim();
			}
		});
		
		iskljuci.addActionListener(ae -> {
			if (iskljuci.getLabel().equals("Iskljuci telefon")) {
				iskljuci.setLabel("Ukljuci telefon");
				iskljuci.setBackground(Color.RED);
			}
			else if (iskljuci.getLabel().equals("Ukljuci telefon")) {
				iskljuci.setLabel("Iskljuci telefon");
				iskljuci.setBackground(Color.LIGHT_GRAY);
			}
		});
	}
	
	private void dodajSever() {
		tastatura.broj.setBackground(boja);
		tastatura.ime.setBackground(boja);
		Panel sever = new Panel(new GridLayout(2, 1));
		sever.add(tastatura.broj);
		sever.add(tastatura.ime);
		this.add(sever, BorderLayout.NORTH);
	}	
	private void dodajCentar() {
		Panel centar = new Panel(new GridLayout(2, 1));
		imenik.setBackground(boja);
		centar.add(tastatura.panelTasteri);
		centar.add(imenik);
		this.add(centar, BorderLayout.CENTER);
	}
	private void dodajJug() {
		Panel dugme = new Panel(new GridLayout(1, 2));
		dugme.add(dodaj);
		dugme.add(iskljuci);
		Panel jug = new Panel(new GridLayout(2, 1));
		jug.add(dugme);
		Label brText = new Label(broj.toString());
		brText.setFont(new Font(null, Font.BOLD, 16));
		brText.setAlignment(Label.CENTER);
		jug.add(brText);
		this.add(jug, BorderLayout.SOUTH);
	}
	
	public Telefon(Broj broj, Color boja) {
		this.broj = broj;
		this.boja = boja;
		imenik = new Imenik();
		tastatura = new Tastatura("");
		this.setBackground(boja);
		imenik.setBackground(boja);
		iskljuci.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new BorderLayout());
		this.dodajSever();
		this.dodajCentar();
		this.dodajJug();
		this.dodajOsluskivace();
	}
	
	public Broj dohvBroj() {
		return broj;
	}
}
