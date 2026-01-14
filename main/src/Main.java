import enums.EtatDeclaration;
import enums.Role;
import model.*;
import persistence.JsonDataManager;
import service.AgentPolicier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Créer des utilisateurs
        Victime victime1 = new Victime("CNI123", "Dupont", "Jean", "12 rue de Paris", "0612345678");
        Victime victime2 = new Victime("CNI456", "Martin", "Sophie", "25 avenue Victor Hugo", "0698765432");
        Temoin temoin1 = new Temoin("CNI789", "Bernard", "Pierre", "8 boulevard Haussmann", "0654321098");

        // Créer des lieux
        Lieu lieu1 = new Lieu("12", "rue de Paris", "Paris", "75001");
        Lieu lieu2 = new Lieu("45", "avenue des Champs-Élysées", "Paris", "75008");
        Lieu lieu3 = new Lieu("3", "place de la République", "Lyon", "69001");

        // Créer des objets volés
        Vehicule vehicule1 = new Vehicule("Rouge", "Peugeot", "AB-123-CD");
        Vehicule vehicule2 = new Vehicule("Noir", "Renault", "EF-456-GH");
        Velo velo1 = new Velo("Bleu", "Decathlon", "SN-VTT-2024-001");

        // Créer des déclarations
        Declaration decl1 = victime1.creerDedclaration(new Date(), "14:00", Role.VICTIME, vehicule1, lieu1);
        Declaration decl2 = victime2.creerDedclaration(new Date(), "09:30", Role.VICTIME, velo1, lieu2);
        Declaration decl3 = temoin1.creerDedclaration(new Date(), "22:15", Role.TEMOIN, vehicule2, lieu3);

        // Agent policier modifie l'état d'une déclaration (test Observer pattern)
        AgentPolicier agent = new AgentPolicier("agent1", "pwd");
        agent.modifierEtatDeclaration(decl1, EtatDeclaration.RESOLUE);

        // Sauvegarder toutes les déclarations dans DATA
        List<Declaration> declarations = new ArrayList<>();
        declarations.add(decl1);
        declarations.add(decl2);
        declarations.add(decl3);
        JsonDataManager.sauvegarderDeclarations(declarations);
        System.out.println("Déclarations sauvegardées dans DATA/declarations.json");

        // Sauvegarder les victimes
        List<Victime> victimes = new ArrayList<>();
        victimes.add(victime1);
        victimes.add(victime2);
        JsonDataManager.sauvegarderVictimes(victimes);
        System.out.println("Victimes sauvegardées dans DATA/victimes.json");

        // Sauvegarder les témoins
        List<Temoin> temoins = new ArrayList<>();
        temoins.add(temoin1);
        JsonDataManager.sauvegarderTemoins(temoins);
        System.out.println("Témoins sauvegardés dans DATA/temoins.json");

        // Charger et afficher les déclarations depuis le fichier JSON
        System.out.println("\n--- Chargement des déclarations depuis DATA ---");
        List<Declaration> declarationsChargees = JsonDataManager.chargerDeclarations();
        for (Declaration d : declarationsChargees) {
            System.out.println("Déclaration #" + d.getIdentifiant() + " - État: " + d.getEtat());
        }
    }
}