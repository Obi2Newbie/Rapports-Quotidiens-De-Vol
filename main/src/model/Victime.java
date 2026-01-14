package model;

import observer.Observer;

public class Victime extends Utilisateur implements Observer {

    public Victime(String numeroCNI, String nom, String prenom, String adresse, String telephone) {
        this.numeroCNI = numeroCNI;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    @Override
    public void notifier(String message) {
        System.out.println("Notification pour " + nom + " " + prenom + ": " + message);
    }
}
