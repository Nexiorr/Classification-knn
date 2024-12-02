package fr.univlille.iut.s302.point.vue;

import fr.univlille.iut.s302.point.controleur.Controleur;
import fr.univlille.iut.s302.point.controleur.ControleurAjoutPoint;
import fr.univlille.iut.s302.point.controleur.ControleurChargerCSV;
import fr.univlille.iut.s302.point.controleur.ControleurDistance;
import fr.univlille.iut.s302.point.modele.ChargementDonnees;
import fr.univlille.iut.s302.point.modele.Pokemon;
import fr.univlille.iut.s302.point.modele.Type;
import fr.univlille.iut.s302.utils.Observable;
import fr.univlille.iut.s302.utils.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VuePokemon extends Stage implements Observer, VuePoint {


    /** Un mappage entre les variétés de fleurs et leurs couleurs correspondantes. */
    private Map<Type, Color> couleursCategories;

    /** Un mappage entre les variétés de fleurs et leurs séries de données pour le graphique en dispersion */
    private Map<Type[], XYChart.Series<Number, Number>> seriesMap;

    private List<Pokemon> pokemonList;
    private ChargementDonnees donnees;

    public ChargementDonnees getDonnees() {
        return donnees;
    }

    private ScatterChart<Number, Number> scatterChart;


    private ComboBox<String> comboBoxX;
    private ComboBox<String> comboBoxY;

    /** Le conteneur principal de l'interface. */
    private BorderPane testLayout;

    /** Les axes x et y du graphique */
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    private Controleur controleur;
    private ControleurAjoutPoint controleurAp;
    private ControleurChargerCSV controleurCSV;
    private ControleurDistance controleurDistance;

    /** Variables pour gérer la sélection d'un point */
    private Circle dernierCercleSelectionne = null;
    private Color derniereCouleur = null;

    public VuePokemon(ChargementDonnees donnees) {
        this.couleursCategories = new HashMap<>();
        this.seriesMap = new HashMap<>();
        this.donnees = donnees;
        this.pokemonList = new ArrayList<>();
        this.donnees.attach(this);  // S'attacher à l'instance de ChargementDonnees

        couleursCategories.put(Type.BUG, Color.GREEN);         // Insecte
        couleursCategories.put(Type.NORMAL, Color.LIGHTGRAY); // Normal
        couleursCategories.put(Type.FLYING, Color.SKYBLUE);   // Volant
        couleursCategories.put(Type.GRASS, Color.DARKGREEN);  // Plante
        couleursCategories.put(Type.ELECTRIC, Color.YELLOW);  // Électrique
        couleursCategories.put(Type.PSYCHIC, Color.PURPLE);   // Psy
        couleursCategories.put(Type.POISON, Color.MEDIUMPURPLE); // Poison
        couleursCategories.put(Type.STEEL, Color.SILVER);     // Acier
        couleursCategories.put(Type.DRAGON, Color.DARKBLUE);  // Dragon
        couleursCategories.put(Type.WATER, Color.BLUE);       // Eau
        couleursCategories.put(Type.ROCK, Color.BROWN);       // Roche
        couleursCategories.put(Type.ICE, Color.CYAN);         // Glace
        couleursCategories.put(Type.GROUND, Color.SANDYBROWN); // Sol
        couleursCategories.put(Type.FIRE, Color.RED);         // Feu
        couleursCategories.put(Type.FAIRY, Color.PINK);       // Fée
        couleursCategories.put(Type.GHOST, Color.DARKSLATEBLUE); // Spectre
        couleursCategories.put(Type.DARK, Color.BLACK);       // Ténèbres
        couleursCategories.put(Type.FIGHTING, Color.ORANGERED); // Combat

    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void setList(List list) {
        this.pokemonList = list;
    }

    @Override
    public List getList() {
        return pokemonList;
    }

    public void setControleurAp(ControleurAjoutPoint controleurAp) {
        this.controleurAp = controleurAp;
    }
    public void setControleurCSV(ControleurChargerCSV controleurCSV) {
        this.controleurCSV = controleurCSV;
    }
    public void setControleurDistance(ControleurDistance controleurDistance) {
        this.controleurDistance= controleurDistance;
    }

    public void initUI() {
        Stage primaryStage = new Stage();
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("000000000 La fenetre se ferme 000000000000000");
            donnees.detach(this);
            if(controleurCSV != null){
                controleurCSV.clearVues();
            }
        });

        testLayout = new BorderPane();
        primaryStage.setTitle("Nuage de Points");
        initScatterChart();

        BorderPane testLayout = new BorderPane();
        testLayout.getStyleClass().add("vbox-layout");

        FlowPane legende = controleur.creerLegendePersonnalisee(this);

        testLayout.setCenter(scatterChart);
        testLayout.setLeft(createComboBoxVBOX());
        testLayout.setRight(legende);
        testLayout.setBottom(createButtonBox());

        Scene scene = new Scene(testLayout, 800, 600);
        String cssFilePath = "data/css/style.css";
        File cssFile = new File(cssFilePath);
        scene.getStylesheets().add(cssFile.toURI().toString());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox createButtonBox(){
        Button ajouterPointButton = new Button("Ajouter un Point");
        ajouterPointButton.setOnAction(e -> controleurAp.ouvrirFenetreAjoutPointPokemon());

        Button chargeButton = new Button("Charger un fichier csv");
        chargeButton.setOnAction(e -> controleurCSV.ouvrirFenetreChargerCSV());

        Button newViewButton = new Button("Nouvelle fenetre");
        newViewButton.setOnAction(e -> controleur.createNewView());

        ajouterPointButton.getStyleClass().add("button");
        chargeButton.getStyleClass().add("button");
        newViewButton.getStyleClass().add("button");

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(ajouterPointButton, chargeButton, newViewButton);
        buttonBox.getStyleClass().add("hbox-buttons");
        return buttonBox;
    }

    public void initScatterChart() {
        // Initialisation des axes
        xAxis = new NumberAxis();
        xAxis.setLabel("Attack");
        yAxis = new NumberAxis();
        yAxis.setLabel("Defense");

        // Initialisation du ScatterChart
        scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Nuage de Points Pokémon");
        scatterChart.getStyleClass().add("scatter-chart");


        scatterChart.setOnScroll(event -> {

            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            double delta = event.getDeltaY();

            double zoomFactor;
            if(event.getDeltaY() > 0) {
                zoomFactor = 0.9;
            }
            else {
                zoomFactor = 1.1;
            }
            zoom(zoomFactor, mouseX, mouseY, delta);
        });

    }

    public VBox createComboBoxVBOX(){
        // Initialisation des ComboBox pour les axes
        // Initialisation des ComboBox pour les axes
        comboBoxX = new ComboBox<>();
        comboBoxX.getItems().addAll(
                "Attack",
                "Defense",
                "Speed",
                "HP",
                "Special Attack",
                "Special Defense",
                "Base Egg Steps",
                "Capture Rate",
                "Experience Growth"
        );
        comboBoxX.setValue("Attack"); // Valeur par défaut pour l'axe X

        comboBoxY = new ComboBox<>();
        comboBoxY.getItems().addAll(
                "Attack",
                "Defense",
                "Speed",
                "HP",
                "Special Attack",
                "Special Defense",
                "Base Egg Steps",
                "Capture Rate",
                "Experience Growth"
        );
        comboBoxY.setValue("Defense"); // Valeur par défaut pour l'axe Y


        comboBoxX.setOnAction(e -> controleur.mettreAJourNuageDePoints());
        comboBoxY.setOnAction(e -> controleur.mettreAJourNuageDePoints());

        VBox comboVBox = new VBox(15);
        comboVBox.getChildren().addAll(comboBoxX, comboBoxY);
        return comboVBox;
    }

    public void afficherPoint(Object poke, double x, double y, Color couleur) {
        Pokemon pokemon = (Pokemon) poke;
        Type[] listTypes = new Type[]{pokemon.getType1(),pokemon.getType2()};
        XYChart.Series<Number, Number> series = seriesMap.computeIfAbsent(listTypes, cat -> {
            XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
            newSeries.setName(cat.toString());
            scatterChart.getData().add(newSeries);
            return newSeries;
        });

        Circle circle = new Circle(4);
        circle.setFill(couleur);

        // Ajoutez un événement de clic sur le cercle pour afficher les informations sur les pokemons
        circle.setOnMouseClicked(event -> {
            afficherValeursPokemon(pokemon, circle);
        });

        XYChart.Data<Number, Number> point = new XYChart.Data<>(x, y, circle);
        point.setNode(circle);
        series.getData().add(point);
    }

    private void afficherValeursPokemon(Pokemon pokemon, Circle cercle) {
        // Restaurer la couleur du dernier cercle sélectionné
        if (dernierCercleSelectionne != null && derniereCouleur != null) {
            dernierCercleSelectionne.setFill(derniereCouleur);
        }
        // Sauvegarder la couleur actuelle du cercle sélectionné
        dernierCercleSelectionne = cercle;
        derniereCouleur = (Color) cercle.getFill();
        // Changer la couleur du cercle sélectionné en noir
        cercle.setFill(Color.BLACK);

        // Afficher la fenêtre des détails de les pokemon
        Stage fenetreDetail = new Stage();
        fenetreDetail.setTitle("Détails de les pokemon");

        VBox informationPokemon = createPokemonDetailInformation(pokemon);

        // Création du bouton "Fermer"
        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> {
            // Restaurer la couleur d'origine de les pokémon
            cercle.setFill(derniereCouleur);
            // Fermer la fenêtre
            fenetreDetail.close();
        });

        informationPokemon.getChildren().add(closeButton); // Ajout du bouton à la fenêtre

        Scene scene = new Scene(informationPokemon, 300, 400);
        fenetreDetail.setScene(scene);
        fenetreDetail.show();
    }

    @Override
    public void zoom(double zoomFactor, double mouseX, double mouseY, double delta) {
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);

        double xMousePosition = xAxis.sceneToLocal(mouseX, mouseY).getX();
        double yMousePosition = yAxis.sceneToLocal(mouseX, mouseY).getY();

        // calcul des nouvelles bornes pour l'axe X
        double xLowerBound = xAxis.getLowerBound();
        double xUpperBound = xAxis.getUpperBound();
        double xRange = xUpperBound - xLowerBound;
        double newRangeX = xRange * zoomFactor;

        double xScaleFactor = (xMousePosition - xAxis.getLayoutX()) / xAxis.getWidth();
        xAxis.setLowerBound(xLowerBound + xRange * xScaleFactor * (1 - zoomFactor));
        xAxis.setUpperBound(xLowerBound + newRangeX);

        // calcul des nouvelles borndes pour l'axe Y
        double yLowerBound = yAxis.getLowerBound();
        double yUpperBound = yAxis.getUpperBound();
        double yRange = yUpperBound - yLowerBound;
        double newRangeY = yRange * zoomFactor;

        double yScaleFactor = (yMousePosition - yAxis.getLayoutY()) / yAxis.getHeight();
        yAxis.setLowerBound(yLowerBound + yRange * yScaleFactor * (1 - zoomFactor));
        yAxis.setUpperBound(yLowerBound + newRangeY);
    }

    public VBox createPokemonDetailInformation(Pokemon pokemon){
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        layout.getChildren().addAll(
                new Text("Name: " + pokemon.getName()),
                new Text("Attack: " + pokemon.getAttack()),
                new Text("Base Egg Steps: " + pokemon.getBaseEggSteps()),
                new Text("Capture Rate: " + pokemon.getCaptureRate()),
                new Text("Defense: " + pokemon.getDefense()),
                new Text("Experience Growth: " + pokemon.getExperienceGrowth()),
                new Text("HP: " + pokemon.getHp()),
                new Text("Special Attack: " + pokemon.getSpAttack()),
                new Text("Special Defense: " + pokemon.getSpDefense()),
                new Text("Speed: " + pokemon.getSpeed()),
                new Text("Type1: " + pokemon.getType1()),
                new Text("Type2: " + pokemon.getType2()),
                new Text("Is Legendary: " + pokemon.isLegend())
        );
        return layout;
    }


    public String getAxeXSelection() {
        return comboBoxX.getValue();
    }

    public String getAxeYSelection() {
        return comboBoxY.getValue();
    }

    public Color getCouleurCategorie(Object categorie) {
        return couleursCategories.getOrDefault(categorie, Color.BLACK);
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public ScatterChart getScatterChart() {
        return scatterChart;
    }

    public NumberAxis getxAxis() {
        return xAxis;
    }

    public NumberAxis getyAxis() {
        return yAxis;
    }

    public ComboBox getComboBoxX() {
        return comboBoxX;
    }
    public ComboBox getComboBoxY() {
        return comboBoxY;
    }

    public BorderPane getLayout(){
        return testLayout;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public void viderNuageDePoints() {
        scatterChart.getData().clear();
        seriesMap.clear();
    }

    @Override
    public ControleurAjoutPoint getControleurAp() {
        return controleurAp;
    }

    @Override
    public ControleurChargerCSV getControleurCSV() {
        return controleurCSV;
    }

    @Override
    public ControleurDistance getControleurDist() {
        return controleurDistance;
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof Pokemon) {
            Pokemon pokemon = (Pokemon) data;
            pokemonList.add(pokemon);
            controleur.mettreAJourNuageDePoints();
        }
    }
}
