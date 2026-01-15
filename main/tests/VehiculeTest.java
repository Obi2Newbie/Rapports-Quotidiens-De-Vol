import model.Vehicule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehiculeTest {

    @Test
    void testVehiculeCreation() {
        Vehicule vehicule = new Vehicule("Rouge", "Peugeot", "AB-123-CD");

        assertNotNull(vehicule);
        assertEquals("Rouge", vehicule.getCouleur());
        assertEquals("Peugeot", vehicule.getMarque());
        assertEquals("AB-123-CD", vehicule.getMatricule());
    }

    @Test
    void testVehiculeWithNullValues() {
        Vehicule vehicule = new Vehicule(null, null, null);

        assertNotNull(vehicule);
        assertNull(vehicule.getCouleur());
        assertNull(vehicule.getMarque());
        assertNull(vehicule.getMatricule());
    }
}