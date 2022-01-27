package jardin;

import jardin.flore.Ail;
import jardin.flore.Carotte;
import jardin.flore.Etat;
import org.junit.jupiter.api.Disabled;
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

    @Test
    void testRecolter() {
        Jardin jardinInstance = new Jardin(2, 2);
        Emplacement emplacementCarotte = new Emplacement(new Carotte());
        jardinInstance.getEmplacement()[0][0] = new Emplacement(new Ail(){{etat = Etat.FLEUR;}});
        jardinInstance.getEmplacement()[1][0] = emplacementCarotte;

        jardinInstance.recolter();

        assertNull(jardinInstance.getEmplacement()[0][0]);
        assertEquals(jardinInstance.getEmplacement()[1][0], emplacementCarotte);

        assertTrue(jardinInstance.getPanier().get("Ail") > 0);
        assertNull(jardinInstance.getPanier().get("Carotte"));
    }

    @Test
    @Disabled
    void testSaisonSuivante() {
        Jardin jardinInstance = new Jardin(2, 2);
        Emplacement emplacementCarotte = new Emplacement(new Carotte(){{etat = Etat.FLEUR;}});
        jardinInstance.getEmplacement()[0][0] = new Emplacement(new Ail(){{etat = Etat.MORT;}});
        jardinInstance.getEmplacement()[1][0] = emplacementCarotte;

        jardinInstance.passerSaisonSuivante();

        assertNull(jardinInstance.getEmplacement()[0][0]);
        assertEquals(jardinInstance.getEmplacement()[1][0], emplacementCarotte);
        assertEquals(jardinInstance.getEmplacement()[1][0].getVeg().getEtat(), Etat.MORT);


    }

//    int countElements(Emplacement[][] map, ) {
//        for (int x = 0; x< map.length; x ++) {
//            for(int y = 0; y < map[x].length; y ++) {
//
//            }
//        }
//    }


}
