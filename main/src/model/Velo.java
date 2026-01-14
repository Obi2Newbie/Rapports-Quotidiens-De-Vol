package model;

public class Velo extends ProprieteVolee {
    
    private String numeroSerie;

    public Velo(String couleur, String marque, String numeroSerie) {
        super(couleur, marque);
        this.numeroSerie = numeroSerie;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }
}
