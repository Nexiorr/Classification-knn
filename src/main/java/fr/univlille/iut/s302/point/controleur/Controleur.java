package fr.univlille.iut.s302.point.controleur;

import fr.univlille.iut.s302.point.modele.*;
import fr.univlille.iut.s302.point.vue.VueIris;
import fr.univlille.iut.s302.point.vue.VuePoint;
import fr.univlille.iut.s302.point.vue.VuePokemon;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Controleur {
    private static ChargementDonnees modele;
    private static List<VuePoint> vues;
    private final Random RAND = new Random();

    public Controleur(ChargementDonnees modele, List<VuePoint> vues) {
        this.modele = modele;
        this.vues = vues;
        for(VuePoint vp : vues){
            vp.setControleur(this);
            vp.initUI();
        }
    }

    /**
     * Charge les données à partir d'un fichier donné.
     *
     * @param fileName Le nom du fichier à partir duquel on charge les données.
     * @throws IOException Si une erreur survient lors du chargement des données.
     */

    public static void chargerDonnees(String fileName) throws IOException {
        System.out.println("chargerDonnees");
        if (vues.get(0).getClass().getSimpleName().equals("VueIris")){
            ChargementDonnees.charger(fileName, modele, Iris.class);
        }
        System.out.println("pas ds le if c'est bon");
        ChargementDonnees.charger(fileName, modele, Pokemon.class);
    }

    public static void chargerDonnees(String fileName, String type) throws IOException {
        if (type.equals("Iris")){
            ChargementDonnees.charger(fileName, modele, Iris.class);
        }
        ChargementDonnees.charger(fileName, modele, Pokemon.class);
    }

    public void chargerDonneesFromView(VuePoint newVue, VuePoint vueOrigin){
        newVue.setList(vueOrigin.getList());
    }

    public void createNewView(){
        String vueType = vues.get(0).getClass().getSimpleName();
        VuePoint newVuePoint = createVue(vueType);
        if(newVuePoint != null){
            vues.add(newVuePoint);
            newVuePoint.setControleur(this);
            newVuePoint.setControleurCSV(vues.get(0).getControleurCSV());
            newVuePoint.setControleurAp(vues.get(0).getControleurAp());
            newVuePoint.setControleurDistance(vues.get(0).getControleurDist());
            newVuePoint.initUI();
            chargerDonneesFromView(newVuePoint,vues.get(0));
            mettreAJourNuageDePoints();
        }
    }

    private VuePoint createVue(String type) {
        return switch (type) {
            case "VueIris" -> new VueIris(modele);
            case "VuePokemon" -> new VuePokemon(modele);
            default -> null;
        };
    }



    /**
     * Met à jour la visualisation du nuage de points en fonction des axes sélectionnés.
     * Chaque point est affiché avec sa couleur correspondant à sa variété.
     */
    public static void mettreAJourNuageDePoints() {
        for (VuePoint vue : vues) {
            vue.viderNuageDePoints();
            String axeX = vue.getAxeXSelection();
            String axeY = vue.getAxeYSelection();

            vue.getScatterChart().setLegendVisible(false);
            vue.getList().forEach(obj -> { //CleanCode evitons la répétition de code avec Polymorphism
                if (obj instanceof Iris iris) {
                    double x = getAxeValue(iris, axeX);
                    double y = getAxeValue(iris, axeY);
                    Color couleur = vue.getCouleurCategorie(iris.getVariety());
                    vue.afficherPoint(iris, x, y, couleur);
                } else if (obj instanceof Pokemon pokemon) {
                    double x = getAxeValue(pokemon, axeX);
                    double y = getAxeValue(pokemon, axeY);
                    Color couleur = vue.getCouleurCategorie(pokemon.getType1());
                    vue.afficherPoint(pokemon, x, y, couleur);
                }
            });

            vue.getxAxis().setLabel(vue.getComboBoxX().getValue());
            vue.getyAxis().setLabel(vue.getComboBoxY().getValue());

            Pane layout = vue.getLayout();
            layout.getChildren().removeIf(node -> node instanceof HBox); //évite les doublons
            layout.getChildren().add(creerLegendePersonnalisee(vue));
        }
    }


    public static FlowPane creerLegendePersonnalisee(VuePoint vue) {
        FlowPane legende = new FlowPane(); // Conteneur qui gère le wrapping
        legende.setHgap(10); // Espacement horizontal entre les éléments
        legende.setVgap(10); // Espacement vertical entre les lignes
        legende.setPrefWrapLength(300); // Largeur à partir de laquelle le wrapping s'applique (modifiable selon vos besoins)

        // Gestion de la légende pour VueIris
        if (vue.getClass().getSimpleName().equals("VueIris")) {
            for (Fleur fleur : Fleur.values()) {
                Circle circle = new Circle(10); // Cercle pour la couleur
                circle.setFill(vue.getCouleurCategorie(fleur)); // Couleur associée à la fleur
                Text label = new Text(fleur.toString()); // Texte pour le nom de la catégorie
                VBox item = new VBox(circle, label); // Empile le cercle et le texte
                item.setSpacing(5); // Espacement entre le cercle et le texte
                item.setAlignment(Pos.CENTER); // Centrer les éléments
                legende.getChildren().add(item); // Ajouter à la légende
            }
        }
        // Gestion de la légende pour VuePokemon
        else if (vue.getClass().getSimpleName().equals("VuePokemon")) {
            for (Type type : Type.values()) {
                Circle circle = new Circle(10); // Cercle pour la couleur
                circle.setFill(vue.getCouleurCategorie(type)); // Couleur associée au type
                Text label = new Text(type.toString()); // Texte pour le nom du type
                VBox item = new VBox(circle, label); // Empile le cercle et le texte
                item.setSpacing(5); // Espacement entre le cercle et le texte
                item.setAlignment(Pos.CENTER); // Centrer les éléments
                legende.getChildren().add(item); // Ajouter à la légende
            }
        }

        return legende; // Retourner la légende complète
    }


    /**
     * Récupère la valeur de l'axe spécifié pour un objet {@link Iris}.
     *
     * @param obj L'objet dont on souhaite récupérer la valeur.
     * @param axe Le nom de l'axe (par exemple, "Sepal Length" ou "Petal Width").
     * @return La valeur de l'axe spécifié.
     */
    protected static double getAxeValue(Object obj, String axe) {
        if (obj instanceof Iris iris) {
            return switch (axe) {
                case "Sepal Length" -> iris.getSepalLength();
                case "Sepal Width" -> iris.getSepalWidth();
                case "Petal Length" -> iris.getPetalLength();
                case "Petal Width" -> iris.getPetalWidth();
                default -> 0;
            };
        } else if (obj instanceof Pokemon pokemon) {
            return switch (axe) {
                case "Attack" -> pokemon.getAttack();
                case "Base Egg Steps" -> pokemon.getBaseEggSteps();
                case "Capture Rate" -> pokemon.getCaptureRate();
                case "Defense" -> pokemon.getDefense();
                case "Experience Growth" -> pokemon.getExperienceGrowth();
                case "HP" -> pokemon.getHp();
                case "Special Attack" -> pokemon.getSpAttack();
                case "Special Defense" -> pokemon.getSpDefense();
                case "Speed" -> pokemon.getSpeed();
                default -> 0;
            };
        }
        return 0;
    }

}
