package telefoni;

public class Imenik extends ListaStavki {
	
	@Override
	public void dodajStavku(Stavka s) {
		if (!(s instanceof Kontakt))
			return;
		super.dodajStavku(s);
	}
	
	public String dovhIme(Broj b) throws GNePostoji {
		for (Stavka s : stavke) {
			if (((Kontakt)s).tekst.getText().equals(b.toString()))
				return ((Kontakt)s).naslov.getText();
		}
		throw new GNePostoji();
	}
	
	public Broj dohvBroj(String ime) throws GNePostoji {
		for (Stavka s : stavke) {
			if (((Kontakt)s).naslov.getText().equals(ime))
				return new Broj(((Kontakt)s).tekst.getText());
		}
		throw new GNePostoji();
	}
}
