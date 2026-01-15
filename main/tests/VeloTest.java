

import model.Velo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VeloTest {

    @Test
    void testVeloCreation() {
        Velo velo = new Velo("Bleu", "Decathlon", "SN-VTT-2024-001");

        assertNotNull(velo);
        assertEquals("Bleu", velo.getCouleur());
        assertEquals("Decathlon", velo.getMarque());
        assertEquals("SN-VTT-2024-001", velo.getNumeroSerie());
    }

    @Test
    void testVeloWithEmptyStrings() {
        Velo velo = new Velo("", "", "");

        assertNotNull(velo);
        assertEquals("", velo.getCouleur());
        assertEquals("", velo.getMarque());
        assertEquals("", velo.getNumeroSerie());
    }
}