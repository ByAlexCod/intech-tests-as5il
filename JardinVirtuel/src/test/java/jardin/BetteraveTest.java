package jardin;
import jardin.flore.Betterave;
import jardin.flore.Etat;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.junit.jupiter.api.Assertions.*;
class BetteraveTest
{
    @Test
    void testSeDupliquer() {
        Betterave betteraveInstance = new Betterave();
        AbstractMap.SimpleEntry<Integer, Integer>  result = betteraveInstance.seDupliquer(5, 5);

        assertEquals(Etat.GRAINE, betteraveInstance.getEtat());
        assertTrue(result.getKey() < 5);
        assertTrue(result.getValue() < 5);
    }

}
