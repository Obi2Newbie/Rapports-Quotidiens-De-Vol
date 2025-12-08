import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RapportQuotidienVol {

    private List<Declaration> declarations = new ArrayList<>();

    private String identifiant;

    private Date DateRapport;

    public void AjouterDeclaration(Declaration declaration) {
        this.declarations.add(declaration);
    }

    public List<Declaration> getNouvellesDeclarations(){
        return this.declarations.stream()
                .filter(declaration -> declaration.getDateCreation().after(this.DateRapport))
                .toList();
    }

    public List<Declaration> getDeclarationsMisesAJour(){
        return this.declarations.stream()
                .filter(declaration -> declaration.getDerniereModifiaction().after(declaration.getDateCreation()))
                .toList();
    }

    public List<Declaration> getDeclarationsResolues(){
        return this.declarations.stream()
                .filter(declaration -> declaration.getEtat() == EtatDeclaration.RESOLUE)
                .toList();
    }
}
