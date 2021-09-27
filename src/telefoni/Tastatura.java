package telefoni;

import java.awt.*;
import java.util.ArrayList;

public class Tastatura extends Panel implements Runnable {
	
	private enum Rezim {BROJ, TEKST};
	
	private static final int brRedova = 4, brKolona = 3;
	private static final int vremeCekanja = 1000;
	private Thread nit = new Thread(this);
	Label broj = new Label(""), ime = new Label("");
	private Rezim rezim = Rezim.BROJ;
	Panel panelTasteri = new Panel(new GridLayout(brRedova, brKolona));
	private ArrayList<Button> tasteri = new ArrayList<>();
	private boolean promena, cekaj;
	private int pocetak, kraj = 1;
	private String slovo = "";
	private int cnt = 0;
	
	private synchronized void kreni() {
		promena = true;
		notify();
	}
	public void prekini() {
		if (nit != null)
			nit.interrupt();
	}
	
	private void azurirajTastere() {
		if (rezim == Rezim.BROJ) {
			for (int i = 0; i<9; i++) {
				tasteri.get(i).setLabel("" + (i + 1));
				tasteri.get(i).setFont(new Font(null, Font.BOLD, 22));
			}
			tasteri.get(9).setLabel("*");
			tasteri.get(9).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(10).setLabel("0");
			tasteri.get(10).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(11).setLabel("+");
			tasteri.get(11).setFont(new Font(null, Font.BOLD, 22));
		}
		else if (rezim == Rezim.TEKST) {
			tasteri.get(0).setLabel("");
			tasteri.get(1).setLabel("ABC");
			tasteri.get(2).setLabel("DEF");
			tasteri.get(3).setLabel("GHI");
			tasteri.get(4).setLabel("JKL");
			tasteri.get(5).setLabel("MNO");
			tasteri.get(6).setLabel("PQRS");
			tasteri.get(7).setLabel("TUV");
			tasteri.get(8).setLabel("WXYZ");
			tasteri.get(9).setLabel("");
			tasteri.get(10).setLabel("_");
			tasteri.get(11).setLabel("");
			tasteri.get(0).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(1).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(2).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(3).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(4).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(5).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(6).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(7).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(8).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(9).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(10).setFont(new Font(null, Font.BOLD, 22));
			tasteri.get(11).setFont(new Font(null, Font.BOLD, 22));
		}
	}
	
	private void dodajTastere() {
		for (Button b : tasteri) {
			panelTasteri.add(b);
		}
		this.add(panelTasteri);
	}
	private void dodajSever() {
		Panel sever = new Panel(new GridLayout(2, 1));
		sever.add(broj);
		sever.add(ime);
		this.add(sever);
	}
	
	private synchronized void dodajOsluskivace() {
		for (Button b : tasteri) {
			b.addActionListener(ae -> {
				if (rezim == Rezim.BROJ) {
					broj.setText(broj.getText() + b.getLabel());
				}
				else if (rezim == Rezim.TEKST) {
					if (b.getLabel().length() == 1) {
						ime.setText(ime.getText() + b.getLabel());
						slovo = b.getLabel();
					}
					else {
						if (ime.getText().length() >= 1 && !(b.getLabel().contains(ime.getText().substring(ime.getText().length()-1)))
								&& cnt >= 1) {
							cnt = 0;
							cekaj = true;
							pocetak = 0;
							kraj = 1;
						}
						cnt++;
						if (cnt > 1) {
							pocetak++;
							kraj++;
						}
						if (b.getLabel().length() == 3 && pocetak == 3) {
							pocetak = 0;
							kraj = 1;
						}
						else if (b.getLabel().length() == 4 && pocetak == 4) {
							pocetak = 0;
							kraj = 1;
						}
						slovo = b.getLabel().substring(pocetak, kraj);
						if (cnt > 1) {
							ime.setText(ime.getText().substring(0, ime.getText().length()-1) + slovo);
						}
						else if (ime.getText().equals("")) {
							ime.setText(slovo);
						}
						else {
							ime.setText(ime.getText() + slovo);
						}
					}
					if (promena == false) {
						this.kreni();
					}
					else if (promena == true && cnt > 1) {
						cekaj = true;
					}
				}
			});
		}
	}
	
	private synchronized void azuriraj() {
		if (ime.getText().length() == 1 && !ime.getText().equals("")) {
			ime.setText(slovo);
		}
		else if (ime.getText().length() > 1) {
			ime.setText(ime.getText().substring(0, ime.getText().length()-1) + slovo);
		}
		cnt = 0;
		promena = false;
		cekaj = false;
		pocetak = 0;
		kraj = 1;
	}

	public Tastatura(String natpis) {
		broj.setText(natpis);
		this.dodajSever();
		for (int i = 0; i < brRedova*brKolona; i++) {
			tasteri.add(new Button());
		}
		this.dodajTastere();
		this.azurirajTastere();
		this.dodajOsluskivace();
		nit.start();
	}
	
	public void promeniNatpis(String natpis) {
		broj.setText(natpis);
	}
	
	public void promeniRezim() {
		if (rezim == Rezim.BROJ) {
			rezim = Rezim.TEKST;
			this.azurirajTastere();
		}
		else if (rezim == Rezim.TEKST) {
			rezim = Rezim.BROJ;
			this.azurirajTastere();
		}
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized (this) {
					while(!promena)
						wait();
				}
				Thread.sleep(vremeCekanja);
				if (cekaj) {
					cekaj = false;
					continue;
				}
				this.azuriraj();
			}
		} catch (InterruptedException e) {}
	}
}
