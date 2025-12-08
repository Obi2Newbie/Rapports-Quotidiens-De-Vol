public class AgentPolicier {
    private String identifiant;

    private String motDePasse;

    public AgentPolicier(String identifiant, String motDePasse) {
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
    }

    public boolean authentifier(String identifiant, String motDePasse) {
        if (this.identifiant.equals(identifiant) && this.motDePasse.equals(motDePasse)){
            System.out.println("Authentification réussie.");
            return true;
        }
        System.out.println("Échec de l'authentification.");
        return false;
    }

    public RapportQuotidienVol creerRapportQuotidienVol() {
        return new RapportQuotidienVol();
    }

    public void modifierEtatDeclaration(Declaration declaration, EtatDeclaration nouvelEtat) {
        declaration.setEtat(nouvelEtat);
        System.out.println("État de la déclaration modifié à : " + nouvelEtat);
    }
}
