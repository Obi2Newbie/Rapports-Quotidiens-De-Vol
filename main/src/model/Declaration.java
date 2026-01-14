package model;

import enums.EtatDeclaration;
import enums.Role;
import observer.Observer;

import java.util.Date;

public class Declaration {

    private static int compteur = 0;

    private int identifiant;

    private Date dateCreation;

    private Date derniereModifiaction;

    private Date dateVol;

    private String heureVol;

    private EtatDeclaration etat;

    private Role roleUtilisateur;

    private Utilisateur utilisateur;

    private ProprieteVolee proprieteVolee;

    private Lieu lieu;

    public Declaration(Date dateVol, String heureVol, Role roleUtilisateur, Utilisateur utilisateur, ProprieteVolee proprieteVolee, Lieu lieu) {
        this.roleUtilisateur = roleUtilisateur;
        this.identifiant = ++compteur;
        this.dateCreation = new Date();
        this.derniereModifiaction = new Date();
        this.dateVol = dateVol;
        this.heureVol = heureVol;
        this.etat = EtatDeclaration.EN_COURS;
        this.utilisateur = utilisateur;
        this.proprieteVolee = proprieteVolee;
        this.lieu = lieu;
    }

    public void setEtat(EtatDeclaration nouvelEtat) {
        this.etat = nouvelEtat;
        this.derniereModifiaction = new Date();

        if (nouvelEtat == EtatDeclaration.RESOLUE && utilisateur instanceof Observer) {
            ((Observer) utilisateur).notifier("Votre déclaration #" + identifiant + " a été résolue.");
        }
    }

    public EtatDeclaration getEtat() {
        return etat;
    }

    public Date getDerniereModifiaction() {
        return derniereModifiaction;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public int getIdentifiant() {
        return identifiant;
    }

}
