package fr.univlille.iut.s302.point.modele;

public enum Type {

    BUG("bug"),
    NORMAL("normal"),
    FLYING("flying"),
    GRASS("grass"),
    ELECTRIC("electric"),
    PSYCHIC("psychic"),
    POISON("poison"),
    STEEL("steel"),
    DRAGON("dragon"),
    WATER("water"),
    ROCK("rock"),
    ICE("ice"),
    GROUND("ground"),
    FIRE("fire"),
    FAIRY("fairy"),
    GHOST("ghost"),
    DARK("dark"),
    FIGHTING("fighting");

    private final String nom;

    Type(final String nom){
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
