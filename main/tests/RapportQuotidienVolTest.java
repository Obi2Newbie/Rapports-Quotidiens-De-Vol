import enums.EtatDeclaration;
import enums.Role;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RapportQuotidienVol;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RapportQuotidienVolTest {

    private RapportQuotidienVol rapport;
    private Declaration declaration1;
    private Declaration declaration2;
    private Declaration declaration3;
    private Victime victime;
    private Temoin temoin;
    private Lieu lieu;
    private Vehicule vehicule;
    private Velo velo;

    @BeforeEach
    void setUp() {
        rapport = new RapportQuotidienVol();

        victime = new Victime("CNI123", "Dupont", "Jean", "12 rue", "0612345678");
        temoin = new Temoin("CNI789", "Bernard", "Paul", "8 boulevard", "0654321098");
        lieu = new Lieu("12", "rue de Paris", "Paris", "75001");
        vehicule = new Vehicule("Rouge", "Peugeot", "AB-123-CD");
        velo = new Velo("Bleu", "Decathlon", "SN-001");

        declaration1 = new Declaration(new Date(), "14:30", Role.VICTIME, victime, vehicule, lieu);
        declaration2 = new Declaration(new Date(), "15:00", Role.VICTIME, victime, velo, lieu);
        declaration3 = new Declaration(new Date(), "16:00", Role.TEMOIN, temoin, vehicule, lieu);
    }

    @Test
    void testRapportCreation() {
        assertNotNull(rapport);
        assertNotNull(rapport.getDateRapport());
        assertTrue(rapport.getDeclarations().isEmpty());
    }

    @Test
    void testRapportCreationWithIdentifiant() {
        RapportQuotidienVol rapportAvecId = new RapportQuotidienVol("RAPPORT-001");

        assertNotNull(rapportAvecId);
        assertEquals("RAPPORT-001", rapportAvecId.getIdentifiant());
        assertNotNull(rapportAvecId.getDateRapport());
    }

    @Test
    void testRapportCreationWithIdentifiantAndDate() {
        Date customDate = new Date();
        RapportQuotidienVol rapportComplet = new RapportQuotidienVol("RAPPORT-002", customDate);

        assertEquals("RAPPORT-002", rapportComplet.getIdentifiant());
        assertEquals(customDate, rapportComplet.getDateRapport());
    }

    @Test
    void testAjouterDeclaration() {
        rapport.AjouterDeclaration(declaration1);

        assertEquals(1, rapport.getDeclarations().size());
        assertTrue(rapport.getDeclarations().contains(declaration1));
    }

    @Test
    void testAjouterMultipleDeclarations() {
        rapport.AjouterDeclaration(declaration1);
        rapport.AjouterDeclaration(declaration2);

        assertEquals(2, rapport.getDeclarations().size());
        assertTrue(rapport.getDeclarations().contains(declaration1));
        assertTrue(rapport.getDeclarations().contains(declaration2));
    }

    @Test
    void testAjouterAllThreeDeclarations() {
        rapport.AjouterDeclaration(declaration1);
        rapport.AjouterDeclaration(declaration2);
        rapport.AjouterDeclaration(declaration3);

        assertEquals(3, rapport.getDeclarations().size());
    }

    @Test
    void testGetNouvellesDeclarations() throws InterruptedException {

        Date dateRapport = new Date(System.currentTimeMillis() - 2000);
        RapportQuotidienVol rapportAvecDate = new RapportQuotidienVol("RAP-001", dateRapport);

        Thread.sleep(100);


        Declaration newDecl1 = new Declaration(new Date(), "16:00", Role.VICTIME, victime, vehicule, lieu);
        Declaration newDecl2 = new Declaration(new Date(), "17:00", Role.VICTIME, victime, velo, lieu);

        rapportAvecDate.AjouterDeclaration(newDecl1);
        rapportAvecDate.AjouterDeclaration(newDecl2);

        List<Declaration> nouvelles = rapportAvecDate.getNouvellesDeclarations();

        assertEquals(2, nouvelles.size());
        assertTrue(nouvelles.contains(newDecl1));
        assertTrue(nouvelles.contains(newDecl2));
    }

    @Test
    void testGetNouvellesDeclarationsEmpty() {

        Date futureDate = new Date(System.currentTimeMillis() + 86400000);
        RapportQuotidienVol rapportFutur = new RapportQuotidienVol("RAP-002", futureDate);

        rapportFutur.AjouterDeclaration(declaration1);
        rapportFutur.AjouterDeclaration(declaration2);

        List<Declaration> nouvelles = rapportFutur.getNouvellesDeclarations();

        assertTrue(nouvelles.isEmpty());
    }

    @Test
    void testGetDeclarationsMisesAJour() throws InterruptedException {
        rapport.AjouterDeclaration(declaration1);
        rapport.AjouterDeclaration(declaration2);

        Thread.sleep(100);
        declaration1.setEtat(EtatDeclaration.RESOLUE);

        List<Declaration> misesAJour = rapport.getDeclarationsMisesAJour();

        assertEquals(1, misesAJour.size());
        assertTrue(misesAJour.contains(declaration1));
        assertFalse(misesAJour.contains(declaration2));
    }

    @Test
    void testGetDeclarationsMisesAJourMultiple() throws InterruptedException {
        rapport.AjouterDeclaration(declaration1);
        rapport.AjouterDeclaration(declaration2);
        rapport.AjouterDeclaration(declaration3);

        // Modify declarations
        Thread.sleep(100);
        declaration1.setEtat(EtatDeclaration.RESOLUE);
        declaration2.setEtat(EtatDeclaration.ARCHIVEE);

        List<Declaration> misesAJour = rapport.getDeclarationsMisesAJour();

        assertEquals(2, misesAJour.size());
        assertTrue(misesAJour.contains(declaration1));
        assertTrue(misesAJour.contains(declaration2));
        assertFalse(misesAJour.contains(declaration3));
    }

    @Test
    void testGetDeclarationsResolues() {
        declaration1.setEtat(EtatDeclaration.RESOLUE);
        declaration2.setEtat(EtatDeclaration.EN_COURS);

        rapport.AjouterDeclaration(declaration1);
        rapport.AjouterDeclaration(declaration2);

        List<Declaration> resolues = rapport.getDeclarationsResolues();

        assertEquals(1, resolues.size());
        assertTrue(resolues.contains(declaration1));
        assertFalse(resolues.contains(declaration2));
    }

    @Test
    void testGetDeclarationsResoluesToutesNonResolues() {
        declaration1.setEtat(EtatDeclaration.EN_COURS);
        declaration2.setEtat(EtatDeclaration.ARCHIVEE);

        rapport.AjouterDeclaration(declaration1);
        rapport.AjouterDeclaration(declaration2);

        List<Declaration> resolues = rapport.getDeclarationsResolues();

        assertTrue(resolues.isEmpty());
    }

    @Test
    void testGetDeclarationsResoluesToutesResolues() {
        declaration1.setEtat(EtatDeclaration.RESOLUE);
        declaration2.setEtat(EtatDeclaration.RESOLUE);
        declaration3.setEtat(EtatDeclaration.RESOLUE);

        rapport.AjouterDeclaration(declaration1);
        rapport.AjouterDeclaration(declaration2);
        rapport.AjouterDeclaration(declaration3);

        List<Declaration> resolues = rapport.getDeclarationsResolues();

        assertEquals(3, resolues.size());
        assertTrue(resolues.contains(declaration1));
        assertTrue(resolues.contains(declaration2));
        assertTrue(resolues.contains(declaration3));
    }

    @Test
    void testGetDeclarationsResoluesMixed() {
        declaration1.setEtat(EtatDeclaration.RESOLUE);
        declaration2.setEtat(EtatDeclaration.EN_COURS);
        declaration3.setEtat(EtatDeclaration.RESOLUE);

        rapport.AjouterDeclaration(declaration1);
        rapport.AjouterDeclaration(declaration2);
        rapport.AjouterDeclaration(declaration3);

        List<Declaration> resolues = rapport.getDeclarationsResolues();

        assertEquals(2, resolues.size());
        assertTrue(resolues.contains(declaration1));
        assertTrue(resolues.contains(declaration3));
        assertFalse(resolues.contains(declaration2));
    }

    @Test
    void testGetDeclarationsEmpty() {
        assertTrue(rapport.getDeclarations().isEmpty());
        assertTrue(rapport.getNouvellesDeclarations().isEmpty());
        assertTrue(rapport.getDeclarationsMisesAJour().isEmpty());
        assertTrue(rapport.getDeclarationsResolues().isEmpty());
    }
}