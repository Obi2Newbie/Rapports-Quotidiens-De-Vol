package model;

import enums.Role;
import java.util.Date;

public abstract class Utilisateur {

    protected String numeroCNI;

    protected String nom;

    protected String prenom;

    protected String adresse;

    protected String telephone;

    public Declaration creerDedclaration(Date dateVol, String heureVol, Role roleUtilisateur, ProprieteVolee proprieteVolee, Lieu lieu) {
        return new Declaration(dateVol, heureVol, roleUtilisateur, this, proprieteVolee, lieu);
    }

    public void editerDeclaration(){};

    public String getNumeroCNI() {
        return numeroCNI;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }
}
