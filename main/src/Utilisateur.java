import java.util.Date;

public abstract class Utilisateur {

    protected String numeroCNI;

    protected String nom;

    protected String prenom;

    protected String adresse ;

    protected String telephone;

    public Declaration creerDedclaration(Date dateVol, String heureVol, Role roleUtilisateur) {
        return new Declaration(dateVol, heureVol, roleUtilisateur, this);
    }
}
