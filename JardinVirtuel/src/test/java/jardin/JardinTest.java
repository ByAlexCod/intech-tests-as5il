package jardin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JardinTest {

    @Test
    void testAjouterPanier() {
        Jardin jardinInstance = new Jardin(2, 2);
        jardinInstance.ajouterPanier("Carotte", 2);

        //Tests the number of carotts in panier
        assertEquals(2, jardinInstance.getPanier().get("Carotte"));
    }

}
