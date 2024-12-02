package fr.univlille.iut.s302.point.controleur;

import fr.univlille.iut.s302.point.modele.ChargementDonnees;
import fr.univlille.iut.s302.point.modele.Iris;
import fr.univlille.iut.s302.point.modele.Pokemon;
import fr.univlille.iut.s302.point.vue.VuePoint;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestControleurDistance {

    private ControleurDistance controleur;
    private ChargementDonnees modele;
    private List<VuePoint> vues;

    @BeforeEach
    modele = new ChargementDonnees();
    vues = new ArrayList<>();
    vues.add(new VuePoint(modele));
    controleur = new ControleurDistance(modele, vues);>

    @Test
    public void testDistanceEuclidienne() {
        Iris iris1 = new Iris("setosa", 5.1, 3.5, 1.4, 0.2);
        Iris iris2 = new Iris("versicolor", 7.0, 3.2, 4.7, 1.4);
        VuePoint vue = new VuePoint(modele);
        Double result = ControleurDistance.distanceEuclidienne(iris1, iris2, vue);
        assertNotNull(result);
    }

    @Test
    public void testDistanceManhattan() {
        Pokemon pokemon1 = new Pokemon("Bulbasaur", 49, 49, 45, 60, 40, 65, 20);
        Pokemon pokemon2 = new Pokemon("Charmander", 52, 43, 39, 50, 50, 65, 20);
        VuePoint vue = new VuePoint(modele);
        Double result = ControleurDistance.distanceManhattan(pokemon1, pokemon2, vue);
        assertNotNull(result);
    }

    @Test
    public void testKnn() {
        Iris iris1 = new Iris("setosa", 5.1, 3.5, 1.4, 0.2);
        Iris iris2 = new Iris("versicolor", 7.0, 3.2, 4.7, 1.4);
        List<VuePoint> vuesTest = new ArrayList<>();
        vuesTest.add(new VuePoint(modele));
        controleur = new ControleurDistance(modele, vuesTest);

        VuePoint vue = vuesTest.get(0);
        vue.addItem(iris1);
        vue.addItem(iris2);

        Object result = ControleurDistance.knn(iris1, 1, "euclidienne", vue);
        assertNotNull(result);
    }

    @Test
    public void testKnnWithPokemon() {
        Pokemon pokemon1 = new Pokemon("Bulbasaur", 49, 49, 45, 60, 40, 65, 20);
        Pokemon pokemon2 = new Pokemon("Charmander", 52, 43, 39, 50, 50, 65, 20);
        List<VuePoint> vuesTest = new ArrayList<>();
        vuesTest.add(new VuePoint(modele));
        controleur = new ControleurDistance(modele, vuesTest);

        VuePoint vue = vuesTest.get(0);
        vue.addItem(pokemon1);
        vue.addItem(pokemon2);

        Object result = ControleurDistance.knn(pokemon1, 1, "manhattan", vue);
        assertNotNull(result);
    }

    @Test
    public void testGetMinMax() {
        Iris iris1 = new Iris("setosa", 5.1, 3.5, 1.4, 0.2);
        Iris iris2 = new Iris("versicolor", 7.0, 3.2, 4.7, 1.4);
        List<VuePoint> vuesTest = new ArrayList<>();
        vuesTest.add(new VuePoint(modele));
        controleur = new ControleurDistance(modele, vuesTest);

        VuePoint vue = vuesTest.get(0);
        vue.addItem(iris1);
        vue.addItem(iris2);

        double[] result = ControleurDistance.getMinMax(vue, "Sepal Length");
        assertNotNull(result);
        assertTrue(result[0] <= result[1]);
    }

    @Test
    public void testNormalize() {
        double result = ControleurDistance.normalize(5.0, 0.0, 10.0);
        assertEquals(0.5, result);
    }

    @Test
    public void testGetAxeValue() {
        Iris iris = new Iris("setosa", 5.1, 3.5, 1.4, 0.2);
        double result = ControleurDistance.getAxeValue(iris, "Sepal Length");
        assertEquals(5.1, result);
    }
}
