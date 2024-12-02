package fr.univlille.iut.s302.point.vue;

import fr.univlille.iut.s302.point.controleur.Controleur;
import fr.univlille.iut.s302.point.controleur.ControleurAjoutPoint;
import fr.univlille.iut.s302.point.controleur.ControleurChargerCSV;
import fr.univlille.iut.s302.point.controleur.ControleurDistance;
import fr.univlille.iut.s302.point.modele.ChargementDonnees;
import fr.univlille.iut.s302.point.modele.Fleur;
import fr.univlille.iut.s302.point.modele.Iris;
import fr.univlille.iut.s302.utils.Observer;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Interface définissant les méthodes pour une vue affichant un nuage de points.
 * Implémente {@link Observer} pour être notifiée des changements de données.
 */
public interface VuePoint extends Observer {

    /** Initialise l'interface utilisateur. */
    void initUI();

    /** Initialise le graphique ScatterChart. */
    void initScatterChart();

    /**
     * Crée une boîte HBox contenant les boutons de l'interface.
     *
     * @return une instance de HBox contenant les boutons
     */
    HBox createButtonBox();

    /**
     * Crée une VBox contenant les ComboBox pour la sélection des axes.
     *
     * @return une instance de VBox contenant les ComboBox
     */
    VBox createComboBoxVBOX();

    /**
     * Obtient la valeur sélectionnée dans le ComboBox pour l'axe X.
     *
     * @return une chaîne représentant la sélection pour l'axe X
     */
    String getAxeXSelection();

    /**
     * Obtient la valeur sélectionnée dans le ComboBox pour l'axe Y.
     *
     * @return une chaîne représentant la sélection pour l'axe Y
     */
    String getAxeYSelection();

    /** @return l'instance de ScatterChart utilisée pour afficher les données */
    ScatterChart<Number, Number> getScatterChart();

    /** @return l'instance de NumberAxis utilisée pour l'axe X */
    NumberAxis getxAxis();

    /** @return l'instance de NumberAxis utilisée pour l'axe Y */
    NumberAxis getyAxis();

    /** @return l'instance de ComboBox utilisée pour l'axe X */
    ComboBox<String> getComboBoxX();

    /** @return l'instance de ComboBox utilisée pour l'axe Y */
    ComboBox<String> getComboBoxY();

    /** @return le conteneur principal BorderPane de la vue */
    BorderPane getLayout();

    void setControleur(Controleur controleur);

    ChargementDonnees getDonnees();

    void setList(List list);

    List getList();

    void viderNuageDePoints();

    Color getCouleurCategorie(Object variety);

    void afficherPoint(Object objet, double x, double y, Color couleur);

    void setControleurAp(ControleurAjoutPoint controleurAjoutPoint);

    void setControleurCSV(ControleurChargerCSV controleurChargerCSV);

    void setControleurDistance(ControleurDistance controleurDistance);

    ControleurAjoutPoint getControleurAp();
    ControleurChargerCSV getControleurCSV();
    ControleurDistance getControleurDist();


    void zoom(double zoomFactor, double mouseX, double mouseY, double delta);
}
