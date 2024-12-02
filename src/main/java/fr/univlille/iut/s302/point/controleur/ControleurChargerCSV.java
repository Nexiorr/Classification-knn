package fr.univlille.iut.s302.point.controleur;

import fr.univlille.iut.s302.point.modele.ChargementDonnees;

import fr.univlille.iut.s302.point.vue.VueIris;
import fr.univlille.iut.s302.point.vue.VuePoint;
import fr.univlille.iut.s302.point.vue.VuePokemon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.SystemColor.desktop;

public class ControleurChargerCSV {
    private final ChargementDonnees modele;
    private final List<VuePoint> vues;

    public ControleurChargerCSV(ChargementDonnees modele, List<VuePoint> vues) {
        this.modele = modele;
        this.vues = vues;
        for(VuePoint vp : vues){
            vp.setControleurCSV(this);
        }
    }

    public void clearVues(){
        vues.clear();
    }

    public void ouvrirFenetreChargerCSV() {
        // Créer une nouvelle fenêtre (Stage)
        Stage fenetreAjout = creerFenetre();

        // Créer une grille pour organiser les champs
        GridPane grid = configurerGrille(10, 10, new Insets(10));

        // Création d'une ComboBox pour sélectionner le fichier CSV
        File dossier = new File("data");
        ComboBox<String> filesComboBox = creerComboBoxSelectionFichierCSV(dossier);

        ComboBox<String> objetComboBox = new ComboBox<>();
        objetComboBox.getItems().add("Iris");
        objetComboBox.getItems().add("Pokemon");
        objetComboBox.setValue(objetComboBox.getItems().get(0));


        //Création du FileChooser
        FileChooser fileChooser = new FileChooser();

        // Bouton pour écraser ou non les données précédentes
        RadioButton ecraserButton = new RadioButton("Ecraser les données précédentes");

        Button ajouterButton = new Button("Valider");


        // Bouton pour valider l'ajout
        Button choisirUnFichier = new Button("Charger un fichier");


        // Bouton pour valider l'ajout
        Button choisirPlusieurFichier = new Button("Charger plusieurs fichiers");

        // Ajout des éléments à la grille
        grid.add(new Label("Choisisez le type de donnée à ajouter"), 0, 0, 2, 1);
        grid.add(objetComboBox, 1, 1);
        grid.add(new Label("Mettez le fichier CSV que vous souhaitez charger dans le répertoire 'data'"), 0, 2, 2, 1);
        grid.add(new Label("Fichier CSV :"), 0, 3);
        grid.add(filesComboBox, 1, 4);
        grid.add(ecraserButton, 1, 5);
        grid.add(ajouterButton, 1, 6);
        grid.add(choisirUnFichier, 1, 7);
        grid.add(choisirPlusieurFichier, 1, 8);

        // Action lors du clic sur le bouton "Ajouter"
        boutonAjouter(ajouterButton, filesComboBox, dossier, ecraserButton, fenetreAjout, objetComboBox);

        boutonChoisirUnFichier(fileChooser,choisirUnFichier,ecraserButton);

        boutonChoisirPlusieurFichier(fileChooser,choisirPlusieurFichier,ecraserButton);
        // Créer la scène et l'afficher
        Scene scene = new Scene(grid, 500, 250);
        String cssFilePath = "data/css/style.css";
        File cssFile = new File(cssFilePath);
        scene.getStylesheets().add(cssFile.toURI().toString());
        fenetreAjout.setScene(scene);
        fenetreAjout.showAndWait();
    }

    private Stage creerFenetre() {
        Stage fenetreAjout = new Stage();
        fenetreAjout.initModality(Modality.APPLICATION_MODAL);
        fenetreAjout.setTitle("Ajouter un nouveau CSV");
        return fenetreAjout;
    }

    private GridPane configurerGrille(double vGap, double hGap, Insets padding) {
        GridPane grid = new GridPane();
        grid.setVgap(vGap);
        grid.setHgap(hGap);
        grid.setPadding(padding); // Ajout du padding autour de la grille
        return grid;
    }

    private ComboBox<String> creerComboBoxSelectionFichierCSV(File dossier) {
        ComboBox<String> filesComboBox = new ComboBox<>();

        if (dossier.exists() && dossier.isDirectory()) {
            File[] fichiers = dossier.listFiles((dir, nom) -> nom.toLowerCase().endsWith(".csv"));
            if (fichiers != null) {
                for (File fichier : fichiers) {
                    filesComboBox.getItems().add(fichier.getName());  // Ajouter les noms des fichiers
                }
            }
        }

        if (!filesComboBox.getItems().isEmpty()) {
            filesComboBox.setValue(filesComboBox.getItems().get(0));  // Sélectionner le premier fichier par défaut
        }
        return filesComboBox;
    }

    public List<VuePoint> getVues() {
        return vues;
    }

    private void boutonAjouter(Button ajouterButton, ComboBox<String> filesComboBox, File dossier, RadioButton ecraserButton, Stage fenetreAjout, ComboBox<String> objetComboBox) {
        ajouterButton.setOnAction(e -> {
            try {
                String fichierSelectionne = filesComboBox.getValue();  // Récupérer le nom du fichier sélectionné
                File fichier = new File(dossier, fichierSelectionne);

                if(ecraserButton.isSelected()){
                    if(!vues.get(0).getClass().getSimpleName().substring(3).equals(objetComboBox.getValue())){
                        ChargementDonnees c = vues.get(0).getDonnees();
                        for(VuePoint vue : vues){
                            c.detach(vue);
                        }
                        vues.clear();
                        if(objetComboBox.getValue().equals("Iris")){
                            VuePoint vue = new VuePokemon(c);
                            c.attach(vue);
                            vues.add(new VueIris(c));
                            vue.setControleur(new Controleur(c,vues));
                            vue.setControleurCSV(new ControleurChargerCSV(c,vues));
                            vue.setControleurAp(new ControleurAjoutPoint(c,vues));
                            vue.setControleurDistance(new ControleurDistance(c,vues));
                        }else if (objetComboBox.getValue().equals("Pokemon")){
                            VuePoint vue = new VuePokemon(c);
                            c.attach(vue);
                            vues.add(new VuePokemon(c));
                            vue.setControleur(new Controleur(c,vues));
                            vue.setControleurCSV(new ControleurChargerCSV(c,vues));
                            vue.setControleurAp(new ControleurAjoutPoint(c,vues));
                            vue.setControleurDistance(new ControleurDistance(c,vues));
                        }
                        vues.get(0).initUI();
                        Controleur.chargerDonnees(fichier.getAbsolutePath());
                    }else {
                        // Vider le graphe précédent
                        for(VuePoint vue : vues){
                            vue.viderNuageDePoints();
                            vue.setList(new ArrayList<>());
                        }
                        // Charger les nouvelles données
                        Controleur.chargerDonnees(fichier.getAbsolutePath(), objetComboBox.getValue());
                    }
                }else {
                    System.out.println("//////////////////////");
                    System.out.println(vues.get(0).getClass().getSimpleName().substring(3));
                    System.out.println("//////////////////////");
                    if(!vues.get(0).getClass().getSimpleName().substring(3).equals(objetComboBox.getValue())){
                        ChargementDonnees c = vues.get(0).getDonnees();
                        for(VuePoint vue : vues){
                            c.detach(vue);
                        }
                        vues.clear();
                        if(objetComboBox.getValue().equals("Iris")){
                            VuePoint vue = new VuePokemon(c);
                            c.attach(vue);
                            vues.add(new VueIris(c));
                            vue.setControleur(new Controleur(c,vues));
                            vue.setControleurCSV(new ControleurChargerCSV(c,vues));
                            vue.setControleurAp(new ControleurAjoutPoint(c,vues));
                            vue.setControleurDistance(new ControleurDistance(c,vues));
                        }else if (objetComboBox.getValue().equals("Pokemon")){
                            VuePoint vue = new VuePokemon(c);
                            c.attach(vue);
                            vues.add(new VuePokemon(c));
                            vue.setControleur(new Controleur(c,vues));
                            vue.setControleurCSV(new ControleurChargerCSV(c,vues));
                            vue.setControleurAp(new ControleurAjoutPoint(c,vues));
                            vue.setControleurDistance(new ControleurDistance(c,vues));
                        }
                        vues.get(0).initUI();
                        Controleur.chargerDonnees(fichier.getAbsolutePath());
                    }else {
                        // Charger les nouvelles données
                        Controleur.chargerDonnees(fichier.getAbsolutePath(), objetComboBox.getValue());
                    }
                }

                // Fermer la fenêtre d'ajout
                fenetreAjout.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void boutonChoisirUnFichier(FileChooser fileChooser, Button openSingleButton, RadioButton ecraserButton) {
        openSingleButton.setOnAction(e -> {
            try {
                Stage fenetreUnFichier = new Stage();
                fenetreUnFichier.initModality(Modality.APPLICATION_MODAL);
                fenetreUnFichier.setTitle("Choisir Un Fichier CSV");

                File file = fileChooser.showOpenDialog(fenetreUnFichier);
                if(ecraserButton.isSelected()){
                    // Vider le graphe précédent
                    for(VuePoint vue : vues){
                        vue.viderNuageDePoints();
                        vue.setList(new ArrayList<>());
                    }
                    // Charger les nouvelles données
                    Controleur.chargerDonnees(file.getAbsolutePath());
                }else {
                    // Charger les nouvelles données
                    Controleur.chargerDonnees(file.getAbsolutePath());

                }
                fenetreUnFichier.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // Méthode pour choisir plusieurs fichiers
    private void boutonChoisirPlusieurFichier(FileChooser fileChooser, Button openMultipleButton, RadioButton ecraserButton) {
        openMultipleButton.setOnAction(e -> {
            try {
                Stage fenetrePlusieursFichier = new Stage();
                fenetrePlusieursFichier.initModality(Modality.APPLICATION_MODAL);
                fenetrePlusieursFichier.setTitle("Choisir Plusieurs Fichier CSV");
                List<File> fileList = fileChooser.showOpenMultipleDialog(fenetrePlusieursFichier);
                if(ecraserButton.isSelected()){
                    // Vider le graphe précédent
                    for(VuePoint vue : vues){
                        vue.viderNuageDePoints();
                        vue.setList(new ArrayList<>());
                    }
                    // Charger les nouvelles données
                    for (File file : fileList){
                        Controleur.chargerDonnees(file.getAbsolutePath());
                    }
                }
                fenetrePlusieursFichier.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    private void ajouterElementsGrille(GridPane grid, ComboBox<String> filesComboBox, Button ajouterButton, RadioButton ecraserButton) {
        grid.add(new Label("Mettez le fichier CSV que vous souhaitez charger dans le répertoire 'data'"), 0, 0, 2, 1);
        grid.add(new Label("Fichier CSV :"), 0, 1);
        grid.add(filesComboBox, 1, 1);
        grid.add(ecraserButton, 1, 2);
        grid.add(ajouterButton, 1, 3);
    }
}
