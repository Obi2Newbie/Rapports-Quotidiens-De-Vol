package model;

public abstract class ProprieteVolee {
    
    protected String couleur;

    protected String marque;

    public ProprieteVolee(String couleur, String marque) {
        this.couleur = couleur;
        this.marque = marque;
    }

    public ProprieteVolee(String couleur, String marque, String descriptionGenerale) {
        this.couleur = couleur;
        this.marque = marque;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getMarque() {
        return marque;
    }

}
