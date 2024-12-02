package fr.univlille.iut.s302.point.controleur;

import fr.univlille.iut.s302.point.modele.ChargementDonnees;
import fr.univlille.iut.s302.point.modele.Fleur;
import fr.univlille.iut.s302.point.modele.Iris;
import fr.univlille.iut.s302.point.modele.Pokemon;
import fr.univlille.iut.s302.point.vue.VuePoint;

import java.util.*;

public class ControleurDistance {

    private final ChargementDonnees modele;
    private final List<VuePoint> vues;

    public ControleurDistance(ChargementDonnees modele, List<VuePoint> vues) {
        this.modele = modele;
        this.vues = vues;
        for(VuePoint vp : vues){
            vp.setControleurDistance(this);
        }
    }

    public static Double distanceEuclidienne(Object obj1, Object obj2, VuePoint vue) {
        String axeX = vue.getAxeXSelection();
        String axeY = vue.getAxeYSelection();

        // Trouver les min et max pour normalisation
        double[] minMaxX = getMinMax(vue, axeX);
        double[] minMaxY = getMinMax(vue, axeY);

        // Obtenir et normaliser les valeurs
        double x1 = normalize(getAxeValue(obj1, axeX), minMaxX[0], minMaxX[1]);
        double y1 = normalize(getAxeValue(obj1, axeY), minMaxY[0], minMaxY[1]);
        double x2 = normalize(getAxeValue(obj2, axeX), minMaxX[0], minMaxX[1]);
        double y2 = normalize(getAxeValue(obj2, axeY), minMaxY[0], minMaxY[1]);

        return Math.sqrt(Math.pow(x2 - x1, 2)
                + Math.pow(y2 - y1, 2));
    }

    public static Double distanceManhattan(Object obj1, Object obj2, VuePoint vue) {
        String axeX = vue.getAxeXSelection();
        String axeY = vue.getAxeYSelection();

        // Trouver les min et max pour normalisation
        double[] minMaxX = getMinMax(vue, axeX);
        double[] minMaxY = getMinMax(vue, axeY);

        // Obtenir et normaliser les valeurs
        double x1 = normalize(getAxeValue(obj1, axeX), minMaxX[0], minMaxX[1]);
        double y1 = normalize(getAxeValue(obj1, axeY), minMaxY[0], minMaxY[1]);
        double x2 = normalize(getAxeValue(obj2, axeX), minMaxX[0], minMaxX[1]);
        double y2 = normalize(getAxeValue(obj2, axeY), minMaxY[0], minMaxY[1]);

        return Math.abs(x1 - x2)
                + Math.abs(y1 - y2);
    }

    private static double[] getMinMax(VuePoint vue, String axe) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        // Parcourir tous les objets pour trouver min et max
        for (Object obj : vue.getList()) {
            double value = getAxeValue(obj, axe);
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        return new double[]{min, max};
    }

    private static double normalize(double value, double min, double max) {
        if (max - min == 0) {
            return 0;

        }
        return (value - min) / (max - min);
    }


    public static Object knn(Object newObj, int k, String typeDist, VuePoint vue) {
        double dist;
        List<Object> data = vue.getList(); // Liste contenant des objets (Iris ou Pokemon)
        List<Double> distances = new ArrayList<>();
        List<Object> categories = new ArrayList<>(); // Fleur pour Iris, Type pour Pokemon

        // Calculer les distances et collecter les catégories
        for (Object obj : data) {
            if (obj != newObj) {
                if (typeDist.equals("euclidienne")) {
                    dist = distanceEuclidienne(newObj, obj, vue);
                } else {
                    dist = distanceManhattan(newObj, obj, vue);
                }
                distances.add(dist);
                categories.add(getCategory(obj)); // Récupère la Fleur ou le Type
            }
        }

        // Identifier les k plus proches voisins
        int[] kNearestId = kSmallestId(distances, k);

        // Compter les occurrences des catégories
        Map<Object, Integer> categoryCounts = new HashMap<>();
        for (int id : kNearestId) {
            Object category = categories.get(id);
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }

        // Trouver la catégorie la plus fréquente
        Object mostFrequentCategory = null;
        int maxCount = 0;
        for (Map.Entry<Object, Integer> entry : categoryCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentCategory = entry.getKey();
            }
        }

        return mostFrequentCategory; // Retourne une Fleur ou un Type selon les données
    }

    private static int[] kSmallestId(List<Double> distances, int k) {
        int[] id = new int[k];
        boolean[] used = new boolean[distances.size()];

        for (int i = 0; i < k; i++) {
            double minDist = Double.MAX_VALUE;
            int minId = -1;
            for (int j = 0; j < distances.size(); j++) {
                if (!used[j] && distances.get(j) < minDist) {
                    minDist = distances.get(j);
                    minId = j;
                }
            }
            id[i] = minId;
            used[minId] = true;
        }
        return id;
    }

    private static Object getCategory(Object obj) {
        if (obj.getClass() == Iris.class) {
            return ((Iris) obj).getVariety(); // Retourne une Fleur pour les Iris
        } else if (obj.getClass() == Pokemon.class) {
            return ((Pokemon) obj).getType(); // Retourne un Type pour les Pokemons
        } else {
            throw new IllegalArgumentException("Unsupported object type: " + obj.getClass());
        }
    }

    protected static double getAxeValue(Object obj, String axe) {
        if (obj.getClass() == Iris.class) {
            Iris iris = (Iris) obj;
            switch (axe) {
                case "Sepal Length":
                    return iris.getSepalLength();
                case "Sepal Width":
                    return iris.getSepalWidth();
                case "Petal Length":
                    return iris.getPetalLength();
                case "Petal Width":
                    return iris.getPetalWidth();
                default:
                    return 0;
            }
        } else if (obj.getClass() == Pokemon.class) {
            Pokemon pokemon = (Pokemon) obj;
            switch (axe) {
                case "Attack":
                    return pokemon.getAttack();
                case "Defense":
                    return pokemon.getDefense();
                case "Speed":
                    return pokemon.getSpeed();
                case "HP":
                    return pokemon.getHp();
                case "Special Attack":
                    return pokemon.getSpAttack();
                case "Special Defense":
                    return pokemon.getSpDefense();
                case "Base Egg Steps":
                    return pokemon.getBaseEggSteps();
                case "Capture Rate":
                    return pokemon.getCaptureRate();
                case "Experience Growth":
                    return pokemon.getExperienceGrowth();
                default:
                    return 0;
            }
        } else {
            throw new IllegalArgumentException("Unsupported object type: " + obj.getClass());
        }
    }

    private void calculerAmplitudes(int amplitudeX, int amplitudeY, int xMax, int xMin, int yMax, int yMin) {
        amplitudeX = xMax - xMin;
        amplitudeY = yMax - yMin;
    }
}

/*
    public static Fleur knn(Iris newIris, int k, String typeDist, VuePoint vue){
        double dist;
        List<Iris> data = vue.getList();
        List<Double> distances = new ArrayList<>();
        List<Fleur> varieties = new ArrayList<>();
        for(Iris iris : data) {
            if (iris != newIris) {
                if (typeDist == "euclidienne") {
                    dist = distanceEuclidienne(newIris, iris, vue);
                } else {
                    dist = distanceManhattan(newIris, iris, vue);
                }
                distances.add(dist);
                varieties.add(iris.getVariety());
            }
        }
        int[] kNearestId = kSmallestId(distances, k);

        //Compter les occurences des variétés des knn voisins
        Map<Fleur, Integer> nbVariety = new HashMap<>();
        for(int id : kNearestId){
            Fleur variety = varieties.get(id);
            nbVariety.put(variety, nbVariety.getOrDefault(variety, 0) + 1);
        }

        // Trouver la variété la plus fréquente
        Fleur varietyPlusPresente = null;
        int maxCount = 0;
        for(Map.Entry<Fleur, Integer> entry : nbVariety.entrySet()){
            if(entry.getValue() > maxCount){
                maxCount = entry.getValue();
                varietyPlusPresente = entry.getKey();
            }
        }

        return varietyPlusPresente;
    }

    private static int[] kSmallestId(List<Double> distances, int k){
        int[] id = new int[k];
        boolean[] used = new boolean[distances.size()];

        for(int i = 0; i < k; i += 1){
            double minDist = Double.MAX_VALUE;
            int minId = -1;
            for(int j = 0; j < distances.size(); j += 1){
                if(!used[j] && distances.get(j) < minDist){
                    minDist = distances.get(j);
                    minId = j;
                }
            }
            id[i] = minId;
            used[minId] = true;
        }
        return id;
    }
    */
