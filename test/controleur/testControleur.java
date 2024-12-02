package controleur;

import fr.univlille.iut.s302.point.modele.*;
import fr.univlille.iut.s302.point.vue.VueIris;
import fr.univlille.iut.s302.point.vue.VuePoint;
import fr.univlille.iut.s302.point.vue.VuePokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestControleur {

    private Controleur controleur;
    private ChargementDonnees modele;
    private List<VuePoint> vues;

    @BeforeEach
    modele = new ChargementDonnees();
    vues = new ArrayList<>();
    vues.add(new VueIris(modele));
    vues.add(new VuePokemon(modele));
    controleur = new Controleur(modele, vues);


    @Test
    public void testChargerDonnees() throws IOException {
        controleur.chargerDonnees("donnees.txt");

        assertNotNull(modele.getIrisList());
        assertNotNull(modele.getPokemonList());
    }

    @Test
    public void testChargerDonneesWithType() throws IOException {
        controleur.chargerDonnees("donnees.txt", "Iris");

        assertNotNull(modele.getIrisList());
        assertNull(modele.getPokemonList());
    }

    @Test
    public void testCreateNewView() {
        int initialSize = vues.size();
        controleur.createNewView();
        assertEquals(initialSize + 1, vues.size());
    }

    @Test
    public void testMettreAJourNuageDePoints() {
        VuePoint vue = vues.get(0);
        vue.setList(new ArrayList<>());
        controleur.mettreAJourNuageDePoints();

        assertNotNull(vue.getScatterChart());
        assertEquals(0, vue.getScatterChart().getData().size());
    }

    @Test
    public void testCreerLegendePersonnalisee() {
        VuePoint vueIris = new VueIris(modele);
        FlowPane legendeIris = controleur.creerLegendePersonnalisee(vueIris);

        assertNotNull(legendeIris);
        assertTrue(legendeIris.getChildren().size() > 0);

        VuePoint vuePokemon = new VuePokemon(modele);
        FlowPane legendePokemon = controleur.creerLegendePersonnalisee(vuePokemon);

        assertNotNull(legendePokemon);
        assertTrue(legendePokemon.getChildren().size() > 0);
    }

    @Test
    public void testGetAxeValueForIris() {
        Iris iris = new Iris(5.0, 3.0, 2.0, 0.5, Fleur.SETOSA);

        double valueSepalLength = controleur.getAxeValue(iris, "Sepal Length");
        assertEquals(5.0, valueSepalLength);

        double valueSepalWidth = controleur.getAxeValue(iris, "Sepal Width");
        assertEquals(3.0, valueSepalWidth);

        double valuePetalLength = controleur.getAxeValue(iris, "Petal Length");
        assertEquals(2.0, valuePetalLength);

        double valuePetalWidth = controleur.getAxeValue(iris, "Petal Width");
        assertEquals(0.5, valuePetalWidth);
    }

    @Test
    public void testGetAxeValueForPokemon() {
        Pokemon pokemon = new Pokemon("Pikachu", 35, 55, 40, 50, 90, 35, 50, 50, 35);

        double valueAttack = controleur.getAxeValue(pokemon, "Attack");
        assertEquals(55.0, valueAttack);

        double valueDefense = controleur.getAxeValue(pokemon, "Defense");
        assertEquals(40.0, valueDefense);

        double valueHp = controleur.getAxeValue(pokemon, "HP");
        assertEquals(35.0, valueHp);
    }
}
