import enums.Role;
import model.Declaration;
import model.Lieu;
import model.Temoin;
import model.Velo;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import java.util.Date;

class TemoinTest {

    private Temoin temoin;

    @BeforeEach
    void setUp() {
        temoin = new Temoin("CNI789012", "Martin", "Sophie", "25 avenue Victor Hugo", "0698765432");
    }

    @Test
    void testTemoinCreation() {
        assertNotNull(temoin);
        assertEquals("CNI789012", temoin.getNumeroCNI());
        assertEquals("Martin", temoin.getNom());
        assertEquals("Sophie", temoin.getPrenom());
        assertEquals("25 avenue Victor Hugo", temoin.getAdresse());
        assertEquals("0698765432", temoin.getTelephone());
    }

    @Test
    void testCreerDeclaration() {
        Lieu lieu = new Lieu("3", "place de la République", "Lyon", "69001");
        Velo velo = new Velo("Bleu", "Decathlon", "SN-001");

        Declaration declaration = temoin.creerDedclaration(
                new Date(), "09:30", Role.TEMOIN, velo, lieu
        );

        assertNotNull(declaration);
    }
}