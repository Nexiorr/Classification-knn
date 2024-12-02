package fr.univlille.iut.s302.point.controleur;

import fr.univlille.iut.s302.point.modele.*;
import fr.univlille.iut.s302.point.vue.VuePoint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControleurAjoutPoint {
    private final ChargementDonnees modele;
    private final List<VuePoint> vues;
    private final Random RAND = new Random();
    public ControleurAjoutPoint(ChargementDonnees modele, List<VuePoint> vues) {
        this.modele = modele;
        this.vues = vues;
        for(VuePoint vp : vues){
            vp.setControleurAp(this);
        }
    }

    public ArrayList<VuePoint> getVuesIris(List<VuePoint> vues){
        ArrayList<VuePoint> vuesIris = new ArrayList<VuePoint>();
        for(int i = 0; i < vues.size(); i += 1){
            if(vues.get(i).getClass().getSimpleName().substring(3).equals("Iris")){
                vuesIris.add(vues.get(i));
            }
        }
        if(vuesIris.isEmpty()){
            System.err.println("Erreur : AUCUNE VUES EXISTANTES.");
        }
        return vuesIris;
    }

    public ArrayList<VuePoint> getVuesPokemon(List<VuePoint> vues){
        ArrayList<VuePoint> vuesPokemon = new ArrayList<VuePoint>();
        for(int i = 0; i < vues.size(); i += 1){
            if(vues.get(i).getClass().getSimpleName().substring(3).equals("Pokemon")){
                vuesPokemon.add(vues.get(i));
            }
        }
        if(vuesPokemon.isEmpty()){
            System.err.println("Erreur : AUCUNE VUES EXISTANTES.");
        }
        return vuesPokemon;
    }

    /**
     * Ouvre une nouvelle fenêtre pour ajouter un point (fleur) à la visualisation.
     * Permet à l'utilisateur de saisir les dimensions de la fleur et de sélectionner sa variété.
     */
    public void ouvrirFenetreAjoutPointIris() {
        Stage fenetreAjout = creerFenetre("Ajouter un nouvel Iris", 300, 500);;

        GridPane grid = creerGrille();
        TextField sepalLengthField = new TextField();
        TextField sepalWidthField = new TextField();
        TextField petalLengthField = new TextField();
        TextField petalWidthField = new TextField();
        TextField kVoisins = new TextField();
        TextField typeDist = new TextField();

        // La touche "Entrée" permet de passer au champ suivant
        sepalLengthField.setOnAction(e -> sepalWidthField.requestFocus());
        sepalWidthField.setOnAction(e -> petalLengthField.requestFocus());
        petalLengthField.setOnAction(e -> petalWidthField.requestFocus());
        petalWidthField.setOnAction(e -> kVoisins.requestFocus());
        kVoisins.setOnAction(e -> kVoisins.requestFocus());

        grid = ajoutGridDonneesIris(grid, sepalLengthField, sepalWidthField, petalLengthField, petalWidthField, kVoisins, typeDist);

        // Bouton pour valider l'ajout
        Button ajouterButton = new Button("Ajouter");
        grid.add(ajouterButton, 1, 7);

        // Action lors du clic sur le bouton "Ajouter"
        ajouterButton.setOnAction(e -> handleAjoutIris(sepalLengthField, sepalWidthField, petalLengthField, petalWidthField, kVoisins, typeDist, fenetreAjout));

        // Créer la scène et l'afficher
        Scene scene = new Scene(grid, 300, 250);
        String cssFilePath = "data/css/style.css";
        File cssFile = new File(cssFilePath);
        scene.getStylesheets().add(cssFile.toURI().toString());
        fenetreAjout.setScene(scene);
        fenetreAjout.showAndWait();
    }

    public void ouvrirFenetreAjoutPointPokemon(){
        Stage fenetreAjout = creerFenetre("Ajouter un nouveau Pokémon", 300, 650);

        GridPane grid = creerGrille();
        TextField name = new TextField();
        TextField attack = new TextField();
        TextField defense = new TextField();
        TextField speed = new TextField();
        TextField hp = new TextField();
        TextField spAttack = new TextField();
        TextField spDefense = new TextField();
        TextField baseEggSteps = new TextField();
        TextField captureRate = new TextField();
        TextField experienceGrowth = new TextField();
        TextField isLegend = new TextField();
        TextField kVoisins = new TextField();
        TextField typeDist = new TextField();

        // La touche "Entrée" permet de passer au champ suivant
        name.setOnAction(e -> attack.requestFocus());
        attack.setOnAction(e -> defense.requestFocus());
        defense.setOnAction(e -> speed.requestFocus());
        speed.setOnAction(e -> hp.requestFocus());
        hp.setOnAction(e -> spAttack.requestFocus());
        spAttack.setOnAction(e -> spDefense.requestFocus());
        spDefense.setOnAction(e -> baseEggSteps.requestFocus());
        baseEggSteps.setOnAction(e -> captureRate.requestFocus());
        captureRate.setOnAction(e -> experienceGrowth.requestFocus());
        experienceGrowth.setOnAction(e -> isLegend.requestFocus());
        isLegend.setOnAction(e -> kVoisins.requestFocus());
        kVoisins.setOnAction(e -> typeDist.requestFocus());
        typeDist.setOnAction(e -> typeDist.requestFocus());

        grid = ajoutGridDonneesPokemon(grid, name, attack, defense, speed, hp, spAttack, spDefense,
                baseEggSteps, captureRate, experienceGrowth, isLegend, kVoisins, typeDist);

        Button ajouterButton = new Button("Ajouter");
        grid.add(ajouterButton, 1, 15);

        ajouterButton.setOnAction(e ->
                handleAjoutPokemon(name, attack, defense, speed, hp, spAttack, spDefense, baseEggSteps,
                        captureRate, experienceGrowth, isLegend, kVoisins, typeDist, fenetreAjout));

        // Créer la scène et l'afficher
        Scene scene = new Scene(grid, 300, 250);
        String cssFilePath = "data/css/style.css";
        File cssFile = new File(cssFilePath);
        scene.getStylesheets().add(cssFile.toURI().toString());
        fenetreAjout.setScene(scene);
        fenetreAjout.showAndWait();
    }

    private void handleAjoutIris(TextField sepalLengthField, TextField sepalWidthField, TextField petalLengthField, TextField petalWidthField, TextField kVoisinsField, TextField typeDistance, Stage fenetreAjout) {
        try {
            int k = Integer.parseInt(kVoisinsField.getText());
            Iris newIris = creationIris(sepalLengthField, sepalWidthField, petalLengthField, petalWidthField, k, typeDistance);

            validationAjoutIris(newIris, k, typeDistance.getText());
            fenetreAjout.close();
        } catch (NumberFormatException ex) {
            System.err.println("Erreur : Veuillez entrer des nombres valides pour les dimensions.");
        }
    }

    private void handleAjoutPokemon(TextField name, TextField attack, TextField defense, TextField speed, TextField hp, TextField spAttack, TextField spDefense, TextField baseEggSteps, TextField captureRate, TextField experienceGrowth, TextField isLegend, TextField kVoisins, TextField typeDist, Stage fenetreAjout) {
        try {
            int k = Integer.parseInt(kVoisins.getText());
            Pokemon newPokemon = creationPokemon(name, attack, defense, speed, hp, spAttack, spDefense, baseEggSteps, captureRate, experienceGrowth, isLegend, kVoisins, typeDist);

            validationAjoutPokemon(newPokemon, k, typeDist.getText());
            fenetreAjout.close();
        } catch (NumberFormatException ex) {
            System.err.println("Erreur : Veuillez entrer des nombres valides pour les dimensions.");
        }
    }

    private Stage creerFenetre(String titre, int largeur, int hauteur) {
        Stage fenetre = new Stage();
        fenetre.initModality(Modality.APPLICATION_MODAL);
        fenetre.setTitle(titre);
        fenetre.setWidth(largeur);
        fenetre.setHeight(hauteur);
        return fenetre;
    }

    private GridPane creerGrille() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }

    public Iris creationIris(TextField sepalLengthField, TextField sepalWidthField, TextField petalLengthField, TextField petalWidthField, int k, TextField typeDist){
        ArrayList<VuePoint> vuesIris = getVuesIris(this.vues);
        VuePoint vue = vuesIris.get(0);

        // Récupérer les valeurs saisies
        double sepalLength = Double.parseDouble(sepalLengthField.getText());
        double sepalWidth = Double.parseDouble(sepalWidthField.getText());
        double petalLength = Double.parseDouble(petalLengthField.getText());
        double petalWidth = Double.parseDouble(petalWidthField.getText());

        // Créer un nouvel objet Iris
        Iris newIris = new Iris(sepalLength, sepalWidth, petalLength, petalWidth, Fleur.values()[0]);
        Fleur f = (Fleur) ControleurDistance.knn(newIris, k, typeDist.getText(), vue);
        int flora = -1;
        if(f == Fleur.SETOSA) flora = 0;
        if(f == Fleur.VERSICOLOR) flora = 1;
        if(f == Fleur.VIRGINICA) flora = 2;
        newIris.setVariety(Fleur.values()[flora]);
        return newIris;
    }

    public Pokemon creationPokemon(TextField nameField, TextField attackField, TextField defenseField, TextField speedField, TextField hpField, TextField spAttackField, TextField spDefenseField, TextField baseEggStepsField, TextField captureRateField, TextField experienceGrowthField, TextField isLegendField, TextField kVoisinsField, TextField typeDistField){
        ArrayList<VuePoint> vuesPoke = getVuesPokemon(this.vues);
        VuePoint vue = vuesPoke.get(0);

        // Récupérer les valeurs saisies
        String name = nameField.getText();
        double attack = Double.parseDouble(attackField.getText());
        double defense = Double.parseDouble(defenseField.getText());
        double speed = Double.parseDouble(speedField.getText());
        double hp = Double.parseDouble(hpField.getText());
        double spAtatck = Double.parseDouble(spAttackField.getText());
        double spDefense = Double.parseDouble(spDefenseField.getText());
        double baseEggSteps = Double.parseDouble(baseEggStepsField.getText());
        double captureRate = Double.parseDouble(captureRateField.getText());
        double expererienceGrowth = Double.parseDouble(captureRateField.getText());
        String isLegendText = isLegendField.getText();
        boolean isLegend = false;
        if(isLegendText.equals("Oui")){
            isLegend = true;
        }
        int k = Integer.parseInt(kVoisinsField.getText());
        // Créer un nouvel objet Pokemon
        Pokemon newPokemon = new Pokemon(name, attack, baseEggSteps, captureRate, defense, expererienceGrowth, hp, spAtatck, spDefense, speed, Type.values()[0], null, isLegend);
        Type[] types = (Type[]) ControleurDistance.knn(newPokemon, k, typeDistField.getText(), vue);
        newPokemon.setType(types);

        return newPokemon;
    }

    public GridPane ajoutGridDonneesIris(GridPane grid, TextField sepalLengthField, TextField sepalWidthField, TextField petalLengthField, TextField petalWidthField, TextField kVoisinsField, TextField typeDistField){
        ArrayList<VuePoint> vuesIris = getVuesIris(this.vues);
        VuePoint vue = vuesIris.get(0);

        // Ajouter les labels et champs de saisie à la grille
        ajouterChamp(grid, "Sepal Length :", sepalLengthField, 0);
        ajouterChamp(grid, "Sepal Width :", sepalWidthField, 1);
        ajouterChamp(grid, "Petal Length :", petalLengthField, 2);
        ajouterChamp(grid, "Petal Width :", petalWidthField, 3);

        grid.add(new Label("k voisins :"), 0, 4);

        Slider slider = new Slider(1, vue.getList().size(), ((double) vue.getList().size() / 2 ));
        Label l = new Label(" ");
        slider.valueProperty().addListener(
                new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number >
                                                observable, Number oldValue, Number newValue)
                    {

                        kVoisinsField.setText("" + newValue.intValue());
                    }
                });
        grid.add(kVoisinsField, 1, 4);
        grid.add(l, 0, 5);
        grid.add(slider ,1, 5);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        grid.add(new Label("Type de distance :"), 0, 6);
        ComboBox<String> comboBoxX = new ComboBox<>();
        comboBoxX.getItems().addAll("Euclidienne", "Manhattan");
        grid.add(comboBoxX, 1, 6);

        return grid;
    }

    public GridPane ajoutGridDonneesPokemon(GridPane grid, TextField name, TextField attack, TextField defense, TextField speed, TextField hp, TextField spAttack, TextField spDefense, TextField baseEggSteps, TextField captureRate, TextField experienceGrowth, TextField isLegend, TextField kVoisins, TextField typeDist){
        ArrayList<VuePoint> vuesPoke = getVuesPokemon(this.vues);
        VuePoint vue = vuesPoke.get(0);

        // Ajouter les labels et champs de saisie à la grille
        ajouterChamp(grid, "Name :", name, 0);
        ajouterChamp(grid, "Attack :", attack, 1);
        ajouterChamp(grid, "Defense :", defense, 2);
        ajouterChamp(grid, "Speed :", speed, 3);
        ajouterChamp(grid, "Hp :", hp, 4);
        ajouterChamp(grid, "SpAttack :", spAttack, 5);
        ajouterChamp(grid, "SpDefense :", spDefense, 6);
        ajouterChamp(grid, "Base Egg Steps :", baseEggSteps, 7);
        ajouterChamp(grid, "Capture Rate :", captureRate, 8);
        ajouterChamp(grid, "Experience Growth :", experienceGrowth, 9);
        grid.add(new Label("is Legend :"), 0, 10);
        ComboBox<String> comboBoxXisLegend = new ComboBox<>();
        comboBoxXisLegend.getItems().addAll("Oui", "Non");
        grid.add(comboBoxXisLegend, 1, 10);

        Slider slider = new Slider(1, vue.getList().size(), ((double) vue.getList().size() / 2 ));
        Label l = new Label(" ");
        slider.valueProperty().addListener(
                new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number >
                                                observable, Number oldValue, Number newValue)
                    {

                        kVoisins.setText("" + newValue.intValue());
                    }
                });
        grid.add(kVoisins, 1, 11);
        grid.add(l, 0, 12);
        grid.add(slider ,1, 12);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        grid.add(new Label("Type de distance :"), 0, 13);
        ComboBox<String> comboBoxXDist = new ComboBox<>();
        comboBoxXDist.getItems().addAll("Euclidienne", "Manhattan");
        grid.add(comboBoxXDist, 1, 13);

        return grid;
    }

    private void ajouterChamp(GridPane grid, String label, TextField field, int row) {
        grid.add(new Label(label), 0, row);
        grid.add(field, 1, row);
    }

    public void validationAjoutIris(Iris newIris, int k, String typeDist){
        ArrayList<VuePoint> vuesIris = getVuesIris(this.vues);
        VuePoint vue = vuesIris.get(0);

        Stage validationFenetre = creerFenetre("Validation Ajout iris", 500, 400);

        // Ajout temporaire du point en couleur noire pour le visualiser
        afficherPointTemporaire(newIris);

        GridPane grid = creerGrille();
        afficherRecapitulatif(grid, newIris, k, typeDist);

        Button validerAjout = new Button("Valider");
        Button annulerAjout = new Button("Annuler");

        grid.add(validerAjout, 0, 11);
        grid.add(annulerAjout, 1, 11);

        validerAjout.setOnAction(e -> {
            ajouterNouveauPoint(newIris);
            validationFenetre.close();
        });

        annulerAjout.setOnAction(e -> {
            validationFenetre.close();
            vue.viderNuageDePoints();
            Controleur.mettreAJourNuageDePoints();
        });

        Scene scene = new Scene(grid, 500, 250);
        validationFenetre.setScene(scene);
        validationFenetre.showAndWait();

    }

    public void validationAjoutPokemon(Pokemon newPokemon, int k, String typeDist){
        ArrayList<VuePoint> vuesPoke = getVuesPokemon(this.vues);
        VuePoint vue = vuesPoke.get(0);

        Stage validationFenetre = creerFenetre("Validation Ajout pokemon", 500, 550);

        // Ajout temporaire du point en couleur noire pour le visualiser
        afficherPointTemporaire(newPokemon);

        GridPane grid = creerGrille();
        afficherRecapitulatif(grid,  newPokemon, k, typeDist);

        Button validerAjout = new Button("Valider");
        Button annulerAjout = new Button("Annuler");

        grid.add(validerAjout, 0, 18);
        grid.add(annulerAjout, 1, 18);

        validerAjout.setOnAction(e -> {
            ajouterNouveauPoint(newPokemon);
            validationFenetre.close();
        });

        annulerAjout.setOnAction(e -> {
            validationFenetre.close();
            vue.viderNuageDePoints();
            Controleur.mettreAJourNuageDePoints();
        });

        Scene scene = new Scene(grid, 500, 250);
        validationFenetre.setScene(scene);
        validationFenetre.showAndWait();

    }

    private void afficherPointTemporaire(Object obj){

        Color couleurTemporaire = Color.BLACK;
        if(obj instanceof Iris){
            ArrayList<VuePoint> vuesIris = getVuesIris(this.vues);
            Iris iris = (Iris) obj;
            for (VuePoint v :vuesIris) {
                v.afficherPoint(iris,
                        Controleur.getAxeValue(iris, v.getAxeXSelection()),
                        Controleur.getAxeValue(iris, v.getAxeYSelection()),
                        couleurTemporaire);
            }
        }else if(obj instanceof Pokemon) {
            ArrayList<VuePoint> vuesPokemon = getVuesPokemon(this.vues);
            Pokemon pokemon = (Pokemon) obj;
            for (VuePoint v : getVuesPokemon(vues)) {
                v.afficherPoint(pokemon,
                        Controleur.getAxeValue(pokemon, v.getAxeXSelection()),
                        Controleur.getAxeValue(pokemon, v.getAxeYSelection()),
                        couleurTemporaire);
            }
        }
    }

    private void afficherRecapitulatif(GridPane grid, Object obj, int k, String typeDistance) {
        grid.add(new Label("Récapitulatif du point à ajouter :"), 1, 0);
        if(obj instanceof Iris) {
            Iris iris = (Iris) obj;
            grid.add(new Label("Sepal Length : " + iris.getSepalLength()), 0, 4);
            grid.add(new Label("Sepal Width : " + iris.getSepalWidth()), 0, 5);
            grid.add(new Label("Petal Length : " + iris.getPetalLength()), 0, 6);
            grid.add(new Label("Petal Width : " + iris.getPetalWidth()), 0, 7);
            grid.add(new Label("Nombre de voisins : " + k), 0, 8);
            grid.add(new Label("Type de distance : " + typeDistance), 0, 9);
        }else if(obj instanceof Pokemon){
            Pokemon pokemon = (Pokemon) obj;
            grid.add(new Label("Name : " + pokemon.getName()), 0, 4);
            grid.add(new Label("Attack : " + pokemon.getAttack()), 0, 5);
            grid.add(new Label("Defense : " + pokemon.getDefense()), 0, 6);
            grid.add(new Label("Speed : " + pokemon.getSpeed()), 0, 7);
            grid.add(new Label("Hp : " + pokemon.getHp()), 0, 8);
            grid.add(new Label("SpAttack : " + pokemon.getSpAttack()), 0, 9);
            grid.add(new Label("SpDefense : " + pokemon.getSpDefense()), 0, 10);
            grid.add(new Label("Base Egg Steps : " + pokemon.getBaseEggSteps()), 0, 11);
            grid.add(new Label("Capture Rate : " + pokemon.getCaptureRate()), 0, 12);
            grid.add(new Label("Experience Growth : " + pokemon.getExperienceGrowth()), 0, 13);
            grid.add(new Label("Is Legend : " + pokemon.getIsLegend()), 0, 14);
            grid.add(new Label("Nombre de voisins : " + k), 0, 15);
            grid.add(new Label("Type de distance : " + typeDistance), 0, 16);
        }else{
            throw new IllegalArgumentException("Unsupported object type: " + obj.getClass());
        }
    }

    /**
     * Ajoute un nouveau point au nuage de points et met à jour la vue.
     *
     * @param obj Un objet {@link Object} représentant la nouvelle fleur ou pokémon à ajouter.
     */
    private void ajouterNouveauPoint(Object obj) {
        if(obj instanceof Iris){
            ArrayList<VuePoint> vuesIris = getVuesIris(this.vues);
            for(VuePoint vue : vuesIris){
                Iris newIris = (Iris) obj;
                // Ajouter la nouvelle fleur à la liste des données
                vue.getList().add(newIris);

                // Affichage du point sur le nuage de point
                vue.afficherPoint(newIris, Controleur.getAxeValue(newIris, vue.getAxeXSelection()),
                        Controleur.getAxeValue(newIris, vue.getAxeYSelection()),
                        vue.getCouleurCategorie(newIris.getVariety()));
            }

        }else if(obj instanceof Pokemon){
            ArrayList<VuePoint> vuesPokemon = getVuesPokemon(this.vues);
            for(VuePoint vue : vuesPokemon){
                Pokemon newPokemon = (Pokemon) obj;
                // Ajouter la nouvelle fleur à la liste des données
                vue.getList().add(newPokemon);
                Color couleur = vue.getCouleurCategorie(newPokemon.getType1());
                // Affichage du point sur le nuage de point
                vue.afficherPoint(newPokemon, Controleur.getAxeValue(newPokemon, vue.getAxeXSelection()),
                        Controleur.getAxeValue(newPokemon, vue.getAxeYSelection()),
                        couleur);
            }
        }else{
            throw new IllegalArgumentException("Unsupported object type: " + obj.getClass());
        }



    }

}
