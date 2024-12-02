package fr.univlille.iut.s302.utils;

public interface Observer {
    void update(Observable observable);
    void update(Observable observable, Object data);
}

