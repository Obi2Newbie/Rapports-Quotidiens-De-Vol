package service;

import enums.EtatDeclaration;
import model.Declaration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RapportQuotidienVol {

    private List<Declaration> declarations = new ArrayList<>();

    private String identifiant;

    private Date dateRapport;

    public RapportQuotidienVol() {
        this.dateRapport = new Date();
    }

    public RapportQuotidienVol(String identifiant) {
        this.identifiant = identifiant;
        this.dateRapport = new Date();
    }

    public RapportQuotidienVol(String identifiant, Date dateRapport) {
        this.identifiant = identifiant;
        this.dateRapport = dateRapport;
    }

    public void AjouterDeclaration(Declaration declaration) {
        this.declarations.add(declaration);
    }

    public List<Declaration> getNouvellesDeclarations() {
        return this.declarations.stream()
                .filter(declaration -> declaration.getDateCreation().after(this.dateRapport))
                .toList();
    }

    public List<Declaration> getDeclarationsMisesAJour() {
        return this.declarations.stream()
                .filter(declaration -> declaration.getDerniereModifiaction().after(declaration.getDateCreation()))
                .toList();
    }

    public List<Declaration> getDeclarationsResolues() {
        return this.declarations.stream()
                .filter(declaration -> declaration.getEtat() == EtatDeclaration.RESOLUE)
                .toList();
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public Date getDateRapport() {
        return dateRapport;
    }
}
