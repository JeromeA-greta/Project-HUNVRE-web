package model;

public class CarteJeu {

		private int id;
		private int valeurbase;
		private String recto;
		private int verso;
		private String couleur;
		
		public CarteJeu(int id, int valeurbase, String recto, int verso, String couleur) {
			this.id = id;
			this.valeurbase = valeurbase;
			this.recto = recto;
			this.verso = verso;
			this.couleur = couleur;
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getValeurbase() {
			return valeurbase;
		}
		public void setValeur(int valeurbase) {
			this.valeurbase = valeurbase;
		}
		public String getRecto() {
			return recto;
		}
		public void setRecto(String recto) {
			this.recto = recto;
		}
		public int getVerso() {
			return verso;
		}
		public void Verso(int verso) {
			this.verso = verso;
		}
		public String getCouleur() {
			return couleur;
		}
		public void setCouleur(String couleur) {
			this.couleur = couleur;
		}
		/*public CarteJeu tirage() {
			
			
			return CarteJeu;
		}*/
}


