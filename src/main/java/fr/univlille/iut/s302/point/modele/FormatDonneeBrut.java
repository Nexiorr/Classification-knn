package fr.univlille.iut.s302.point.modele;

public class FormatDonneeBrut {
    // Ajouter des propriétés génériques, si nécessaire, ou laisser vide si c'est une classe de base abstraite

    // Méthode pour convertir un FormatDonneeBrut en DonneesBrutIris
    public DonneesBrutIris toIris() {
        return new DonneesBrutIris(); // Retourner une instance de DonneesBrutIris après avoir défini ses valeurs
    }

    // Si nécessaire, ajouter une méthode toPokemon() pour les données Pokémon
    public DonneesBrutPokemon toPokemon() {
        return new DonneesBrutPokemon();
    }
}
