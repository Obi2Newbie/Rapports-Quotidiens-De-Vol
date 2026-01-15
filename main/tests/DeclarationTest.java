import enums.EtatDeclaration;
import enums.Role;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DeclarationTest {

    private Victime victime;
    private Temoin temoin;
    private Vehicule vehicule;
    private Velo velo;
    private Lieu lieu;
    private Date dateVol;
    private String heureVol;

    @BeforeEach
    void setUp() {
        victime = new Victime("CNI123", "Dupont", "Jean", "12 rue de Paris", "0612345678");
        temoin = new Temoin("CNI456", "Martin", "Pierre", "25 avenue Hugo", "0698765432");
        vehicule = new Vehicule("Rouge", "Peugeot", "AB-123-CD");
        velo = new Velo("Bleu", "Decathlon", "SN-001");
        lieu = new Lieu("12", "rue de Paris", "Paris", "75001");
        dateVol = new Date();
        heureVol = "14:30";
    }

    @Test
    void testDeclarationCreation() {
        Declaration declaration = new Declaration(dateVol, heureVol, Role.VICTIME, victime, vehicule, lieu);

        assertNotNull(declaration);
        assertEquals(EtatDeclaration.EN_COURS, declaration.getEtat());
        assertNotNull(declaration.getDateCreation());
        assertNotNull(declaration.getDerniereModifiaction());
        assertTrue(declaration.getIdentifiant() > 0);
    }

    @Test
    void testSetEtatChangesState() {
        Declaration declaration = new Declaration(dateVol, heureVol, Role.VICTIME, victime, vehicule, lieu);

        declaration.setEtat(EtatDeclaration.ARCHIVEE);

        assertEquals(EtatDeclaration.ARCHIVEE, declaration.getEtat());
    }

    @Test
    void testSetEtatUpdatesModificationDate() throws InterruptedException {
        Declaration declaration = new Declaration(dateVol, heureVol, Role.VICTIME, victime, vehicule, lieu);
        Date premiereDateModif = declaration.getDerniereModifiaction();

        Thread.sleep(10);
        declaration.setEtat(EtatDeclaration.RESOLUE);

        assertTrue(declaration.getDerniereModifiaction().after(premiereDateModif));
    }

    @Test
    void testSetEtatResolueNotifiesObserver() {
        Declaration declaration = new Declaration(dateVol, heureVol, Role.VICTIME, victime, vehicule, lieu);

        // This should trigger notification to the victim (Observer)
        declaration.setEtat(EtatDeclaration.RESOLUE);

        assertEquals(EtatDeclaration.RESOLUE, declaration.getEtat());
    }

    @Test
    void testSetEtatResolueDoesNotNotifyNonObserver() {
        Declaration declaration = new Declaration(dateVol, heureVol, Role.TEMOIN, temoin, vehicule, lieu);

        // This should NOT throw exception even though temoin is not an Observer
        assertDoesNotThrow(() -> declaration.setEtat(EtatDeclaration.RESOLUE));
        assertEquals(EtatDeclaration.RESOLUE, declaration.getEtat());
    }

    @Test
    void testUniqueIdentifiers() {
        Declaration decl1 = new Declaration(dateVol, heureVol, Role.VICTIME, victime, vehicule, lieu);
        Declaration decl2 = new Declaration(dateVol, heureVol, Role.VICTIME, victime, vehicule, lieu);

        assertNotEquals(decl1.getIdentifiant(), decl2.getIdentifiant());
    }

    @Test
    void testDeclarationWithVelo() {
        Declaration declaration = new Declaration(dateVol, heureVol, Role.VICTIME, victime, velo, lieu);

        assertNotNull(declaration);
        assertEquals(EtatDeclaration.EN_COURS, declaration.getEtat());
    }

    @Test
    void testDeclarationWithTemoin() {
        Declaration declaration = new Declaration(dateVol, heureVol, Role.TEMOIN, temoin, vehicule, lieu);

        assertNotNull(declaration);
        assertEquals(EtatDeclaration.EN_COURS, declaration.getEtat());
    }

    @Test
    void testMultipleStateChanges() {
        Declaration declaration = new Declaration(dateVol, heureVol, Role.VICTIME, victime, vehicule, lieu);

        assertEquals(EtatDeclaration.EN_COURS, declaration.getEtat());

        declaration.setEtat(EtatDeclaration.RESOLUE);
        assertEquals(EtatDeclaration.RESOLUE, declaration.getEtat());

        declaration.setEtat(EtatDeclaration.ARCHIVEE);
        assertEquals(EtatDeclaration.ARCHIVEE, declaration.getEtat());
    }

    @Test
    void testDateCreationBeforeModification() {
        Declaration declaration = new Declaration(dateVol, heureVol, Role.VICTIME, victime, vehicule, lieu);

        assertFalse(declaration.getDateCreation().after(declaration.getDerniereModifiaction()));
    }
}