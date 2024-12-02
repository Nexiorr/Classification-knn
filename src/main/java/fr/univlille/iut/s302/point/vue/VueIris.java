package fr.univlille.iut.s302.point.vue;


import fr.univlille.iut.s302.point.controleur.*;
import fr.univlille.iut.s302.point.modele.*;
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


/**
 * La classe VuePoint gère l'affichage du nuage de points des données des iris dans une interface graphique.
 * Elle observe les changements des données et met à jour l'affichage.
 * Cette classe implémente l'interface {@link Observer} pour être notifiée des changements de données dans {@link ChargementDonnees}.
 */

public class VueIris extends Stage implements Observer, VuePoint {

    //Map données
    private Map<Fleur, Color> couleursCategories;
    private Map<Fleur, XYChart.Series<Number, Number>> seriesMap;

    //Données et controleurs
    private ChargementDonnees donnees;
    private Controleur controleur;
    private ControleurAjoutPoint controleurAp;
    private ControleurChargerCSV controleurCSV;
    private ControleurDistance controleurDistance;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private List<Iris> irisList;

    //UI
    private ScatterChart<Number, Number> scatterChart;
    private ComboBox<String> comboBoxX;
    private ComboBox<String> comboBoxY;
    private BorderPane testLayout;

    /** Variables pour gérer la sélection d'un point */
    private Circle dernierCercleSelectionne = null;
    private Color derniereCouleur = null;



    public VueIris(ChargementDonnees donnees) {
        this.couleursCategories = new HashMap<>();
        this.seriesMap = new HashMap<>();
        this.donnees = donnees;
        this.irisList = new ArrayList<>();
        this.donnees.attach(this);  // S'attacher à l'instance de ChargementDonnees

        couleursCategories.put(Fleur.SETOSA, Color.BLUE);
        couleursCategories.put(Fleur.VERSICOLOR, Color.RED);
        couleursCategories.put(Fleur.VIRGINICA, Color.GREEN);
    }

    public ChargementDonnees getDonnees() {
        return donnees;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void setList(List list) {
        this.irisList = list;
    }

    @Override
    public List getList() {
        return irisList;
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
        ajouterPointButton.setOnAction(e -> controleurAp.ouvrirFenetreAjoutPointIris());

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
        xAxis.setLabel("Sepal Length");
        yAxis = new NumberAxis();
        yAxis.setLabel("Sepal Width");

        // Initialisation du ScatterChart
        scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Nuage de Points Iris");
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

    public VBox createComboBoxVBOX(){
        // Initialisation des ComboBox pour les axes
        comboBoxX = new ComboBox<>();
        comboBoxX.getItems().addAll("Sepal Length", "Sepal Width", "Petal Length", "Petal Width");
        comboBoxX.setValue("Sepal Length");

        comboBoxY = new ComboBox<>();
        comboBoxY.getItems().addAll("Sepal Length", "Sepal Width", "Petal Length", "Petal Width");
        comboBoxY.setValue("Sepal Width");

        comboBoxX.setOnAction(e -> controleur.mettreAJourNuageDePoints());
        comboBoxY.setOnAction(e -> controleur.mettreAJourNuageDePoints());

        VBox comboVBox = new VBox(15);
        comboVBox.getChildren().addAll(comboBoxX, comboBoxY);
        return comboVBox;
    }

    public void afficherPoint(Object ir, double x, double y, Color couleur) {
        Iris iris = (Iris) ir;
        XYChart.Series<Number, Number> series = creerSerie(iris);

        Circle circle = creerCercle(couleur, iris);
        XYChart.Data<Number, Number> point = new XYChart.Data<>(x, y, circle);
        point.setNode(circle);
        series.getData().add(point);
    }

    private XYChart.Series<Number, Number> creerSerie(Iris iris) {
        return seriesMap.computeIfAbsent(iris.getVariety(), cat -> {
            XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
            newSeries.setName(cat.toString());
            scatterChart.getData().add(newSeries);
            return newSeries;
        });
    }

    private Circle creerCercle(Color couleur, Iris iris) {
        Circle circle = new Circle(4);
        circle.setFill(couleur);
        circle.setOnMouseClicked(event -> afficherValeursIris(iris, circle));
        return circle;
    }

    private void afficherValeursIris(Iris iris, Circle cercle) {
        restaurerDerniereCouleur();
        sauvegarderSelection(cercle);

        Stage fenetreDetail = creerFenetreDetails(iris, cercle);
        fenetreDetail.show();
    }

    private void restaurerDerniereCouleur() {
        if (dernierCercleSelectionne != null && derniereCouleur != null) {
            dernierCercleSelectionne.setFill(derniereCouleur);
        }
    }

    private void sauvegarderSelection(Circle cercle) {
        dernierCercleSelectionne = cercle;
        derniereCouleur = (Color) cercle.getFill();
        cercle.setFill(Color.BLACK);
    }

    private Stage creerFenetreDetails(Iris iris, Circle cercle) {
        Stage fenetreDetail = new Stage();
        fenetreDetail.setTitle("Détails de l'Iris");

        VBox informationIris = createIrisDetailInformation(iris);
        ajouterBoutonFermer(informationIris, cercle, fenetreDetail);

        Scene scene = new Scene(informationIris, 300, 200);
        fenetreDetail.setScene(scene);
        return fenetreDetail;
    }

    private void ajouterBoutonFermer(VBox layout, Circle cercle, Stage fenetreDetail) {
        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> {
            cercle.setFill(derniereCouleur);
            fenetreDetail.close();
        });
        layout.getChildren().add(closeButton);
    }

    public VBox createIrisDetailInformation(Iris iris){
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        layout.getChildren().addAll(
                new Text("Sepal Length: " + iris.getSepalLength()),
                new Text("Sepal Width: " + iris.getSepalWidth()),
                new Text("Petal Length: " + iris.getPetalLength()),
                new Text("Petal Width: " + iris.getPetalWidth()),
                new Text("Variété: " + iris.getVariety())
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

    public List<Iris> getIrisList() {
        return irisList;
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

    public void viderNuageDePoints() {
        scatterChart.getData().clear();
        seriesMap.clear();
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof Iris) {
            Iris iris = (Iris) data;
            irisList.add(iris);
            controleur.mettreAJourNuageDePoints();
        }
    }
}
