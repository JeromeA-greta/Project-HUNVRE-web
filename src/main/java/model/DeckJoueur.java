package model;

import java.util.ArrayList;
import java.util.List;

public class DeckJoueur extends ArrayList<CarteJeu> {
	private static final long serialVersionUID = 1L;
	
	private List<CarteJeu> deck;
	
	public DeckJoueur() {
	
		this.deck = new ArrayList<CarteJeu>();	
		
	}
	
	public ArrayList getListedeck() { 
		return (ArrayList) deck;
	}

	public CarteJeu cherchercarte(int id, ArrayList<CarteJeu> deck) {
		for (CarteJeu carte : deck) {
			if (String.valueOf(id).equals(carte.getRecto())) {			
				return carte;
			}
		}
		return null;
	}
	
	public CarteJeu tiragecarte(int id, ArrayList<CarteJeu> deck) {
		
		return deck.get(id);
	}
	
	/*public String chercherRectoCarte() {
		String recto;
		
		
		return recto;
	}*/
	
	public void ajoutercarte(CarteJeu c) { // Surcharge de la méthode add() de ArrayList
		this.add(c);
	}

}
