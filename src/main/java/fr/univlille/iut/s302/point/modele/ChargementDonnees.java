package fr.univlille.iut.s302.point.modele;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.univlille.iut.s302.utils.Observable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ChargementDonnees extends Observable {

    /**
     * La methode charger permet de retourner une list de FormatDonneeBrut delimité par des ','
     *
     * @param fileName le chemin vers le fichier
     * @return retourne une list de FormatDonneeBrut
     * @see List<FormatDonneeBrut>
     */
    public static void charger(String fileName, ChargementDonnees observableInstance, Class<?> objet) throws IOException {
        System.out.println("charger");
        System.out.println(objet.getName());
        if (objet.getName().equals(Iris.class.getName())) {
            System.out.println("dans Iris :/");
            List<DonneesBrutIris> donneesBrutes = new CsvToBeanBuilder<DonneesBrutIris>(Files.newBufferedReader(Paths.get(fileName)))
                    .withSeparator(',')
                    .withType(DonneesBrutIris.class)
                    .build()
                    .parse();
            for (DonneesBrutIris d : donneesBrutes) {
                Iris iris = transformerIris(d);
                observableInstance.notifyObservers(iris);
            }
        } else if (objet.getName().equals(Pokemon.class.getName())) {
            System.out.println("dans Pokemon :)");
            List<DonneesBrutPokemon> donneesBrutes = new CsvToBeanBuilder<DonneesBrutPokemon>(Files.newBufferedReader(Paths.get(fileName)))
                    .withSeparator(',')
                    .withType(DonneesBrutPokemon.class)
                    .build()
                    .parse();
            for (DonneesBrutPokemon d : donneesBrutes) {
                Pokemon pokemon = transformerPokemon(d);
                observableInstance.notifyObservers(pokemon);
            }
            System.out.println("tout les pokemon chargé");
        } else {
            throw new IllegalArgumentException("Type d'objet non supporté : " + objet.getName());
        }
    }

    /**
     * La methode transformer permet de retourner une initialisation de l'objet Iris avec les données de FormatDonneeBrut
     *
     * @return retourne un objet Iris
     * @see Iris
     */
    public static Iris transformerIris(DonneesBrutIris d) {
        return new Iris(d.getSepalLength(), d.getSepalWidth(), d.getPetalLength(), d.getPetalWidth(), d.getVariety());
    }
    /**
     * La methode transformer permet de retourner une initialisation de l'objet Iris avec les données de FormatDonneeBrut
     *
     * @return retourne un objet Pokemon
     * @see Pokemon
     */
    public static Pokemon transformerPokemon(DonneesBrutPokemon d) {
        return new Pokemon(d.getName(),d.getAttack(),d.getBase_egg_steps(),d.getCapture_rate(),d.getDefense(),d.getExperience_growth(),d.getHp(),d.getSp_attack(),d.getSp_defense(),d.getSpeed(),d.getType1(), d.getType2(),d.isIs_legendary());
    }
}
