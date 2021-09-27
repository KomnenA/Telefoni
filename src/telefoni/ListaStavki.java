package telefoni;

import java.awt.*;
import java.util.ArrayList;

public class ListaStavki extends Panel {
	
	protected ArrayList<Stavka> stavke;
	
	public ListaStavki() {
		stavke = new ArrayList<>();
		this.setLayout(new GridLayout(5, 1));
	}

	public void dodajStavku(Stavka s) {
		stavke.add(s);
		this.add(s);
	}
}
