package telefoni;

import java.awt.*;

public class Stavka extends Panel {

	Label naslov = new Label(), tekst = new Label();

	public Stavka(String naslov, String tekst) {
		this.setLayout(new GridLayout(2, 1));
		this.naslov.setFont(new Font(null, Font.BOLD, 14));
		this.naslov.setText(naslov);
		this.tekst.setText(tekst);
		this.add(this.naslov);
		this.add(this.tekst);
	}
	
	public void promeniNaslov(String naslov) {
		this.naslov.setText(naslov);
	}
}
