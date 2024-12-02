package fr.univlille.iut.s302.point.modele;

public class Iris{
    private double[] sepalPetal;
    private Fleur variety;

    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, Fleur variety){
        sepalPetal = new double[5];
        this.sepalPetal[0] = sepalLength;
        this.sepalPetal[1] = sepalWidth;
        this.sepalPetal[2] = petalLength;
        this.sepalPetal[3] = petalWidth;
        this.variety = variety;
    }

    @Override
    public String toString() {
        return "FormatDonneeBrut{" +
                "sepalLength='" + sepalPetal[0] + '\'' +
                ", sepalWidth='" + sepalPetal[1] + '\'' +
                ", petalLength='" + sepalPetal[2] + '\'' +
                ", petalWidth='" + sepalPetal[3] + '\'' +
                ", variety=" + variety +
                '}';
    }

    public double getSepalLength() {
        return sepalPetal[0];
    }

    public double getSepalWidth() {
        return sepalPetal[1];
    }

    public double getPetalLength() {
        return sepalPetal[2];
    }

    public double getPetalWidth() {
        return sepalPetal[3];
    }

    public Fleur getVariety() {
        return variety;
    }

    public void setVariety(Fleur variety) {
        this.variety = variety;
    }
}