package controleur;

import fr.univlille.iut.s302.point.controleur.ControleurAjoutPoint;
import fr.univlille.iut.s302.point.modele.ChargementDonnees;
import fr.univlille.iut.s302.point.modele.Fleur;
import fr.univlille.iut.s302.point.modele.Iris;
import fr.univlille.iut.s302.point.vue.VuePoint;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testControleurAjoutPoint {

    private ControleurAjoutPoint controleur;
    private ChargementDonnees modele;
    private List<VuePoint> vues;

    @BeforeEach
    modele = new ChargementDonnees();
    vues = new ArrayList<>();
    VuePoint vue = new VuePoint();
    vues.add(vue);

    controleur = new ControleurAjoutPoint(modele, vues);

    @Test
    public void testAjoutGridDonnees() {
        TextField sepalLengthField = new TextField();
        TextField sepalWidthField = new TextField();
        TextField petalLengthField = new TextField();
        TextField petalWidthField = new TextField();
        TextField kVoisinsField = new TextField();
        TextField typeDistField = new TextField();

        var grid = controleur.ajoutGridDonnees(null, sepalLengthField, sepalWidthField, petalLengthField, petalWidthField, kVoisinsField, typeDistField);

        assertNotNull(grid);
        assertEquals(7, grid.getChildren().size());
    }
}
