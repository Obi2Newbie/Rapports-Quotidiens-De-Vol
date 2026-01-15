import enums.EtatDeclaration;
import enums.Role;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AgentPolicier;
import service.RapportQuotidienVol;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AgentPolicierTest {

    private AgentPolicier agent;
    private Declaration declaration;
    private Victime victime;
    private Vehicule vehicule;
    private Lieu lieu;

    @BeforeEach
    void setUp() {
        agent = new AgentPolicier("agent123", "password123");

        victime = new Victime("CNI123", "Dupont", "Jean", "12 rue", "0612345678");
        vehicule = new Vehicule("Rouge", "Peugeot", "AB-123-CD");
        lieu = new Lieu("12", "rue de Paris", "Paris", "75001");
        declaration = new Declaration(new Date(), "14:30", Role.VICTIME, victime, vehicule, lieu);
    }

    @Test
    void testAuthentifierSuccessful() {
        boolean result = agent.authentifier("agent123", "password123");
        assertTrue(result);
    }

    @Test
    void testAuthentifierWrongPassword() {
        boolean result = agent.authentifier("agent123", "wrongpassword");
        assertFalse(result);
    }

    @Test
    void testAuthentifierWrongIdentifiant() {
        boolean result = agent.authentifier("wrongagent", "password123");
        assertFalse(result);

    }

    @Test
    void testAuthentifierBothWrong() {
        boolean result = agent.authentifier("wrongagent", "wrongpassword");
        assertFalse(result);
    }

    @Test
    void testAuthentifierEmptyCredentials() {
        boolean result = agent.authentifier("", "");
        assertFalse(result);
    }

    @Test
    void testCreerRapportQuotidienVol() {
        RapportQuotidienVol rapport = agent.creerRapportQuotidienVol();

        assertNotNull(rapport);
        assertNotNull(rapport.getDateRapport());
        assertTrue(rapport.getDeclarations().isEmpty());
    }

    @Test
    void testModifierEtatDeclaration() {
        assertEquals(EtatDeclaration.EN_COURS, declaration.getEtat());

        agent.modifierEtatDeclaration(declaration, EtatDeclaration.RESOLUE);

        assertEquals(EtatDeclaration.RESOLUE, declaration.getEtat());
    }

    @Test
    void testModifierEtatToResolue() {
        agent.modifierEtatDeclaration(declaration, EtatDeclaration.RESOLUE);

        assertEquals(EtatDeclaration.RESOLUE, declaration.getEtat());
    }

    @Test
    void testGetIdentifiant() {
        assertEquals("agent123", agent.getIdentifiant());
    }

    @Test
    void testModifierMultipleDeclarations() {
        Declaration declaration2 = new Declaration(new Date(), "15:00", Role.VICTIME, victime, vehicule, lieu);

        agent.modifierEtatDeclaration(declaration, EtatDeclaration.RESOLUE);
        agent.modifierEtatDeclaration(declaration2, EtatDeclaration.ARCHIVEE);

        assertEquals(EtatDeclaration.RESOLUE, declaration.getEtat());
        assertEquals(EtatDeclaration.ARCHIVEE, declaration2.getEtat());
    }

    @Test
    void testAgentCreationWithDifferentCredentials() {
        AgentPolicier agent2 = new AgentPolicier("agent456", "differentPassword");

        assertEquals("agent456", agent2.getIdentifiant());
        assertTrue(agent2.authentifier("agent456", "differentPassword"));
        assertFalse(agent2.authentifier("agent123", "password123"));
    }
}