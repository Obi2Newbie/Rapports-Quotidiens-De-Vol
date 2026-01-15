import enums.EtatDeclaration;
import enums.Role;
import model.Declaration;
import model.Lieu;
import model.Vehicule;
import model.Victime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class VictimeTest {

    private Victime victime;

    @BeforeEach
    void setUp() {
        victime = new Victime("CNI123456", "Dupont", "Jean", "12 rue de Paris", "0612345678");
    }

    @Test
    void testVictimeCreation() {
        assertNotNull(victime);
        assertEquals("CNI123456", victime.getNumeroCNI());
        assertEquals("Dupont", victime.getNom());
        assertEquals("Jean", victime.getPrenom());
        assertEquals("12 rue de Paris", victime.getAdresse());
        assertEquals("0612345678", victime.getTelephone());
    }

    @Test
    void testNotifierPrintsMessage() {
        assertDoesNotThrow(() -> victime.notifier("Test message"));
    }

    @Test
    void testCreerDeclaration() {
        Lieu lieu = new Lieu("12", "rue de Paris", "Paris", "75001");
        Vehicule vehicule = new Vehicule("Rouge", "Peugeot", "AB-123-CD");

        Declaration declaration = victime.creerDedclaration(
                new Date(), "14:30", Role.VICTIME, vehicule, lieu
        );

        assertNotNull(declaration);
        assertEquals(EtatDeclaration.EN_COURS, declaration.getEtat());
        assertTrue(declaration.getIdentifiant() > 0);
    }
}