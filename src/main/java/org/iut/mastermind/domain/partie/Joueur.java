package org.iut.mastermind.domain.partie;

public class Joueur {

    private final String nom;

    // constructeur
    public Joueur(String nom) {
        this.nom = nom;
    }

    // getter nom joueur
    public String getNom() {
        return nom;
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if(o instanceof Joueur joueur) {
            return this.nom.equals(joueur.nom);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}
