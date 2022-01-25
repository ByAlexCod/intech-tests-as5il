package jardin;
import jardin.flore.Carotte;
import jardin.flore.IRacePure;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
class CarotteTest
{
    @Test
    void testSeReproduire() {
        IRacePure carotteInstance = new Carotte();
        HashMap<String, Integer> panier = new HashMap<>();
        panier.put("Carotte", 1);
        carotteInstance.seReproduire(panier);

        assertEquals(4, panier.get("Carotte"));
    }
}
