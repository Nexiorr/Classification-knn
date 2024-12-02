package fr.univlille.iut.s302.point.modele;

import java.util.Arrays;


public class Pokemon {

    private String name;
    private double[] stats;
    private boolean isLegend;
    private Type[] type;

    public Pokemon(String name,double attack,double base_egg_steps,double capture_rate,double defense,double experience_growth,double hp,double sp_attack,double sp_defense,double speed, Type type1, Type type2,boolean isLegend){
        stats = new double[9];

        this.stats[0] = attack;
        this.stats[1] = base_egg_steps;
        this.stats[2] = capture_rate;
        this.stats[3] = defense;
        this.stats[4] = experience_growth;
        this.stats[5] = hp;
        this.stats[6] = sp_attack;
        this.stats[7] = sp_defense;
        this.stats[8] = speed;

        this.type = new Type[]{ type1,  type2};
        this.name = name;
        this.isLegend = isLegend;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", stats=" + Arrays.toString(stats) +
                ", isLegend=" + isLegend +
                ", type1=" + type[0] +
                ", type2=" + type[1] +
                '}';
    }

    public String getName() {
        return name;
    }

    public double[] getStats() {
        return stats;
    }

    public boolean isLegend() {
        return isLegend;
    }

    public Type getType1() {
        return type[0];
    }

    public Type getType2() {
        return type[1];
    }

    public Type[] getType() {
        return type;
    }

    public double getAttack(){
        return stats[0];
    }
    public double getBaseEggSteps(){
        return stats[1];
    }
    public double getCaptureRate(){
        return stats[2];
    }
    public double getDefense(){
        return stats[3];
    }
    public double getExperienceGrowth(){
        return stats[4];
    }
    public double getHp(){
        return stats[5];
    }
    public double getSpAttack(){
        return stats[6];
    }
    public double getSpDefense(){
        return stats[7];
    }
    public double getSpeed(){
        return stats[8];
    }
    public boolean getIsLegend(){
        return this.isLegend;
    }


    public void setType(Type type) {
        this.type[0] = type;
    }

    public void setType(Type[] types) {
        this.type[0] = types[0];
        this.type[1] = types[1];
    }

}
