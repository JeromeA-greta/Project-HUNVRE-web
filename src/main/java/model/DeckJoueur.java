package model;

import java.util.ArrayList;
import java.util.List;

public class DeckJoueur extends ArrayList {
	
	private List<CarteJeu> deck;
	
	public DeckJoueur() {
	
			this.deck = new ArrayList<CarteJeu>();	
	}
	
	public ArrayList getListedeck() { 
		return (ArrayList) this.deck;
	}
	/*public CarteJeu getCartedejeubyid(int id) {
		Cartedejeu.getId();
	}*/
	
	public CarteJeu cherchercarte(int id) {
		for (CarteJeu carte : this.deck) {
			if (carte.getId() == id) {
				return carte;
			}
		}
		return null;
	}
	
	public void ajoutercarte(CarteJeu c) { // Surcharge de la méthode add() de ArrayList
		this.add(c);
	}

}
