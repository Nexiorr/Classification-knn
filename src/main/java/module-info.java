module point {
    requires javafx.controls;
    requires javafx.swing;
    requires java.sql;
    requires com.opencsv;
    opens fr.univlille.iut.s302.point;
    exports fr.univlille.iut.s302.point.modele;

}