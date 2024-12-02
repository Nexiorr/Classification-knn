package fr.univlille.iut.s302.point;

import fr.univlille.iut.s302.point.controleur.*;
import fr.univlille.iut.s302.point.vue.VueIris;
import fr.univlille.iut.s302.point.vue.VuePokemon;
import javafx.application.Application;
import javafx.stage.Stage;
import fr.univlille.iut.s302.point.modele.ChargementDonnees;
import fr.univlille.iut.s302.point.vue.VuePoint;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        ChargementDonnees model = new ChargementDonnees();
        VuePoint vue = new VuePokemon(model);
        model.attach(vue);
        ArrayList<VuePoint> list = new ArrayList<>();
        list.add(vue);
        Controleur controleur = new Controleur(model,list);
        ControleurAjoutPoint controleurAp = new ControleurAjoutPoint(model,list);
        ControleurChargerCSV controleurCSV = new ControleurChargerCSV(model,list);
        ControleurDistance controleurDistance = new ControleurDistance(model, list);
        // Charger les données initiales (si nécessaire)
        controleur.chargerDonnees("data/pokemon_train.csv");

    }

    public static void main(String[] args) {
        launch(args);
    }
}