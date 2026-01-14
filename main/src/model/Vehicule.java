package model;

public class Vehicule extends ProprieteVolee {
    
    private String matricule;

    public Vehicule(String couleur, String marque, String matricule) {
        super(couleur, marque);
        this.matricule = matricule;
    }


    public String getMatricule() {
        return matricule;
    }
}
