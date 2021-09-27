package telefoni;

public class Broj {
	
	private int kodDrzave, pozivniBr, brPretpl;

	public Broj(int kodDrzave, int pozivniBr, int brPretpl) {
		this.kodDrzave = kodDrzave;
		this.pozivniBr = pozivniBr;
		this.brPretpl = brPretpl;
	}
	public Broj(String broj) {
		this.kodDrzave = Integer.parseInt(broj.substring(1, 4));
		this.pozivniBr = Integer.parseInt(broj.substring(4, 6));
		this.brPretpl = Integer.parseInt(broj.substring(6));
	}

	public static boolean istaDrzava(Broj b1, Broj b2) {
		return b1.kodDrzave == b2.kodDrzave;
	}
	
	public static boolean istaMreza(Broj b1, Broj b2) {
		return Broj.istaDrzava(b1, b2) && b1.pozivniBr == b2.pozivniBr;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Broj))
			return false;
		return Broj.istaMreza(this, (Broj)o) && this.brPretpl == ((Broj)o).brPretpl;
	}
	
	@Override
	public String toString() {
		return "+" + String.format("%03d", kodDrzave) + " " + String.format("%02d", pozivniBr)
		+ " " + String.format("%02d", brPretpl);
	}
}
