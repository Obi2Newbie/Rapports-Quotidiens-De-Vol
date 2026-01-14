package model;

public class Lieu {

    private String numeroRue;

    private String nomRue;

    private String ville;

    private String codePostal;

    public Lieu(String numeroRue, String nomRue, String ville, String codePostal) {
        this.numeroRue = numeroRue;
        this.nomRue = nomRue;
        this.ville = ville;
        this.codePostal = codePostal;
    }


    public String getNumeroRue() {
        return numeroRue;
    }

    public String getNomRue() {
        return nomRue;
    }

    public String getVille() {
        return ville;
    }

    public String getCodePostal() {
        return codePostal;
    }
}
