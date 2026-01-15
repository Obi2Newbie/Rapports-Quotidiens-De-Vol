import model.Lieu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LieuTest {

    @Test
    void testLieuCreation() {
        Lieu lieu = new Lieu("12", "rue de Paris", "Paris", "75001");

        assertNotNull(lieu);
        assertEquals("12", lieu.getNumeroRue());
        assertEquals("rue de Paris", lieu.getNomRue());
        assertEquals("Paris", lieu.getVille());
        assertEquals("75001", lieu.getCodePostal());
    }

    @Test
    void testLieuWithCompleteAddress() {
        Lieu lieu = new Lieu("45", "avenue des Champs-Élysées", "Paris", "75008");

        assertEquals("45", lieu.getNumeroRue());
        assertEquals("avenue des Champs-Élysées", lieu.getNomRue());
        assertEquals("Paris", lieu.getVille());
        assertEquals("75008", lieu.getCodePostal());
    }
}