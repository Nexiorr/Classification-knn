package fr.univlille.iut.s302.point.modele;

import com.opencsv.bean.CsvBindByName;

public class DonneesBrutPokemon extends FormatDonneeBrut{
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "attack")
    private double attack;
    @CsvBindByName(column = "base_egg_steps")
    private double base_egg_steps;
    @CsvBindByName(column = "capture_rate")
    private double capture_rate;
    @CsvBindByName(column = "defense")
    private double defense;
    @CsvBindByName(column = "experience_growth")
    private double experience_growth;
    @CsvBindByName(column = "hp")
    private double hp;
    @CsvBindByName(column = "sp_attack")
    private double sp_attack;
    @CsvBindByName(column = "sp_defense")
    private double sp_defense;
    @CsvBindByName(column = "speed")
    private double speed;
    @CsvBindByName(column = "type1")
    private Type type1;
    @CsvBindByName(column = "type2")
    private Type type2;
    @CsvBindByName(column = "is_legendary")
    private boolean is_legendary;

    @Override
    public String toString() {
        return "DonneeBrutPokemon{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", base_egg_steps=" + base_egg_steps +
                ", capture_rate=" + capture_rate +
                ", defense=" + defense +
                ", experience_growth=" + experience_growth +
                ", hp=" + hp +
                ", sp_attack=" + sp_attack +
                ", sp_defense=" + sp_defense +
                ", speed=" + speed +
                ", type1=" + type1 +
                ", type2=" + type2 +
                ", is_legendary=" + is_legendary +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getBase_egg_steps() {
        return base_egg_steps;
    }

    public void setBase_egg_steps(double base_egg_steps) {
        this.base_egg_steps = base_egg_steps;
    }

    public double getCapture_rate() {
        return capture_rate;
    }

    public void setCapture_rate(double capture_rate) {
        this.capture_rate = capture_rate;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getExperience_growth() {
        return experience_growth;
    }

    public void setExperience_growth(double experience_growth) {
        this.experience_growth = experience_growth;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getSp_attack() {
        return sp_attack;
    }

    public void setSp_attack(double sp_attack) {
        this.sp_attack = sp_attack;
    }

    public double getSp_defense() {
        return sp_defense;
    }

    public void setSp_defense(double sp_defense) {
        this.sp_defense = sp_defense;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Type getType1() {
        return type1;
    }

    public void setType1(Type type1) {
        this.type1 = type1;
    }

    public Type getType2() {
        return type2;
    }

    public void setType2(Type type2) {
        this.type2 = type2;
    }

    public boolean isIs_legendary() {
        return is_legendary;
    }

    public void setIs_legendary(boolean is_legendary) {
        this.is_legendary = is_legendary;
    }
}
