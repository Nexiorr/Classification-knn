package fr.univlille.iut.s302.point.modele;

import com.opencsv.bean.CsvBindByName;

public class DonneesBrutIris extends FormatDonneeBrut{
    @CsvBindByName(column = "sepal.length")
    private double sepalLength;
    @CsvBindByName(column = "sepal.width")
    private double sepalWidth;
    @CsvBindByName(column = "petal.length")
    private double petalLength;
    @CsvBindByName(column = "petal.width")
    private double petalWidth;
    @CsvBindByName(column = "variety")
    private Fleur variety;

    @Override
    public String toString() {
        return "FormatDonneeBrut{" +
                "sepalLength='" + sepalLength + '\'' +
                ", sepalWidth='" + sepalWidth + '\'' +
                ", petalLength='" + petalLength + '\'' +
                ", petalWidth='" + petalWidth + '\'' +
                ", variety=" + variety + '\'' +
                '}' + '\n';
    }

    public double getSepalLength() {
        return sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    public Fleur getVariety() {
        return variety;
    }

    public void setSepalLength(double sepalLength) {
        this.sepalLength = sepalLength;
    }

    public void setSepalWidth(double sepalWidth) {
        this.sepalWidth = sepalWidth;
    }

    public void setPetalLength(double petalLength) {
        this.petalLength = petalLength;
    }

    public void setPetalWidth(double petalWidth) {
        this.petalWidth = petalWidth;
    }

    public void setVariety(Fleur variety) {
        this.variety = variety;
    }

}