package fr.univlille.iut.s302.point.modele;

/**
 * L'énumération Fleur représente les trois variétés de fleurs utilisés dans les données d'iris.
 * Chaque type de fleur a un nom associé.
 */
public enum Fleur{

    SETOSA("Setosa"),
    VERSICOLOR("Versicolor"),
    VIRGINICA("Virginica");

    private final String nom;

    Fleur(final String nom){
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }

    public String getNom() {
        return nom;
    }
}