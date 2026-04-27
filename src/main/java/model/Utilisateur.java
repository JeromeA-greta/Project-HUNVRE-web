package model;

public class Utilisateur {

    private String pseudo;
    private String mail;
    private String mdp;
    private DeckJoueur deck;
    private String role = "joueur";


    // 2026-04-13 - Vitally Lubin
    // Ajout de l'attribut score pour stocker le score de la partie en cours.
    // Cet attribut est alimenté par ZoneSeb via setScore() à chaque combinaison jouée.
    // Il est lu par EcranDeFin via getScore() pour afficher le score final.
    // Il est sauvegardé en BDD par ControleurEcranDeFin.sauvegarderScore() en fin de partie.
    private int score = 0;

    public Utilisateur(String pseudo, String mail, DeckJoueur deck, String role) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.deck = deck;
        this.role = role;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    // set et get pour mdp ?? -pauline
    // TODO : implémenter getter/setter pour mdp

    public DeckJoueur getDeck() {
        return deck;
    }

    public void setDeck(DeckJoueur deck) {
        this.deck = deck;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // 2026-04-13 - Vitally Lubin
    // Getter et setter pour le score
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void cumulscore (int score) { //Permet d'augmenter le score total du joueur à mesure que la partie avance dans le but de l'enregistrer en BDD à la fin
    	this.score = this.score + score;
    }
}
