package jardin;
import jardin.flore.Ail;
import jardin.flore.IRacePure;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.HashMap;
class AilTest
{
    @Test
    void testSeReproduire() {
        IRacePure ailInstance = new Ail();
        HashMap<String, Integer> panier = new HashMap();
        panier.put("Ail", 1);
        ailInstance.seReproduire(panier);

        assertEquals(4, panier.get("Ail"));
    }
}
